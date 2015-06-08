package info.rmapproject.webapp.controllers;

import info.rmapproject.core.model.RMapResource;
import info.rmapproject.core.model.RMapTriple;
import info.rmapproject.core.model.disco.RMapDiSCO;
import info.rmapproject.core.rmapservice.RMapService;
import info.rmapproject.core.rmapservice.RMapServiceFactoryIOC;
import info.rmapproject.webapp.model.GraphParts;
import info.rmapproject.webapp.model.ResourceDescription;
import info.rmapproject.webapp.model.TripleDisplayFormat;

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
		String discoUri = "ark:/22573/rmdg0nq9";
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
		
		//need to construct list of nodes and edges as we go through.
	    GraphParts graphParts = new GraphParts();
	    
	    graphParts.addEdge(discoUri,"rmap:DiSCO","rdf:type");
	    if (discoDescription.length()>0) {
	    	graphParts.addEdge(discoUri,discoDescription,"dcterms:description");
	    }
	    if (discoCreator.length()>0) {
	    	graphParts.addEdge(discoUri, discoCreator,"dcterms:creator");
	    }
	    
		List <URI> aggregatedResources = rmapDisco.getAggregratedResources();
	    for (URI aggregate : aggregatedResources) {
		    graphParts.addEdge(discoUri, aggregate.toString(),"ore:aggregates");
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
	    		String predicate = stmt.getPredicate().toString();
	    		String object = stmt.getObject().toString();
	    		
	    		if (subject.toString().equals(resource)) {
	    			TripleDisplayFormat tripleDF = new TripleDisplayFormat(stmt);
	    			String listKey = tripleDF.getSubjectDisplay()+tripleDF.getPredicateDisplay()+tripleDF.getObjectDisplay();
	    			
	    			if (tripleDF.getPredicateDisplay().contains("rdf:type"))	{
	    				types.put(listKey, tripleDF);	
	    			}
	    			else {
	    				properties.put(listKey, tripleDF);				
	    			}

				    graphParts.addEdge(subject.toString(), object, predicate);
	    		}
	    	}

	    	Map<String, TripleDisplayFormat> sortedTypes = new TreeMap<String, TripleDisplayFormat>(types);
	    	Map<String, TripleDisplayFormat> sortedProperties = new TreeMap<String, TripleDisplayFormat>(properties);
	    	
	    	resourceDescriptions.add(new ResourceDescription(resource, sortedTypes, sortedProperties));	    	
	    }
	    	    	    
	    rmapService.closeConnection();
	}

}
