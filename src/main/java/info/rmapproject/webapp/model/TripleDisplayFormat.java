package info.rmapproject.webapp.model;

import info.rmapproject.core.model.RMapResource;
import info.rmapproject.core.model.RMapTriple;
import info.rmapproject.core.model.RMapUri;
import info.rmapproject.core.model.RMapValue;
import info.rmapproject.webapp.utils.WebappUtils;

public class TripleDisplayFormat {

	private RMapResource subject;
	private String subjectDisplay;
	private String subjectLink;
	
	private RMapUri predicate;
	private String predicateDisplay;
	private String predicateLink;
	
	private RMapValue object;
	private String objectDisplay;
	private String objectLink;
	
	
	public TripleDisplayFormat() {}

	public TripleDisplayFormat(RMapTriple rmapTriple) {
		
		RMapResource subj = rmapTriple.getSubject();
		String subjDisplay = subj.toString();
		String subjLink = "resources?uri=" + subj.toString();
		
		RMapUri pred = rmapTriple.getPredicate();
		String predDisplay = WebappUtils.replaceNamespace(pred.toString());
		String predLink = pred.toString();
		
		RMapValue obj = rmapTriple.getObject();
		String objDisplay = WebappUtils.replaceNamespace(obj.toString());
		String objLink = "";
					    			
		if (predDisplay.contains("rdf:type"))	{
			objLink = obj.toString();  
		}
		else {
			//no link it the object is a literal or bnode.
			if (obj instanceof RMapUri)	{
				objLink = "resources?uri=" + obj.toString();
			}
		}
		
		this.subject=subj;
		this.subjectDisplay=subjDisplay;
		this.subjectLink=subjLink;
		
		this.predicate=pred;
		this.predicateDisplay=predDisplay;
		this.predicateLink=predLink;
		
		this.object=obj;
		this.objectDisplay=objDisplay;
		this.objectLink=objLink;		
	}	

	public TripleDisplayFormat(RMapUri subject, String subjectDisplay, String subjectLink, 
								RMapUri predicate, String predicateDisplay, String predicateLink, 
								RMapValue object, String objectDisplay, String objectLink) {
		this.subject=subject;
		this.subjectDisplay=subjectDisplay;
		this.subjectLink=subjectLink;
		this.predicate=predicate;
		this.predicateDisplay=predicateDisplay;
		this.predicateLink=predicateLink;
		this.object=object;
		this.objectDisplay=objectDisplay;
		this.objectLink=objectLink;		
	}	
	
	public RMapResource getSubject() {
		return subject;
	}

	public void setSubject(RMapResource subject) {
		this.subject = subject;
	}

	public String getSubjectDisplay() {
		return subjectDisplay;
	}

	public void setSubjectDisplay(String subjectDisplay) {
		this.subjectDisplay = subjectDisplay;
	}

	public String getSubjectLink() {
		return subjectLink;
	}

	public void setSubjectLink(String subjectLink) {
		this.subjectLink = subjectLink;
	}		
	
	public RMapUri getPredicate() {
		return predicate;
	}
	public void setPredicate(RMapUri predicate) {
		this.predicate = predicate;
	}
	public String getPredicateDisplay() {
		return predicateDisplay;
	}
	public void setPredicateDisplay(String predicateDisplay) {
		this.predicateDisplay = predicateDisplay;
	}
	public String getPredicateLink() {
		return predicateLink;
	}
	public void setPredicateLink(String predicateLink) {
		this.predicateLink = predicateLink;
	}
	public RMapValue getObject() {
		return object;
	}
	public void setObject(RMapValue object) {
		this.object = object;
	}
	public String getObjectDisplay() {
		return objectDisplay;
	}
	public void setObjectDisplay(String objectDisplay) {
		this.objectDisplay = objectDisplay;
	}
	public String getObjectLink() {
		return objectLink;
	}
	public void setObjectLink(String objectLink) {
		this.objectLink = objectLink;
	}

}
