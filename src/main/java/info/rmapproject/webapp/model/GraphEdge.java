package info.rmapproject.webapp.model;

import java.io.Serializable;

public class GraphEdge implements Serializable {

	private static final long serialVersionUID = 1L;
    private static Integer MAX_EDGETEXT_LENGTH = 30;
	private Integer source;
	private Integer target;
	private String label;
	private String shortlabel;
	private NodeType targetNodeType;
	
	public GraphEdge(){
	}
	
	public GraphEdge(Integer source, Integer target, String label){
		setSource(source);
		setTarget(target);
		setLabel(label);
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
		if (label.contains(":")){
			label=label.substring(label.lastIndexOf(":")+1);
		}
		if (label.contains("/")){
			label=label.substring(label.lastIndexOf("/")+1);
		}
		if (label.contains("#")){
			label=label.substring(label.lastIndexOf("#")+1);
		}
		this.label = label;
		if (label.length() > MAX_EDGETEXT_LENGTH) {
			setShortlabel(label.substring(0, MAX_EDGETEXT_LENGTH-3) + "...");
		}
		else {
			setShortlabel(label);
		}
	}

	public NodeType getTargetNodeType() {
		return targetNodeType;
	}

	public void setTargetNodeType(NodeType targetNodeType) {
		this.targetNodeType = targetNodeType;
	}

	public String getShortlabel() {
		return shortlabel;
	}

	public void setShortlabel(String shortlabel) {
		this.shortlabel = shortlabel;
	}
	
}
