package info.rmapproject.webapp.controllers;

import info.rmapproject.core.model.RMapLiteral;
import info.rmapproject.core.model.RMapStatus;
import info.rmapproject.core.model.RMapTriple;
import info.rmapproject.core.rmapservice.RMapService;
import info.rmapproject.core.rmapservice.RMapServiceFactoryIOC;
import info.rmapproject.webapp.model.GraphParts;
import info.rmapproject.webapp.model.NodeType;
import info.rmapproject.webapp.model.ResourceDescription;
import info.rmapproject.webapp.model.TripleDisplayFormat;
import info.rmapproject.webapp.utils.WebappUtils;

import java.net.URI;
import java.net.URLDecoder;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import org.junit.Test;

public class ResourceControllerTest {

	@Test
	public void testResource() throws Exception{

		//WARNING: NOT A PROPER TEST JUST A COPY OF THE CODE!
		
		String resourceUri = "http://dx.doi.org/10.5281/zenodo.10307";
		URI uriResourceUri = null;
		resourceUri = URLDecoder.decode(resourceUri, "UTF-8");
		uriResourceUri = new URI(resourceUri);
				
		String rmapType = WebappUtils.getRMapType(uriResourceUri);
		if (rmapType.length()>0){
			String redirect =  "redirect:" + rmapType + "s?uri=" + resourceUri;
		}
		
		RMapService rmapService = RMapServiceFactoryIOC.getFactory().createService();
		List<RMapTriple> rmapStatements = rmapService.getResourceRelatedTriples(uriResourceUri, RMapStatus.ACTIVE);
	    //model.addAttribute("RESOURCE_URI", resourceUri);

    	Map<String,TripleDisplayFormat> types = new HashMap<String,TripleDisplayFormat>();	    	
    	Map<String,TripleDisplayFormat> properties = new HashMap<String,TripleDisplayFormat>(); 
    	
    	for (RMapTriple stmt : rmapStatements) {    		
    		TripleDisplayFormat tripleDF = new TripleDisplayFormat(stmt);
    		String listKey = tripleDF.getSubjectDisplay()+tripleDF.getPredicateDisplay()+tripleDF.getObjectDisplay();
    		
    		if (tripleDF.getPredicateDisplay().contains("rdf:type") 
    				&& stmt.getSubject().toString().equals(resourceUri))	{
    			types.put(listKey, tripleDF);	
    		}
    		else {
    			properties.put(listKey, tripleDF);				
    		}
    	}

    	Map<String, TripleDisplayFormat> sortedTypes = new TreeMap<String, TripleDisplayFormat>(types);
    	Map<String, TripleDisplayFormat> sortedProperties = new TreeMap<String, TripleDisplayFormat>(properties);
    	
    	ResourceDescription resourceDescription = new ResourceDescription(resourceUri, sortedTypes, sortedProperties);	    	
	        
	    //model.addAttribute("RESOURCE_DESCRIP", resourceDescription);
	    
	    //used to create visual graph

	    GraphParts graphParts = new GraphParts();
	    
	    for (RMapTriple triple :  rmapStatements)	{
	    	String object = triple.getObject().toString();
	    	if (triple.getObject() instanceof RMapLiteral && object.length()>30){
	    		object = object.substring(0,30) + "...";
	    	}

	    	NodeType sourceType = WebappUtils.getNodeType(triple.getSubject());
	    	NodeType targetType = WebappUtils.getNodeType(triple.getObject());
	    	graphParts.addEdge(triple.getSubject().toString(),
	    						object,
	    						triple.getPredicate().toString(), sourceType, targetType);	 
	    }
	    
	    //model.addAttribute("OBJECT_NODES", graphParts.getNodes());
	    //model.addAttribute("OBJECT_EDGES", graphParts.getEdges());

	    rmapService.closeConnection();
	}

}
