package info.rmapproject.webapp.model;

import java.io.Serializable;

public class GraphNode implements Serializable{

	public GraphNode(){
	}
	
	private static final long serialVersionUID = 1L;
    private static Integer MAX_NODETEXT_LENGTH = 30;
	private Integer id;
	private String name;
	private String shortname; 
	private Integer weight;
	private Boolean isUri;
	private Boolean isDiSCO;
	
	public GraphNode(Integer id, String name, Integer weight, Boolean isUri, Boolean isDiSCO){
		setId(id);
		setName(name);
		setWeight(weight);
		setIsUri(isUri);
		setIsDiSCO(isDiSCO);
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

	public Boolean getIsUri() {
		return isUri;
	}

	public void setIsUri(Boolean isUri) {
		this.isUri = isUri;
	}

	public Boolean getIsDiSCO() {
		return isDiSCO;
	}

	public void setIsDiSCO(Boolean isDiSCO) {
		this.isDiSCO = isDiSCO;
	}

	public String getShortname() {
		return shortname;
	}

	public void setShortname(String shortname) {
		this.shortname = shortname;
	}	
	
}
