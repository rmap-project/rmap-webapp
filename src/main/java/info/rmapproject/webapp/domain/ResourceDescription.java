package info.rmapproject.webapp.domain;

import info.rmapproject.webapp.utils.WebappUtils;
import info.rmapproject.webapps.exception.ErrorCode;
import info.rmapproject.webapps.exception.RMapWebException;

import java.io.Serializable;
import java.util.Map;
import java.util.TreeMap;

/**
 * Holds a description of a single Resource to support the display of the data about that Resource.
 * It includes the Name of the Resource (typically a URI such as a DOI), the rdf:type(s) listed for that 
 * Resource, and a list of triples describing the Resource.
 * @author khanson
 *
 */
public class ResourceDescription implements Serializable {

	private static final long serialVersionUID = 1L;

	/**Name of resource - generally a string version of the URI for the resource*/
	private String resourceName; 
	/**List of rdf:types associated with the Resource. Typically the String KV is
	 * a concatenation of sub-pred-obj. The TripleDisplayFormat contains triples 
	 * describing the resource types along with additional information to support display 
	 * of the Resource */
	private Map<String, TripleDisplayFormat> resourceTypes;
	/**List of triples associated with the Resource. Typically the String KV is
	 * a concatenation of sub-pred-obj. The TripleDisplayFormat contains triples 
	 * describing the resources along with additional information to support display 
	 * of the Resource */
	private Map<String, TripleDisplayFormat> propertyValues;
		
	public ResourceDescription() {}

	public ResourceDescription(String resourceName) {
		this.resourceName = resourceName;
		this.resourceTypes = new TreeMap<String,TripleDisplayFormat>();
		this.propertyValues = new TreeMap<String, TripleDisplayFormat>();
	}
	
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
	
	public void addPropertyValue(TripleDisplayFormat tripleDF) throws RMapWebException {
		if (tripleDF!=null) {
			String listKey = tripleDF.getSubjectDisplay()+tripleDF.getPredicateDisplay()+tripleDF.getObjectDisplay();
			this.propertyValues.put(listKey, tripleDF);
		}
		else {
			throw new RMapWebException(ErrorCode.ER_RESOURCE_PROPERTY_VALUE_NULL);
		}
	}
	
	public void addResourceType(TripleDisplayFormat tripleDF) throws RMapWebException {
		if (tripleDF!=null) {
			//this is the only time we want to format the triple's object
			String objDisplay = WebappUtils.replaceNamespace(tripleDF.getObjectDisplay());
			tripleDF.setObjectDisplay(objDisplay);
			String listKey = tripleDF.getSubjectDisplay()+tripleDF.getPredicateDisplay()+tripleDF.getObjectDisplay();
			this.resourceTypes.put(listKey, tripleDF);
		}
		else {
			throw new RMapWebException(ErrorCode.ER_RESOURCE_TYPE_NULL);
		}		
	}

	
	
	
	
}
