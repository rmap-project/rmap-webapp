package info.rmapproject.webapp.service;

import info.rmapproject.webapp.service.dto.AgentDTO;
import info.rmapproject.webapp.service.dto.DiSCODTO;
import info.rmapproject.webapp.service.dto.EventDTO;
import info.rmapproject.webapp.service.dto.ResourceDTO;

import java.net.URI;


/**
 * 
 * @author khanson
 *
 */
public interface DataDisplayService {
	
	public DiSCODTO getDiSCODTO(String discoUri) throws Exception;
	public AgentDTO getAgentDTO(String agentUri) throws Exception;
	public ResourceDTO getResourceDTO(String resourceUri) throws Exception;
	public EventDTO getEventDTO(String eventUri) throws Exception;
	
	/**
	 * Determine whether the URI provided is an RMap object, or just a regular resource.
	 * If it is an RMap resource return the type name as string.
	 * @param resourceUri
	 * @return
	 * @throws Exception
	 */
	public String getRMapTypeDisplayName(URI resourceUri) throws Exception;

}
