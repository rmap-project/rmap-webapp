package info.rmapproject.webapp.service.dto;

import info.rmapproject.core.model.RMapStatus;
import info.rmapproject.core.model.RMapUri;
import info.rmapproject.webapp.domain.Graph;
import info.rmapproject.webapp.domain.ResourceDescription;

import java.net.URI;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * 
 * @author khanson
 * Used to hold the information about a DiSCO needed to for representation on a webpage.
 */
public class AgentDTOImpl implements AgentDTO {

	private URI uri;
	private String creator;
	private RMapStatus status;
	private List <URI> events;
	private String agentRepresented;
	private Graph graph;
	private List<ResourceDescription> resourceDescriptions;
	
	public URI getUri() {
		return uri;
	}
	public void setUri(URI uri) {
		this.uri = uri;
	}
	public String getCreator() {
		return creator;
	}
	public void setCreator(String creator) {
		this.creator = creator;
	}
	public void setCreator(RMapUri creator) {
		if (creator!=null){
			this.creator=creator.toString();
		}
		else {
			this.creator="";
		}
	}
	public RMapStatus getStatus() {
		return status;
	}
	public void setStatus(RMapStatus status) {
		this.status = status;
	}
	public List<URI> getEvents() {
		return events;
	}
	public void setEvents(List<URI> events) {
	    Set <URI> uniqueEvents = new HashSet<URI>();
	    uniqueEvents.addAll(events);
	    events.clear();
	    events.addAll(uniqueEvents);
		this.events = events;
	}
	public String getAgentRepresented() {
		return agentRepresented;
	}
	public void setAgentRepresented(String agentRepresented) {
		this.agentRepresented = agentRepresented;
	}
	public List<ResourceDescription> getResourceDescriptions() {
		return resourceDescriptions;
	}
	public void setResourceDescriptions(
			List<ResourceDescription> resourceDescriptions) {
		this.resourceDescriptions = resourceDescriptions;
	}
	public Graph getGraph() {
		return graph;
	}

	public void setGraph(Graph graph) {
		this.graph = graph;
	}
	
	public int getNumEvents() {
		int numEvents = events.size();
		return numEvents;
	}
}
