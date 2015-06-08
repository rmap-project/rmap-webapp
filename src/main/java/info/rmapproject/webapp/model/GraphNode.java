package info.rmapproject.webapp.model;

import java.io.Serializable;

public class GraphNode implements Serializable{

	private static final long serialVersionUID = 1L;
	private Integer id;
	private String name;
	private Integer weight;
	
	public GraphNode(){
	}
	
	public GraphNode(Integer id, String name, Integer weight){
		this.id = id;
		this.name = name;
		this.weight = weight;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Integer getWeight() {
		return weight;
	}

	public void setWeight(Integer weight) {
		this.weight = weight;
	}
	
	
	
}
