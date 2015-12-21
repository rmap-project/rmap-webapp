package info.rmapproject.webapp.controllers;

import info.rmapproject.webapp.service.DataDisplayService;
import info.rmapproject.webapp.service.dto.AgentDTO;
import info.rmapproject.webapp.service.dto.DiSCODTO;
import info.rmapproject.webapp.service.dto.EventDTO;
import info.rmapproject.webapp.service.dto.ResourceDTO;
import info.rmapproject.webapp.utils.WebappUtils;

import java.net.URI;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * Handles requests for the data visualization page
 */
@Controller
public class DataDisplayController {
	
	private static final Logger logger = LoggerFactory.getLogger(DataDisplayController.class);
	
	private DataDisplayService dataDisplayService;
	
	@Autowired(required=true)
	@Qualifier(value="dataDisplayService")
	public void setDataDisplayService(DataDisplayService dataDisplayService) {
		this.dataDisplayService = dataDisplayService;
	}	
	
	/**
	 * Get details of a DiSCO
	 */
	@RequestMapping(value="/discos", method = RequestMethod.GET)
	public String disco(@RequestParam("uri") String discoUri, @RequestParam(value="visualize", required=false) 
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
	
	

	/**
	 * Get details of an Agent
	 */
	@RequestMapping(value="/agents", method = RequestMethod.GET)
	public String agent(@RequestParam("uri") String agentUri, 
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
	 * Get details of a resource
	 * @param resourceUri
	 * @param visualize - if "1" the larger visualization page will be displayed
	 * @param objpage - This is for when a URI is passed in that may be an RMap object URI (Agent, DiSCO, Event).
	 * 					When objpage==1, it will check for an RMap type, and where one is found the appropriate 
	 * 					RMap object page will be displayed instead of the generic resources page. When objpage==0, 
	 * 					the /resources page will be displayed by default.
	 * @param model
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value="/resources", method = RequestMethod.GET)
	public String resource(@RequestParam("uri") String resourceUri, 
				@RequestParam(value="visualize", required=false) Integer visualize, 
				@RequestParam(value="objpage", required=false) Integer objpage, 
				Model model) throws Exception {
		logger.info("Resource requested");
		
		if (visualize == null) {
			visualize = 0;
		}
		if (objpage == null) {
			visualize = 0;
		}

		/*
		 * This is for when a URI is passed in that may be an RMap object URI (Agent, DiSCO, Event).
		 * When objpage==1, it will check for an RMap type, and where one is found the appropriate 
		 * RMap object page will be displayed instead of the generic resource page. When objpage==0, 
		 * the /resources page will be displayed by default.
		 */
		if (objpage==1) {
			//TODO: need to handle exception properly
			String rmapType = WebappUtils.getRMapType(new URI(resourceUri));
			if (rmapType.length()>0){
				return "redirect:" + rmapType + "s?uri=" + resourceUri;
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
	 * Get details of an Event
	 */
	@RequestMapping(value="/events", method = RequestMethod.GET)
	public String event(@RequestParam("uri") String eventUri, Model model) throws Exception {
		logger.info("Event requested");
				
		EventDTO eventDTO = dataDisplayService.getEventDTO(eventUri);
		model.addAttribute("EVENT", eventDTO);
		
		return "events";
	}	
	
	
	
	
}
