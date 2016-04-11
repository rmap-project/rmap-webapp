package info.rmapproject.webapp.controllers;

import info.rmapproject.webapp.service.DataDisplayService;
import info.rmapproject.webapp.service.dto.AgentDTO;
import info.rmapproject.webapp.service.dto.DiSCODTO;
import info.rmapproject.webapp.service.dto.EventDTO;
import info.rmapproject.webapp.service.dto.ResourceDTO;

import java.net.URI;
import java.net.URLDecoder;
import java.net.URLEncoder;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.SessionAttributes;

/**
 * Handles requests for the data visualization pages
 * @author khanson
 *
 */

@Controller
@SessionAttributes({"user","account"})
public class DataDisplayController {
	
	private static final Logger logger = LoggerFactory.getLogger(DataDisplayController.class);
	/**Service for managing RMap data display*/
	@Autowired
	private DataDisplayService dataDisplayService;
	
	/**
	 * GET details of a DiSCO
	 */
	@RequestMapping(value="/discos/{uri}", method = RequestMethod.GET)
	public String disco(@PathVariable(value="uri") String discoUri, @RequestParam(value="visualize", required=false) 
				Integer visualize, Model model) throws Exception {

		logger.info("DiSCO requested");
		if (visualize == null) {
			visualize = 0;
		}
		
		DiSCODTO discoDTO = dataDisplayService.getDiSCODTO(discoUri);

	    model.addAttribute("DISCO",discoDTO);	    
	    model.addAttribute("OBJECT_NODES", discoDTO.getGraph().getNodes());
	    model.addAttribute("OBJECT_EDGES", discoDTO.getGraph().getEdges());
	    
		if (visualize==1)	{
	    	return "discosvisual";
	    }
	    
		return "discos";
	}	

	@RequestMapping(value="/discos/{uri}/edit", method = RequestMethod.GET)
	public String discoeditable(@PathVariable(value="uri") String discoUri, Model model) throws Exception {
	
		DiSCODTO discoDTO = dataDisplayService.getDiSCODTO(discoUri);

	    model.addAttribute("DISCO",discoDTO);	    
	    model.addAttribute("OBJECT_NODES", discoDTO.getGraph().getNodes());
	    model.addAttribute("OBJECT_EDGES", discoDTO.getGraph().getEdges());
    
		return "discoedit";
	}	

	@RequestMapping(value="/discos/new", method = RequestMethod.GET)
	public String disconew(Model model) throws Exception {
	    model.addAttribute("NEWDISCO",true);	        
		return "disconew";
	}		
	

	/**
	 * GET details of an Agent
	 */
	@RequestMapping(value="/agents/{uri}", method = RequestMethod.GET)
	public String agent(@PathVariable(value="uri") String agentUri, 
			@RequestParam(value="visualize", required=false) Integer visualize, Model model) throws Exception {
		logger.info("Agent requested");
		
		if (visualize == null) {
			visualize = 0;
		}
		
		AgentDTO agentDTO = dataDisplayService.getAgentDTO(agentUri);

	    model.addAttribute("AGENT",agentDTO);	    
	    model.addAttribute("OBJECT_NODES", agentDTO.getGraph().getNodes());
	    model.addAttribute("OBJECT_EDGES", agentDTO.getGraph().getEdges());
	    
		if (visualize==1)	{
	    	return "agentsvisual";
	    }
	    
		return "agents";
	}	
	
	/**
	 * GET details of a resource
	 * @param resourceUri
	 * @param visualize - if "1" the larger visualization page will be displayed
	 * @param resview - This is for when a URI is passed in that may be an RMap object URI (Agent, DiSCO, Event).
	 * 					When resview==0, it will check for an RMap type, and where one is found the appropriate 
	 * 					RMap object page will be displayed instead of the generic resources page. When resview==1, 
	 * 					the /resources page will be displayed by default.
	 * @param model
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value="/resources/{uri}", method = RequestMethod.GET)
	public String resource(@PathVariable(value="uri") String resourceUri, 
				@RequestParam(value="visualize", required=false) Integer visualize, 
				@RequestParam(value="resview", required=false) Integer resview, 
				Model model) throws Exception {
		logger.error("Resource requested" + resourceUri);
		
		if (visualize == null) {
			visualize = 0;
		}
		if (resview == null) {
			resview = 0;
		}

		/*
		 * This is for when a URI is passed in that may be an RMap object URI (Agent, DiSCO, Event).
		 * When resview==0, it will check for an RMap type, and where one is found the appropriate 
		 * RMap object page will be displayed instead of the generic resource page. When resview==1, 
		 * the /resources page will be displayed by default.
		 */
		
		if (resview==0) {
			//decode http first
			resourceUri = URLDecoder.decode(resourceUri, "UTF-8");
			//TODO: need to handle exception properly
			String rmapType = dataDisplayService.getRMapTypeDisplayName(new URI(resourceUri));
			if (rmapType.length()>0){
				return "redirect:/" + rmapType.toLowerCase() + "s/" + URLEncoder.encode(resourceUri, "UTF-8");
			}
		}

		ResourceDTO resourceDTO = dataDisplayService.getResourceDTO(resourceUri);

	    model.addAttribute("RESOURCE",resourceDTO);	    
	    model.addAttribute("OBJECT_NODES", resourceDTO.getGraph().getNodes());
	    model.addAttribute("OBJECT_EDGES", resourceDTO.getGraph().getEdges());
	    	    
	    if (visualize==1)	{
	    	return "resourcesvisual";
	    }
		return "resources";
	}
		
	
	/**
	 * GET details of an Event
	 * @param eventUri
	 * @param model
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value="/events/{uri}", method = RequestMethod.GET)
	public String event(@PathVariable(value="uri") String eventUri, Model model) throws Exception {
		logger.info("Event requested");
				
		EventDTO eventDTO = dataDisplayService.getEventDTO(eventUri);
		model.addAttribute("EVENT", eventDTO);
		
		return "events";
	}	
	
	
	
	
}
