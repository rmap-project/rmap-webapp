package info.rmapproject.webapp.controllers;

import info.rmapproject.core.model.RMapUri;
import info.rmapproject.core.model.event.RMapEvent;
import info.rmapproject.core.model.event.RMapEventCreation;
import info.rmapproject.core.model.event.RMapEventDeletion;
import info.rmapproject.core.model.event.RMapEventDerivation;
import info.rmapproject.core.model.event.RMapEventInactivation;
import info.rmapproject.core.model.event.RMapEventTombstone;
import info.rmapproject.core.model.event.RMapEventType;
import info.rmapproject.core.model.event.RMapEventUpdate;
import info.rmapproject.core.rmapservice.RMapService;
import info.rmapproject.core.rmapservice.RMapServiceFactoryIOC;
import info.rmapproject.webapp.utils.WebappUtils;

import java.net.URI;
import java.net.URLDecoder;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
public class EventController {
	
	private static final Logger logger = LoggerFactory.getLogger(EventController.class);
	
	/**
	 * Get details of a Event
	 */

	@RequestMapping(value="/events", method = RequestMethod.GET)
	public String event(@RequestParam("uri") String eventUri, Model model) throws Exception {
		logger.info("Event requested");
		
		URI uriEventUri = null;
		eventUri = URLDecoder.decode(eventUri, "UTF-8");
		uriEventUri = new URI(eventUri);
		
		RMapService rmapService = RMapServiceFactoryIOC.getFactory().createService();
		RMapEvent rmapEvent = rmapService.readEvent(uriEventUri);
	    model.addAttribute("EVENT_URI", eventUri);
	    model.addAttribute("EVENT_DESCRIPTION", rmapEvent.getDescription());

		RMapUri associatedAgent = rmapEvent.getAssociatedAgent();
	    model.addAttribute("EVENT_AGENT", associatedAgent.toString());
		model.addAttribute("EVENT_TARGETTYPE", rmapEvent.getEventTargetType());  //disco or agent
	    model.addAttribute("EVENT_STARTTIME", rmapEvent.getStartTime()); 
	    model.addAttribute("EVENT_ENDTIME", rmapEvent.getEndTime()); 

	    RMapEventType eventType = rmapEvent.getEventType();
	    model.addAttribute("EVENT_TYPE", eventType); //create, delete etc.
	    	    
	    Map<String, String> resourcesAffected = new HashMap<String, String>();

	    if (eventType == RMapEventType.CREATION){
	    	RMapEventCreation creationEvent = (RMapEventCreation) rmapEvent;
	    	List<RMapUri> uris = creationEvent.getCreatedObjectIds();
	    	for (RMapUri uri : uris){
	    		String type = WebappUtils.getRMapType(new URI(uri.toString()));
	    		resourcesAffected.put(uri.toString(), "Created " + type);
	    	}
	    }
	    else if (eventType == RMapEventType.DELETION)	{
	    	RMapEventDeletion deletionEvent = (RMapEventDeletion) rmapEvent;
	    	List<RMapUri> uris = deletionEvent.getDeletedObjectIds();
	    	for (RMapUri uri : uris){
	    		String type = WebappUtils.getRMapType(new URI(uri.toString()));
	    		resourcesAffected.put(uri.toString(), "Deleted " + type);
	    	}
	    }
	    else if (eventType == RMapEventType.TOMBSTONE)	{
	    	RMapEventTombstone tombstoneEvent = (RMapEventTombstone) rmapEvent;
	    	RMapUri uri = tombstoneEvent.getTombstonedResourceId();
			String type = WebappUtils.getRMapType(new URI(uri.toString()));
    		resourcesAffected.put(uri.toString(), "Tombstoned " + type);
	    }
	    else if (eventType == RMapEventType.DERIVATION)	{
	    	RMapEventDerivation derivationEvent = (RMapEventDerivation) rmapEvent;
	    	List<RMapUri> createdUris = derivationEvent.getCreatedObjectIds();
	    	for (RMapUri uri : createdUris){
	    		String type = WebappUtils.getRMapType(new URI(uri.toString()));
	    		resourcesAffected.put(uri.toString(), "Created " + type);
	    	}
	    	RMapUri derivedUri = derivationEvent.getDerivedObjectId();
			String type = WebappUtils.getRMapType(new URI(derivedUri.toString()));
    		resourcesAffected.put(derivedUri.toString(), "Derived " + type);	    	
	    }
	    else if (eventType == RMapEventType.UPDATE)	{
	    	RMapEventUpdate updateEvent = (RMapEventUpdate) rmapEvent;	   
	    	List<RMapUri> createdUris = updateEvent.getCreatedObjectIds();
	    	for (RMapUri uri : createdUris){
	    		String type = WebappUtils.getRMapType(new URI(uri.toString()));
	    		resourcesAffected.put(uri.toString(), "Created " + type);
	    	}
	    	RMapUri derivedUri = updateEvent.getDerivedObjectId();
			String type = WebappUtils.getRMapType(new URI(derivedUri.toString()));
    		resourcesAffected.put(derivedUri.toString(), "Derived " + type);	
    		
	    	RMapUri inactivatedUri = updateEvent.getInactivatedObjectId();
	    	type = WebappUtils.getRMapType(new URI(inactivatedUri.toString()));
    		resourcesAffected.put(inactivatedUri.toString(), "Inactivated " + type);	  	    			    	
	    }
	    else if (eventType == RMapEventType.INACTIVATION)	{
	    	RMapEventInactivation inactivateEvent = (RMapEventInactivation) rmapEvent;	    
	    	RMapUri uri = inactivateEvent.getInactivatedObjectId();
			String type = WebappUtils.getRMapType(new URI(uri.toString()));
    		resourcesAffected.put(uri.toString(), "Inactivated " + type);	  
	    }
	        
	    model.addAttribute("EVENT_RESAFFECTED", resourcesAffected);
	    
	    rmapService.closeConnection();
	    
		return "events";
	}
		
}
