package info.rmapproject.webapp.service.dto;

import info.rmapproject.core.model.RMapStatus;
import info.rmapproject.core.model.RMapValue;
import info.rmapproject.webapp.domain.Graph;

import java.net.URI;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * 
 * @author khanson
 * Used to hold the information about a DiSCO needed to for representation on a webpage.
 */
public class AgentDTO {

	private URI uri;
	private String name;
	private RMapStatus status;
	private List <URI> events;
	private List <URI> discos;
	private String idProvider;
	private String authId;
	private Graph graph;
	
	public URI getUri() {
		return uri;
	}
	public void setUri(URI uri) {
		this.uri = uri;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public void setName(RMapValue name) {
		if (name!=null){
			this.name=name.toString();
		}
		else {
			this.name="";
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
	
	public List <URI> getDiscos() {
		return discos;
	}
	public void setDiscos(List <URI> discos) {
	    Set <URI> uniqueDiSCOs = new HashSet<URI>();
	    uniqueDiSCOs.addAll(discos);
	    discos.clear();
	    discos.addAll(uniqueDiSCOs);
		this.discos = discos;
	}
	public String getIdProvider() {
		return idProvider;
	}
	public void setIdProvider(String idProvider) {
		this.idProvider = idProvider;
	}
	public String getAuthId() {
		return authId;
	}
	public void setAuthId(String authId) {
		this.authId = authId;
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
	public int getNumDiscos() {
		int numDiSCOs = discos.size();
		return numDiSCOs;
	}
}
