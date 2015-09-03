package info.rmapproject.webapp.model;

import info.rmapproject.core.model.RMapTriple;
import info.rmapproject.core.model.RMapValue;
import info.rmapproject.webapp.utils.WebappUtils;

import java.net.URI;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.openrdf.model.vocabulary.RDF;

public class GraphParts {

    private Set<String> uniqueNodes;
    private List<GraphNode> nodes;
    private List<GraphEdge> edges;
    private Integer counter = 0;

	public GraphParts(){}
	
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
	
	public void setUniqueNodes(Set<String> uniqueNodes) {
		this.uniqueNodes = uniqueNodes;
	}
	
	public Integer addNode(String sNode, NodeType nodeType) throws Exception{
		Integer id = 0;
		if (this.uniqueNodes == null){
			this.uniqueNodes = new HashSet<String>();
		}		
		if (this.nodes == null){
			this.nodes = new ArrayList<GraphNode>();
		}		
		
		if (!uniqueNodes.contains(sNode)) {
			id = addOne();
			uniqueNodes.add(sNode);	
			//TODO: quick fix for demo - need to do this properly respecting context, make types configurable and put this in a separate method
			nodes.add(new GraphNode(id, sNode, 10, nodeType));
		}
		else {
			//find matching node, add to weight
			for (GraphNode node:this.nodes) {
				if (node.getName().equals(sNode)){
					node.setWeight(node.getWeight() + 10);
					id = node.getId();
				}
			}
		}
		return id;
	}
	
	public void addEdge(GraphEdge edge)	{
		if (this.edges == null) {
			this.edges = new ArrayList<GraphEdge>();
		}
		edges.add(edge);
	}
	
	public void addEdge(RMapTriple triple) throws Exception {
		RMapValue subject = triple.getSubject();
		String predicate = triple.getPredicate().toString();
		RMapValue object = triple.getObject();
		NodeType sourceNodeType = WebappUtils.getNodeType(subject);
		NodeType targetNodeType = null;
		if (predicate.equals(RDF.TYPE)) {
			targetNodeType = NodeType.TYPE; 
		}
		else {
			targetNodeType = WebappUtils.getNodeType(object);
		}
		addEdge(subject.toString(), object.toString(), predicate, sourceNodeType, targetNodeType);
	}	

	public void addEdge(RMapTriple triple, URI context) throws Exception {
		RMapValue subject = triple.getSubject();
		String predicate = triple.getPredicate().toString();
		RMapValue object = triple.getObject();
		NodeType sourceNodeType = WebappUtils.getNodeType(subject, context);
		NodeType targetNodeType = null;
		if (predicate.equals(RDF.TYPE)) {
			targetNodeType = NodeType.TYPE; 
		}
		else {
			targetNodeType = WebappUtils.getNodeType(object, context);
		}
		addEdge(subject.toString(), object.toString(), predicate, sourceNodeType, targetNodeType);
	}	
		
	public void addEdge(String sourceKey, String targetKey, String label, 
							NodeType sourceNodeType, NodeType targetNodeType) throws Exception {
		GraphEdge edge = new GraphEdge();
		targetKey = targetKey.replaceAll("[\n\r]", "");
		targetKey = targetKey.replaceAll("[ ]+", " ");
		Integer source = addNode(WebappUtils.replaceNamespace(sourceKey), sourceNodeType);
		Integer target = addNode(WebappUtils.replaceNamespace(targetKey), targetNodeType);
		edge.setLabel(WebappUtils.replaceNamespace(label));
		edge.setSource(source);
		edge.setTarget(target);
		edge.setTargetNodeType(targetNodeType);
		addEdge(edge);
	}	
		
	private Integer addOne(){
		this.counter = this.counter+1;
		return this.counter;
	}
	
}
