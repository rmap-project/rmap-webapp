package info.rmapproject.webapp.domain;

import info.rmapproject.webapp.utils.Constants;
import info.rmapproject.webapp.utils.WebappUtils;

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
	private String targetNodeType;
	
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
		if (label != null){
			setShortlabel(shortenLabel(label)); //update short label based on new label assignment	
		}
	}

	public String getTargetNodeType() {
		return targetNodeType;
	}

	public void setTargetNodeType(String targetNodeType) {
		this.targetNodeType = targetNodeType;
	}

	public String getShortlabel() {
		if (shortlabel==null && label != null){
			setShortlabel(shortenLabel(label)); //update short label based on new label assignment			
		}
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
		if (label != null){
			label = WebappUtils.replaceNamespace(label);
			if (label.length() > Constants.MAX_EDGETEXT_LENGTH) {
				setShortlabel(label.substring(label.length() - Constants.MAX_EDGETEXT_LENGTH) + "...");
			}
		}
		return label;
	}

	
}
