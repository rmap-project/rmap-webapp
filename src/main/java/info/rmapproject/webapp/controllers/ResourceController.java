package info.rmapproject.webapp.controllers;

import info.rmapproject.core.exception.RMapObjectNotFoundException;
import info.rmapproject.core.model.RMapStatus;
import info.rmapproject.core.model.RMapTriple;
import info.rmapproject.core.rmapservice.RMapService;
import info.rmapproject.core.rmapservice.RMapServiceFactoryIOC;
import info.rmapproject.webapp.model.GraphParts;
import info.rmapproject.webapp.model.ResourceDescription;
import info.rmapproject.webapp.model.TripleDisplayFormat;
import info.rmapproject.webapp.utils.WebappUtils;

import java.net.URI;
import java.net.URLDecoder;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * Handles requests for the application resource pages.
 */
@Controller
public class ResourceController {
	
	private static final Logger logger = LoggerFactory.getLogger(ResourceController.class);
	
	/**
	 * Get details of a Resource
	 */

	@RequestMapping(value="/resources", method = RequestMethod.GET)
	public String resource(@RequestParam("uri") String resourceUri, @RequestParam(value="visualize", required=false) Integer visualize, Model model) throws Exception {
		logger.info("Resource requested");
		
		if (visualize == null) {
			visualize = 0;
		}
		
		URI uriResourceUri = null;
		resourceUri = URLDecoder.decode(resourceUri, "UTF-8");
		uriResourceUri = new URI(resourceUri);
				
		String rmapType = WebappUtils.getRMapType(uriResourceUri);
		if (rmapType.length()>0){
			return "redirect:" + rmapType + "s?uri=" + resourceUri;
		}
		
		RMapService rmapService = RMapServiceFactoryIOC.getFactory().createService();
		List<RMapTriple> rmapTriples = rmapService.getResourceRelatedTriples(uriResourceUri, RMapStatus.ACTIVE);
    	
		//if there are no triples, don't load an empty screen, show a not found error.
    	if (rmapTriples.size()==0)	{
    		throw new RMapObjectNotFoundException();
    	}
    	
		model.addAttribute("RESOURCE_URI", resourceUri);

    	Map<String,TripleDisplayFormat> types = new HashMap<String,TripleDisplayFormat>();	    	
    	Map<String,TripleDisplayFormat> properties = new HashMap<String,TripleDisplayFormat>(); 
    	
    	for (RMapTriple triple : rmapTriples) {    		
    		TripleDisplayFormat tripleDF = new TripleDisplayFormat(triple);
    		String listKey = tripleDF.getSubjectDisplay()+tripleDF.getPredicateDisplay()+tripleDF.getObjectDisplay();
    		
    		if (tripleDF.getPredicateDisplay().contains("rdf:type") 
    				&& triple.getSubject().toString().equals(resourceUri))	{
    			types.put(listKey, tripleDF);	
    		}
    		else {
    			properties.put(listKey, tripleDF);				
    		}
    	}

    	Map<String, TripleDisplayFormat> sortedTypes = new TreeMap<String, TripleDisplayFormat>(types);
    	Map<String, TripleDisplayFormat> sortedProperties = new TreeMap<String, TripleDisplayFormat>(properties);
    	
    	ResourceDescription resourceDescription = new ResourceDescription(resourceUri, sortedTypes, sortedProperties);	    	
	        
	    model.addAttribute("RESOURCE_DESCRIP", resourceDescription);
	    
	    //used to create visual graph

	    GraphParts graphParts = new GraphParts();
	    
	    for (RMapTriple triple :  rmapTriples)	{	    	
	    	graphParts.addEdge(triple);	 
	    }
	    
	    model.addAttribute("OBJECT_NODES", graphParts.getNodes());
	    model.addAttribute("OBJECT_EDGES", graphParts.getEdges());

	    rmapService.closeConnection();
	    
	    if (visualize==1)	{
	    	return "resourcesvisual";
	    }
		return "resources";
	}
	

	
	
	
}
