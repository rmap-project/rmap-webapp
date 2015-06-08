package info.rmapproject.webapp.model;

import java.io.Serializable;

public class GraphEdge implements Serializable {

	private static final long serialVersionUID = 1L;
	private Integer source;
	private Integer target;
	private String label;
	
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
	}
	
}
