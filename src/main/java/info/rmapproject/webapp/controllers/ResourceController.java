package info.rmapproject.webapp.controllers;

import info.rmapproject.core.model.RMapStatus;
import info.rmapproject.core.model.RMapTriple;
import info.rmapproject.core.rmapservice.RMapService;
import info.rmapproject.core.rmapservice.RMapServiceFactoryIOC;
import info.rmapproject.webapp.model.GraphParts;
import info.rmapproject.webapp.model.ResourceDescription;
import info.rmapproject.webapp.model.TripleDisplayFormat;
import info.rmapproject.webapps.utils.WebappUtils;

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
	public String resource(@RequestParam("uri") String resourceUri, Model model) throws Exception {
		logger.info("Resource requested");
		
		URI uriResourceUri = null;
		resourceUri = URLDecoder.decode(resourceUri, "UTF-8");
		uriResourceUri = new URI(resourceUri);
				
		String rmapType = WebappUtils.getRMapType(uriResourceUri);
		if (rmapType.length()>0){
			return "redirect:" + rmapType + "s?uri=" + resourceUri;
		}
		
		RMapService rmapService = RMapServiceFactoryIOC.getFactory().createService();
		List<RMapTriple> rmapStatements = rmapService.getResourceRelatedTriples(uriResourceUri, RMapStatus.ACTIVE);
	    model.addAttribute("RESOURCE_URI", resourceUri);

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
	        
	    model.addAttribute("RESOURCE_DESCRIP", resourceDescription);
	    
	    //used to create visual graph

	    GraphParts graphParts = new GraphParts();
	    
	    for (RMapTriple triple :  rmapStatements)	{
	    	graphParts.addEdge(triple.getSubject().toString(),
	    						triple.getObject().toString(),
	    						triple.getPredicate().toString());	 
	    }
	    
	    model.addAttribute("OBJECT_NODES", graphParts.getNodes());
	    model.addAttribute("OBJECT_EDGES", graphParts.getEdges());

	    rmapService.closeConnection();
	    
		return "resources";
	}
	

	
	
	
}
