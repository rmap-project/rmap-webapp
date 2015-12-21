package info.rmapproject.webapp.domain;

import java.io.Serializable;

public class GraphNode implements Serializable{

	public GraphNode(){
	}
	
	private static final long serialVersionUID = 1L;
    private static Integer MAX_NODETEXT_LENGTH = 21;
	private Integer id;
	private String name;
	private String shortname; 
	private Integer weight;
	private NodeType type;
	
	public GraphNode(Integer id, String name, Integer weight, NodeType type){
		setId(id);
		setName(name);
		setWeight(weight);
		setType(type);
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
		name=name.replace("\\", "\\\\");
		name=name.replace("'", "\\'");
		this.name = name;
		if (name.length() > MAX_NODETEXT_LENGTH) {
			setShortname(name.substring(0, MAX_NODETEXT_LENGTH-3) + "...");
		}
		else {
			setShortname(name);			
		}
		
	}

	public Integer getWeight() {
		return weight;
	}

	public void setWeight(Integer weight) {
		this.weight = weight;
	}

	public NodeType getType() {
		return type;
	}

	public void setType(NodeType type) {
		this.type = type;
	}

	public String getShortname() {
		return shortname;
	}

	public void setShortname(String shortname) {
		this.shortname = shortname;
	}	
	
}
