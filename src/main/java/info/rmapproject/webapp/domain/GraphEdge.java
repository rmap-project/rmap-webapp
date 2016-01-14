package info.rmapproject.webapp.domain;

import info.rmapproject.webapp.utils.Constants;

import java.io.Serializable;

/**
 * Holds a Graph Edge description
 * @author khanson
 *
 */
public class GraphEdge implements Serializable {

	private static final long serialVersionUID = 1L;
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
		this.label = label;
		setShortlabel(shortenLabel(label)); //update short label based on new label assignment
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
	
	/**
	 * Creates a short version of the label so it can be used for tidy graph display.
	 * By default the shortLabel method will be used to create the shortLabel each time setLabel is called
	 * To customize the short label, do a setShortLabel after setLabel
	 * @param label
	 * @return
	 */
	public String shortenLabel(String label) {
		//TODO:improve this to display the namespace prefix e.g. rmap:DiSCO
		if (label != null){
			if (label.contains(":")){
				label=label.substring(label.lastIndexOf(":")+1);
			}
			if (label.contains("/")){
				label=label.substring(label.lastIndexOf("/")+1);
			}
			if (label.contains("#")){
				label=label.substring(label.lastIndexOf("#")+1);
			}
			if (label.length() > Constants.MAX_EDGETEXT_LENGTH) {
				setShortlabel(label.substring(0, Constants.MAX_EDGETEXT_LENGTH-3) + "...");
			}
		}
		return label;
	}

	
}
