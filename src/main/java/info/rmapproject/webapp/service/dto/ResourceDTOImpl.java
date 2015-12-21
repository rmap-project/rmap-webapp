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
public class ResourceDTOImpl implements ResourceDTO {

	private URI uri;
	private Graph graph;
	private List<URI> relatedDiSCOs;
	private ResourceDescription resourceDescription;

	public URI getUri() {
		return uri;
	}
	public void setUri(URI uri) {
		this.uri = uri;
	}
	
	public ResourceDescription getResourceDescription() {
		return resourceDescription;
	}
	public void setResourceDescription(ResourceDescription resourceDescription) {
		this.resourceDescription = resourceDescription;
	}

	public Graph getGraph() {
		return graph;
	}

	public void setGraph(Graph graph) {
		this.graph = graph;
	}

	public List<URI> getRelatedDiSCOs() {
		return relatedDiSCOs;
	}
	public void setRelatedDiSCOs(List<URI> relatedDiSCOs) {
		this.relatedDiSCOs = relatedDiSCOs;
	}
		
}
