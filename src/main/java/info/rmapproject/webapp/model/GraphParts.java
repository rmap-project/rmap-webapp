package info.rmapproject.webapp.model;

import info.rmapproject.core.model.RMapLiteral;
import info.rmapproject.core.model.RMapResource;
import info.rmapproject.core.model.RMapTriple;
import info.rmapproject.core.model.RMapValue;
import info.rmapproject.webapp.utils.WebappUtils;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class GraphParts {

	public GraphParts(){}

    private Set<String> uniqueNodes;
    private List<GraphNode> nodes;
    private List<GraphEdge> edges;
    private Integer counter = 0;

    private static Integer MAX_LITERAL_LENGTH = 30;
    
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
	
	public Integer addNode(String sNode, Boolean isUri){
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
			nodes.add(new GraphNode(id, sNode, 10, isUri));
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
	
	public void addEdge(RMapTriple triple)	{
		RMapResource subject = triple.getSubject();
		String predicate = triple.getPredicate().toString();
		String object = triple.getObject().toString();
		Boolean connectsUri = true;
		if (triple.getObject() instanceof RMapLiteral) {
			if (object.length()>MAX_LITERAL_LENGTH) {
				object = object.substring(0,MAX_LITERAL_LENGTH) + "...";
			}
			connectsUri = false;
		}
		addEdge(subject.toString(), object, predicate, connectsUri);
	}	
	
	
	public void addEdge(String sourceKey, RMapValue targetKey, String label)	{
		String sTargetKey = "";
		Boolean connectsUri = true;
		if (targetKey!=null && sourceKey!=null){
			sTargetKey= targetKey.toString();
			if (targetKey instanceof RMapLiteral) {
				if (targetKey.toString().length()>MAX_LITERAL_LENGTH) {
					sTargetKey = sTargetKey.substring(0,MAX_LITERAL_LENGTH) + "...";
				}
				connectsUri = false;
			}
			addEdge(sourceKey, sTargetKey, label, connectsUri);
		}
	}	
	
	public void addEdge(String sourceKey, String targetKey, String label, Boolean connectsUri)	{
		GraphEdge edge = new GraphEdge();
		targetKey = targetKey.replaceAll("[\n\r]", "");
		targetKey = targetKey.replaceAll("[ ]+", " ");
		Integer source = addNode(WebappUtils.replaceNamespace(sourceKey), true);
		Integer target = addNode(WebappUtils.replaceNamespace(targetKey), connectsUri);
		edge.setLabel(WebappUtils.replaceNamespace(label));
		edge.setSource(source);
		edge.setTarget(target);
		edge.setConnectsUri(connectsUri);
		addEdge(edge);
	}	
		
	private Integer addOne(){
		this.counter = this.counter+1;
		return this.counter;
	}
	
}
