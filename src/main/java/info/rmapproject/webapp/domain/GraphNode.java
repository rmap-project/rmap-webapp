package info.rmapproject.webapp.domain;

import info.rmapproject.webapp.utils.Constants;

import java.io.Serializable;

/**
 * Holds a Graph Node description
 * @author khanson
 *
 */
public class GraphNode implements Serializable{

	public GraphNode(){
	}
	
	private static final long serialVersionUID = 1L;
	
    /**ID of node - must be unique in the context of a graph*/
    private Integer id;
    /**Name of node to be used as a label*/
	private String name;
	/**Shortened version of the node name for tidy label display*/
	private String shortname; 
	/**Node weight*/
	private Integer weight;
	/**Node type**/
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
		if (name.length() > Constants.MAX_NODETEXT_LENGTH) {
			setShortname(name.substring(0, Constants.MAX_NODETEXT_LENGTH-3) + "...");
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
