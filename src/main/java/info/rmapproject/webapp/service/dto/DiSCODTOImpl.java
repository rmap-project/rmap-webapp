package info.rmapproject.webapp.service.dto;

import info.rmapproject.core.model.RMapStatus;
import info.rmapproject.core.model.RMapUri;
import info.rmapproject.core.model.RMapValue;
import info.rmapproject.webapp.domain.Graph;
import info.rmapproject.webapp.domain.ResourceDescription;

import java.net.URI;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * 
 * @author khanson
 * Used to hold the information about a DiSCO needed to for representation on a webpage.
 */
public class DiSCODTOImpl implements DiSCODTO {

	private URI uri;
	private String description;
	private String creator;
	private List <URI> agentVersions;
	private List <URI> allVersions;
	private RMapStatus status;
	private List <URI> events;
	private List <URI> aggregatedResources; 
	private Graph graph;
	private List<ResourceDescription> resourceDescriptions;

	public URI getUri() {
		return uri;
	}
	public void setUri(URI uri) {
		this.uri = uri;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public void setDescription(RMapValue description) {
		if (description!=null){
			this.description=description.toString();
		}
		else {
			this.description="";
		}
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
	public List<URI> getAgentVersions() {
		return agentVersions;
	}
	public void setAgentVersions(List<URI> agentVersions) throws Exception {
		if (uri==null){
			throw new Exception("DiSCO URI must be defined before setting Agent Versions");
		}
		agentVersions.remove(uri);  //takes out current DiSCO URI
		this.agentVersions = agentVersions;
	}
	
	
	public List<URI> getAllVersions() {
		return allVersions;
	}
	
	public void setAllVersions(List<URI> otherAgentVersions) throws Exception {
		if (this.uri==null){
			throw new Exception("DiSCO URI must be defined before setting Agent Versions");
		}
		otherAgentVersions.remove(uri);  //takes out current DiSCO URI
		this.allVersions = otherAgentVersions;
	}
	

	/**
	 * calculated from all versions and agent versions.
	 * @return list of DiSCO versions not created by original Agent.
	 */
	public List<URI> getOtherAgentVersions() {
		if (allVersions!=null && agentVersions!=null){
			List <URI> otherAgentVersions = new ArrayList<URI>(); 
			for (URI version : this.allVersions) {
				if (!this.agentVersions.contains(version))	{
					otherAgentVersions.add(version);
				}
			}
			return otherAgentVersions;
		}
		else {
			return null;
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
	public List<URI> getAggregatedResources() {
		return aggregatedResources;
	}
	public void setAggregatedResources(List<URI> aggregatedResources) {
		this.aggregatedResources = aggregatedResources;
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
		
}
