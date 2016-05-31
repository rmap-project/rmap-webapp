package info.rmapproject.webapp.domain;

import info.rmapproject.webapp.utils.ConfigUtils;
import info.rmapproject.webapp.utils.Constants;

import java.util.Map;

/**
 * Holds a node type. For example name="Physical_Object", displayName="Physical Object", color="#11111"
 * @author khanson
 *
 */
public class GraphNodeType {

	private static final String NODETYPE_PROPFILE = "nodetypes";
	private static Map<String, String> colors = ConfigUtils.getPropertyValues(NODETYPE_PROPFILE);
		
	private String name = "";
	
	private String displayName = "";
	
	private String color = "";
	
	private String shape = "";

	public GraphNodeType(String name){
		//just pass in name, the rest is constructed using configuration
		if (name!=null){
			this.name=name;
			this.displayName=name.replace("_", " ");

			String nodeProps = colors.get(name);
			if (nodeProps!=null && nodeProps.contains("|")) {
				String[] props = nodeProps.split("\\|");
				this.color = props[0];
				this.shape = props[1];
			} else { //set defaults
				this.color=Constants.DEFAULT_NODE_COLOR;
				this.shape=Constants.DEFAULT_NODE_SHAPE;
			}		
		}
	}
	
	public String getName() {
		return name;
	}
	public String getDisplayName() {
		return displayName;
	}
	public String getColor() {
		return color;
	}
	public String getShape() {
		return shape;
	}
	
}
