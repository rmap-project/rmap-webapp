package info.rmapproject.webapp.service.dto;

import info.rmapproject.core.model.RMapStatus;
import info.rmapproject.webapp.domain.Graph;
import info.rmapproject.webapp.domain.ResourceDescription;

import java.net.URI;
import java.util.List;

/**
 * 
 * @author khanson
 * Used to hold the information about a DiSCO needed to for representation on a webpage.
 */
public interface DiSCODTO {
	public URI getUri();
	public String getDescription();
	public String getCreator();
	public List<URI> getAgentVersions();
	public List<URI> getAllVersions();
	public RMapStatus getStatus();
	public List<URI> getEvents();
	public List<URI> getAggregatedResources();
	public List<ResourceDescription> getResourceDescriptions();
	public Graph getGraph();		
}
