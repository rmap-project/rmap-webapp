package info.rmapproject.webapp.model;

import java.io.Serializable;

public class GraphEdge implements Serializable {

	private static final long serialVersionUID = 1L;
    private static Integer MAX_EDGETEXT_LENGTH = 30;
	private Integer source;
	private Integer target;
	private String label;
	private String shortlabel;
	private Boolean connectsUri;
	
	public GraphEdge(){
	}
	
	public GraphEdge(Integer source, Integer target, String label){
		this.source = source;
		this.target = target;
		this.label = label;
	}
	
	public Integer getSource() {
		return source;
	}
	public void setSource(Integer source) {
		this.source = source;
	}
	public Integer getTarget() {
		return target;
	}
	public void setTarget(Integer target) {
		this.target = target;
	}
	public String getLabel() {
		return label;
	}
	public void setLabel(String label) {
		this.label = label;
		if (label.length() > MAX_EDGETEXT_LENGTH) {
			setShortlabel(label.substring(0, MAX_EDGETEXT_LENGTH-3) + "...");
		}
		else {
			setShortlabel(label);
		}
	}

	public Boolean getConnectsUri() {
		return connectsUri;
	}

	public void setConnectsUri(Boolean connectsUri) {
		this.connectsUri = connectsUri;
	}

	public String getShortlabel() {
		return shortlabel;
	}

	public void setShortlabel(String shortlabel) {
		this.shortlabel = shortlabel;
	}
	
}
