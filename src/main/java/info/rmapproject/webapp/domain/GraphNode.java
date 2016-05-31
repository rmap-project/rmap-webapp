package info.rmapproject.webapp.domain;

import info.rmapproject.webapp.utils.Constants;
import info.rmapproject.webapp.utils.WebappUtils;

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
	private String type;
	
	public GraphNode(Integer id, String name, Integer weight, String type){
		setId(id);
		setType(type);
		setName(name);
		setWeight(weight);
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
		String shortname = name;
		if (this.type!=null && this.type.equals(Constants.NODETYPE_TYPE)){
			//for types see if we can do a short name
			shortname=WebappUtils.replaceNamespace(shortname);
		}
		if (shortname.length() > Constants.MAX_NODETEXT_LENGTH) {
			setShortname(shortname.substring(0, Constants.MAX_NODETEXT_LENGTH-3) + "...");
		}
		else {
			setShortname(shortname);			
		}
		
	}

	public Integer getWeight() {
		return weight;
	}

	public void setWeight(Integer weight) {
		this.weight = weight;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getShortname() {
		return shortname;
	}

	public void setShortname(String shortname) {
		this.shortname = shortname;
	}	
	
}
