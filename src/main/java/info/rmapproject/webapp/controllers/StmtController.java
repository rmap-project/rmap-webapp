package info.rmapproject.webapp.controllers;

import info.rmapproject.core.model.RMapStatus;
import info.rmapproject.core.model.RMapTriple;
import info.rmapproject.core.rmapservice.RMapService;
import info.rmapproject.core.rmapservice.RMapServiceFactoryIOC;
import info.rmapproject.webapp.model.ResourceDescription;
import info.rmapproject.webapp.model.TripleDisplayFormat;

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
 * Handles requests for the application event pages.
 */
@Controller
public class StmtController {
	
	private static final Logger logger = LoggerFactory.getLogger(StmtController.class);
	
	/**
	 * Get details of a Statement
	 */

	@RequestMapping(value="/stmts", method = RequestMethod.GET)
	public String statement(@RequestParam("uri") String stmtUri, Model model) throws Exception {
		logger.info("Resource requested");
		
		URI uriResourceUri = null;
		stmtUri = URLDecoder.decode(stmtUri, "UTF-8");
		uriResourceUri = new URI(stmtUri);
		
		RMapService rmapService = RMapServiceFactoryIOC.getFactory().createService();
		List<RMapTriple> rmapStatements = rmapService.getResourceRelatedTriples(uriResourceUri, RMapStatus.ACTIVE);
	    model.addAttribute("RESOURCE_URI", stmtUri);
	
		Map<String,TripleDisplayFormat> types = new HashMap<String,TripleDisplayFormat>();	    	
		Map<String,TripleDisplayFormat> properties = new HashMap<String,TripleDisplayFormat>(); 
		
		for (RMapTriple stmt : rmapStatements) {    		
			TripleDisplayFormat tripleDF = new TripleDisplayFormat(stmt);
			String listKey = tripleDF.getSubjectDisplay()+tripleDF.getPredicateDisplay()+tripleDF.getObjectDisplay();
			
			if (tripleDF.getPredicateDisplay().contains("rdf:type") 
					&& stmt.getSubject().toString().equals(stmtUri))	{
				types.put(listKey, tripleDF);	
			}
			else {
				properties.put(listKey, tripleDF);				
			}
		}
	
		Map<String, TripleDisplayFormat> sortedTypes = new TreeMap<String, TripleDisplayFormat>(types);
		Map<String, TripleDisplayFormat> sortedProperties = new TreeMap<String, TripleDisplayFormat>(properties);
		
		ResourceDescription resourceDescription = new ResourceDescription(stmtUri, sortedTypes, sortedProperties);	    	
	        
	    model.addAttribute("RESOURCE_DESCRIP", resourceDescription);
	    
	    rmapService.closeConnection();
	        
		return "stmts";
	}
		
}
