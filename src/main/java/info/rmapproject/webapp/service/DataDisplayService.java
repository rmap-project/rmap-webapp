package info.rmapproject.webapp.service;

import info.rmapproject.webapp.service.dto.AgentDTO;
import info.rmapproject.webapp.service.dto.DiSCODTO;
import info.rmapproject.webapp.service.dto.EventDTO;
import info.rmapproject.webapp.service.dto.ResourceDTO;

import java.net.URI;


/**
 * Service to retrieve packages of data to be used in webpage display
 * @author khanson
 *
 */
public interface DataDisplayService {
	
	/**
	 * Get DiSCO data package for URI provided. Data package contains elements used on web page views
	 * @param discoUri
	 * @return
	 * @throws Exception
	 */
	public DiSCODTO getDiSCODTO(String discoUri) throws Exception;
	
	/**
	 * Get Agent data package for URI provided. Data package contains elements used on web page views
	 * @param agentUri
	 * @return
	 * @throws Exception
	 */
	public AgentDTO getAgentDTO(String agentUri) throws Exception;
	
	/**
	 * Get Resource data package for URI provided. Data package contains elements used on web page views
	 * @param resourceUri
	 * @return
	 * @throws Exception
	 */
	public ResourceDTO getResourceDTO(String resourceUri) throws Exception;

	/**
	 * Get Event data package for URI provided. Data package contains elements used on web page views
	 * @param eventUri
	 * @return
	 * @throws Exception
	 */
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
