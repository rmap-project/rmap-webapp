package info.rmapproject.webapp.domain;

import info.rmapproject.webapp.utils.Constants;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * Holds a Graph description
 * @author khanson
 *
 */
public class Graph {

	/** Unique list of nodes stored to prevent duplicate nodes being added */
    private Set<String> uniqueNodes;  
	/** List of nodes */
    private List<GraphNode> nodes;
	/** List of edges */
    private List<GraphEdge> edges;
    
    /**Unique list of node types to prevent duplicate types**/
    private Set<String> uniqueNodeTypes;
    
	/** List of node types */
    private List<GraphNodeType> nodeTypes;
        
    //Each node must be assigned a number that is unique within the context of the graph
    //The counter keeps track of an incrementing number that is assigned to each node as it is added to the graph
    private Integer counter = 0; 

	public Graph(){
		//initiate lists.
		this.uniqueNodes = new HashSet<String>();
		this.uniqueNodeTypes = new HashSet<String>();
		this.nodes = new ArrayList<GraphNode>();
		this.edges = new ArrayList<GraphEdge>();
		this.nodeTypes = new ArrayList<GraphNodeType>();
	}
	
	public List<GraphNode> getNodes() {
		return nodes;
	}
	
	public void setNodes(List<GraphNode> nodes) {
		this.nodes = nodes;
	}
	
	public List<GraphEdge> getEdges() {
		return edges;
	}
	
	public void setEdges(List<GraphEdge> edges) {
		this.edges = edges;
	}
    
	public Set<String> getUniqueNodes() {
		return uniqueNodes;
	}
	
	public List<GraphNodeType> getNodeTypes() {
		return nodeTypes;
	}
	
	/**
	 * Creates GraphNode object and adds it to the Graph
	 * @param sNode
	 * @param nodeType
	 * @return
	 * @throws Exception
	 */
	public Integer addNode(String sNode, String nodeType) throws Exception{
		Integer id = 0;
		if (!uniqueNodes.contains(sNode)) {
			id = getNextId();
			uniqueNodes.add(sNode);	
			nodes.add(new GraphNode(id, sNode, Constants.NODE_WEIGHT_INCREMENT, nodeType));
			addNodeType(nodeType); //only if it's a new value
		}
		else {
			//find matching node, add to weight
			for (GraphNode node:this.nodes) {
				if (node.getName().equals(sNode)){
					node.setWeight(node.getWeight() + Constants.NODE_WEIGHT_INCREMENT);
					id = node.getId();
				}
			}
		}
		return id;
	}
	
	public void addEdge(GraphEdge edge)	{
		edges.add(edge);
	}
			
	/**
	 * Creates GraphEdge object and adds it to the Graph
	 * @param sourceKey
	 * @param targetKey
	 * @param label
	 * @param sourceNodeType
	 * @param targetNodeType
	 * @throws Exception
	 */
	public void addEdge(String sourceKey, String targetKey, String label, 
							String sourceNodeType, String targetNodeType) throws Exception {
		GraphEdge edge = new GraphEdge();
		targetKey = targetKey.replaceAll("[\n\r]", "");
		targetKey = targetKey.replaceAll("[ ]+", " ");
		Integer source = addNode(sourceKey, sourceNodeType);
		Integer target = addNode(targetKey, targetNodeType);
		edge.setLabel(label);
		edge.setSource(source);
		edge.setTarget(target);
		edge.setTargetNodeType(targetNodeType);
		addEdge(edge);
	}	
		
	private Integer getNextId(){
		this.counter = this.counter+1;
		return this.counter;
	}

	/**
	 * Add type node to list, but only if it's not already in there
	 * @param nodeType
	 */
	private void addNodeType(String sType) {
		if (sType!=null && sType.length()>0
				&& !uniqueNodeTypes.contains(sType)){
			GraphNodeType type = new GraphNodeType(sType);
			this.nodeTypes.add(type);
			this.uniqueNodeTypes.add(sType);//for detecting duplicates
		}
	}

}
