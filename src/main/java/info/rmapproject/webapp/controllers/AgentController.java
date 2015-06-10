package info.rmapproject.webapp.controllers;

import info.rmapproject.core.model.RMapLiteral;
import info.rmapproject.core.model.RMapResource;
import info.rmapproject.core.model.RMapStatus;
import info.rmapproject.core.model.RMapTriple;
import info.rmapproject.core.model.agent.RMapAgent;
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
 * Handles requests for the application agent pages.
 */
@Controller
public class AgentController {
	
	private static final Logger logger = LoggerFactory.getLogger(AgentController.class);
	
	/**
	 * Get details of a Agent
	 */

	@RequestMapping(value="/agents", method = RequestMethod.GET)
	public String agent(@RequestParam("uri") String agentUri, @RequestParam(value="visualize", required=false) Integer visualize, Model model) throws Exception {
		logger.info("Agent requested");
		
		if (visualize == null) {
			visualize = 0;
		}
		
		URI uriAgentUri = null;
		agentUri = URLDecoder.decode(agentUri, "UTF-8");
		uriAgentUri = new URI(agentUri);
		
		RMapService rmapService = RMapServiceFactoryIOC.getFactory().createService();
		RMapAgent rmapAgent = rmapService.readAgent(uriAgentUri);
		
		String agentCreator = "";
		if (rmapAgent.getCreator()!=null){
			agentCreator=rmapAgent.getCreator().toString();
		}
		
		RMapStatus agentStatus = rmapService.getAgentStatus(uriAgentUri);
		String agentRepresented = rmapAgent.getRepresentationId().toString();
		
		//need to construct list of nodes and edges as we go through.
	    GraphParts graphParts = new GraphParts();
	    
	    graphParts.addEdge(agentUri,"rmap:Agent","rdf:type");
	    graphParts.addEdge(agentUri, rmapAgent.getCreator(),"dcterms:creator");
	    graphParts.addEdge(agentUri, agentRepresented,"dcterms:isFormatOf");
	    
	    model.addAttribute("AGENT_URI", agentUri);
	    model.addAttribute("AGENT_RESPRESENTED", agentRepresented);
	    model.addAttribute("AGENT_CREATOR", agentCreator);
	    model.addAttribute("AGENT_STATUS", agentStatus);
		
	    List <RMapTriple> agentProperties = rmapAgent.getProperties();
 	    
	    //first extract unique list of resources mentioned in subject	
	    Set<String> resourcesDescribed = new HashSet<String>();
	    for (RMapTriple property:agentProperties) {
	    	resourcesDescribed.add(property.getSubject().toString());
	    }
	    
	    List<ResourceDescription> resourceDescriptions = new ArrayList<ResourceDescription>();
	    
	    //now sort statements into blocks by resource
	    for (String resource : resourcesDescribed) {
	    	
	    	Map<String,TripleDisplayFormat> types = new HashMap<String,TripleDisplayFormat>();	    	
	    	Map<String,TripleDisplayFormat> properties = new HashMap<String,TripleDisplayFormat>(); 
	    	
	    	for (RMapTriple triple : agentProperties) {
	    		RMapResource subject = triple.getSubject();

	    		if (subject.toString().equals(resource)) {
	    			TripleDisplayFormat tripleDF = new TripleDisplayFormat(triple);
	    			String listKey = tripleDF.getSubjectDisplay()+tripleDF.getPredicateDisplay()+tripleDF.getObjectDisplay();
	    			
	    			if (tripleDF.getPredicateDisplay().contains("rdf:type"))	{
	    				types.put(listKey, tripleDF);	
	    			}
	    			else {
	    				properties.put(listKey, tripleDF);				
	    			}
				    graphParts.addEdge(triple);
	    		}
	    	}

	    	Map<String, TripleDisplayFormat> sortedTypes = new TreeMap<String, TripleDisplayFormat>(types);
	    	Map<String, TripleDisplayFormat> sortedProperties = new TreeMap<String, TripleDisplayFormat>(properties);
	    	
	    	resourceDescriptions.add(new ResourceDescription(resource, sortedTypes, sortedProperties));	    	
	    }
	        
	    model.addAttribute("AGENT_RESOURCE_DESCRIP", resourceDescriptions);
	    
	    List <URI> events = rmapService.getAgentEvents(uriAgentUri);
	    model.addAttribute("AGENT_EVENTS", events);
	    
		model.addAttribute("OBJECT_NODES", graphParts.getNodes());
	    model.addAttribute("OBJECT_EDGES", graphParts.getEdges());
	    	    
	    rmapService.closeConnection();
	    
	    if (visualize==1)	{
	    	return "agentsvisual";
	    }
	    
		return "agents";
	}
	
	
	

	
	
	
}
