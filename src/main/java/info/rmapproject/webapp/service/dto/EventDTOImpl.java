package info.rmapproject.webapp.service.dto;

import info.rmapproject.core.model.RMapUri;
import info.rmapproject.core.model.RMapValue;
import info.rmapproject.core.model.event.RMapEventTargetType;
import info.rmapproject.core.model.event.RMapEventType;

import java.net.URI;
import java.util.Date;
import java.util.Map;

/**
 * 
 * @author khanson
 * Used to hold the information about a DiSCO needed to for representation on a webpage.
 */
public class EventDTOImpl implements EventDTO {

	private URI uri;
	private String associatedAgent;
	private RMapEventType type;
	private RMapEventTargetType targetType;
	private String description;
	private Date startTime;
	private Date endTime;
	private Map<String, String> resourcesAffected;
	
	public URI getUri() {
		return uri;
	}
	public void setUri(URI uri) {
		this.uri = uri;
	}
	
	public String getAssociatedAgent() {
		return associatedAgent;
	}
	public void setAssociatedAgent(String associatedAgent) {
		this.associatedAgent = associatedAgent;
	}
	public void setAssociatedAgent(RMapUri associatedAgent) {
		if (associatedAgent!=null){
			this.associatedAgent=associatedAgent.toString();
		}
		else {
			this.associatedAgent="";
		}
	}

	public RMapEventType getType() {
		return type;
	}
	public void setType(RMapEventType type) {
		this.type = type;
	}
	
	public RMapEventTargetType getTargetType() {
		return targetType;
	}
	public void setTargetType(RMapEventTargetType targetType) {
		this.targetType = targetType;
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
	
	public Map<String, String> getResourcesAffected() {
		return resourcesAffected;
	}
	public void setResourcesAffected(
			Map<String,String> resourcesAffected) {
		this.resourcesAffected = resourcesAffected;
	}
	
	public Date getStartTime() {
		return startTime;
	}
	public void setStartTime(Date startTime) {
		this.startTime = startTime;
	}
	public Date getEndTime() {
		return endTime;
	}
	public void setEndTime(Date endTime) {
		this.endTime = endTime;
	}
	
}
