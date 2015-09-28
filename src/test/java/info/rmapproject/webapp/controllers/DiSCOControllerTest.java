package info.rmapproject.webapp.controllers;

import info.rmapproject.core.model.RMapResource;
import info.rmapproject.core.model.RMapStatus;
import info.rmapproject.core.model.RMapTriple;
import info.rmapproject.core.model.RMapValue;
import info.rmapproject.core.model.disco.RMapDiSCO;
import info.rmapproject.core.rmapservice.RMapService;
import info.rmapproject.core.rmapservice.RMapServiceFactoryIOC;
import info.rmapproject.webapp.model.GraphParts;
import info.rmapproject.webapp.model.NodeType;
import info.rmapproject.webapp.model.ResourceDescription;
import info.rmapproject.webapp.model.TripleDisplayFormat;
import info.rmapproject.webapp.utils.WebappUtils;

import java.net.URI;
import java.net.URLDecoder;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;

import org.junit.Test;

public class DiSCOControllerTest {

	@Test
	public void testDisco() throws Exception{
		String discoUri = "ark:/22573/rmd18mddgf";
		URI uriDiscoUri = null;
		discoUri = URLDecoder.decode(discoUri, "UTF-8");
		uriDiscoUri = new URI(discoUri);
				
		RMapService rmapService = RMapServiceFactoryIOC.getFactory().createService();
		RMapDiSCO rmapDisco = rmapService.readDiSCO(uriDiscoUri);
	    
		String discoDescription = "";
		if (rmapDisco.getDescription()!=null){
			discoDescription=rmapDisco.getDescription().toString();
		}
		
		String discoCreator = "";
		if (rmapDisco.getCreator()!=null){
			discoCreator=rmapDisco.getCreator().toString();
		}
		
		List <URI> agentVersions = rmapService.getDiSCOAllAgentVersions(uriDiscoUri);
		agentVersions.remove(uriDiscoUri);  //takes out current DiSCO URI
		
		List <URI> allVersions = rmapService.getDiSCOAllVersions(uriDiscoUri);
		allVersions.remove(uriDiscoUri);  //takes out current DiSCO URI
		List <URI> otherVersions = new ArrayList<URI>(); 
		for (URI version : allVersions) {
			if (!agentVersions.contains(version))	{
				otherVersions.add(version);
			}
		}
		
		RMapStatus discoStatus = rmapService.getDiSCOStatus(uriDiscoUri);
	    
		//need to construct list of nodes and edges as we go through.
	    GraphParts graphParts = new GraphParts();
	    
	    graphParts.addEdge(discoUri,"rmap:DiSCO","rdf:type", NodeType.DISCO, NodeType.UNDEFINED);
	    
	    if (discoDescription !=  null) {
	    graphParts.addEdge(discoUri, discoDescription,"dcterms:description", NodeType.DISCO, NodeType.LITERAL);
	    }
	    graphParts.addEdge(discoUri, rmapDisco.getCreator().toString(),"dcterms:creator", NodeType.DISCO, NodeType.AGENT);
    	
    	List <URI> aggregatedResources = rmapDisco.getAggregratedResources();
	    for (URI aggregate : aggregatedResources) {
	    	NodeType type = WebappUtils.getNodeType(aggregate, uriDiscoUri);
		    graphParts.addEdge(discoUri, aggregate.toString(),"ore:aggregates", NodeType.DISCO, type);
	    }
	    
	    List <RMapTriple> rmapStatements = rmapDisco.getRelatedStatements();
 	    
	    //first extract unique list of resources mentioned in subject	
	    Set<String> resourcesDescribed = new HashSet<String>();
	    for (RMapTriple stmt:rmapStatements) {
	    	resourcesDescribed.add(stmt.getSubject().toString());
	    }
	    
	    List<ResourceDescription> resourceDescriptions = new ArrayList<ResourceDescription>();
	    
	    //now sort statements into blocks by resource
	    for (String resource : resourcesDescribed) {
	    	
	    	Map<String,TripleDisplayFormat> types = new HashMap<String,TripleDisplayFormat>();	    	
	    	Map<String,TripleDisplayFormat> properties = new HashMap<String,TripleDisplayFormat>(); 
	    	
	    	for (RMapTriple stmt : rmapStatements) {
	    		RMapResource subject = stmt.getSubject();
	    		
	    		if (subject.toString().equals(resource)) {
	    			TripleDisplayFormat tripleDF = new TripleDisplayFormat(stmt);
	    			String listKey = tripleDF.getSubjectDisplay()+tripleDF.getPredicateDisplay()+tripleDF.getObjectDisplay();
	    			
	    			if (tripleDF.getPredicateDisplay().contains("rdf:type"))	{
	    				types.put(listKey, tripleDF);	
	    			}
	    			else {
	    				properties.put(listKey, tripleDF);				
	    			}
				    graphParts.addEdge(stmt);
	    		}
	    	}

	    	Map<String, TripleDisplayFormat> sortedTypes = new TreeMap<String, TripleDisplayFormat>(types);
	    	Map<String, TripleDisplayFormat> sortedProperties = new TreeMap<String, TripleDisplayFormat>(properties);
	    	
	    	resourceDescriptions.add(new ResourceDescription(resource, sortedTypes, sortedProperties));	    	
	    }
	        
	    
	    List <URI> events = rmapService.getDiSCOEvents(uriDiscoUri);
	    	    
	    rmapService.closeConnection();
	}

}
