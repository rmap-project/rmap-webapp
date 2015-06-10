package info.rmapproject.webapp.controllers;

import info.rmapproject.core.model.RMapLiteral;
import info.rmapproject.core.model.RMapResource;
import info.rmapproject.core.model.RMapStatus;
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

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * Handles requests for the application disco pages.
 */
@Controller
public class DiSCOController {
	
	private static final Logger logger = LoggerFactory.getLogger(DiSCOController.class);
	
	/**
	 * Get details of a DiSCO
	 */

	@RequestMapping(value="/discos", method = RequestMethod.GET)
	public String disco(@RequestParam("uri") String discoUri, @RequestParam(value="visualize", required=false) Integer visualize, Model model) throws Exception {
		logger.info("DiSCO requested");
		
		if (visualize == null) {
			visualize = 0;
		}
		
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
	    
	    model.addAttribute("DISCO_URI", discoUri);
	    model.addAttribute("DISCO_DESCRIPTION", discoDescription);
	    model.addAttribute("DISCO_CREATOR", discoCreator);
	    model.addAttribute("DISCO_OTHERVERSIONS", otherVersions);
	    model.addAttribute("DISCO_AGENTVERSIONS", agentVersions);
	    model.addAttribute("DISCO_STATUS", discoStatus);
		
		//need to construct list of nodes and edges as we go through.
	    GraphParts graphParts = new GraphParts();
	    
	    graphParts.addEdge(discoUri,"rmap:DiSCO","rdf:type");
	    graphParts.addEdge(discoUri, rmapDisco.getDescription(),"dcterms:description");
	    graphParts.addEdge(discoUri, rmapDisco.getCreator(),"dcterms:creator");
    	
    	List <URI> aggregatedResources = rmapDisco.getAggregratedResources();
	    model.addAttribute("RESOURCE_LIST", aggregatedResources);
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
	        
	    model.addAttribute("DISCO_RESOURCE_DESCRIP", resourceDescriptions);
	    
	    List <URI> events = rmapService.getDiSCOEvents(uriDiscoUri);
	    model.addAttribute("DISCO_EVENTS", events);
	    
		model.addAttribute("OBJECT_NODES", graphParts.getNodes());
	    model.addAttribute("OBJECT_EDGES", graphParts.getEdges());
	    	    
	    rmapService.closeConnection();
	    
	    if (visualize==1)	{
	    	return "discosvisual";
	    }
	    
		return "discos";
	}
	
	
	

	
	
	
}
