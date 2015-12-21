package info.rmapproject.webapp.service.dto;

import info.rmapproject.webapp.domain.Graph;
import info.rmapproject.webapp.domain.ResourceDescription;

import java.net.URI;
import java.util.List;

/**
 * 
 * @author khanson
 * Used to hold the information about a DiSCO needed to for representation on a webpage.
 */
public interface ResourceDTO {
	public URI getUri();
	public ResourceDescription getResourceDescription();
	public List<URI> getRelatedDiSCOs();
	public Graph getGraph();		
}
