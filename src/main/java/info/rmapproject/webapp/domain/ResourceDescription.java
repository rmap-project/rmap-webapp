package info.rmapproject.webapp.domain;

import java.util.Map;

public class ResourceDescription {

	private String resourceName;
	private Map<String, TripleDisplayFormat> resourceTypes;
	private Map<String, TripleDisplayFormat> propertyValues;
	
	public ResourceDescription() {}

	public ResourceDescription(String resourceName, Map<String, TripleDisplayFormat> resourceTypes, Map<String, TripleDisplayFormat> propertyValues) {
		this.resourceName = resourceName;
		this.resourceTypes = resourceTypes;
		this.propertyValues = propertyValues;		
	}
	
	public String getResourceName() {
		return resourceName;
	}

	public void setResourceName(String resourceName) {
		this.resourceName = resourceName;
	}

	public Map<String, TripleDisplayFormat> getResourceTypes() {
		return resourceTypes;
	}

	public void setResourceTypes(Map<String, TripleDisplayFormat> resourceTypes) {
		this.resourceTypes = resourceTypes;
	}

	public Map<String, TripleDisplayFormat> getPropertyValues() {
		return propertyValues;
	}

	public void setPropertyValues(Map<String, TripleDisplayFormat> propertyValues) {
		this.propertyValues = propertyValues;
	}

	
	
	
	
}
