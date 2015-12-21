package info.rmapproject.webapp.service.dto;

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
public interface EventDTO {
	public URI getUri();
	public String getAssociatedAgent();
	public String getDescription();
	public RMapEventType getType();
	public RMapEventTargetType getTargetType();
	public Date getStartTime();
	public Date getEndTime();
	public Map<String,String> getResourcesAffected();
}
