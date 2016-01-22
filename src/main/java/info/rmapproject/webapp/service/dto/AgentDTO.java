package info.rmapproject.webapp.service.dto;

import info.rmapproject.webapp.domain.Graph;

import java.net.URI;
import java.util.List;

/**
 * 
 * @author khanson
 * Used to hold the information about a DiSCO needed to for representation on a webpage.
 */
public interface AgentDTO {
	public URI getUri();
	public String getName();
	public List<URI> getEvents();
	public List<URI> getDiscos();
	public String getIdProvider();
	public Graph getGraph();
	public int getNumEvents();
	public int getNumDiscos();
}
