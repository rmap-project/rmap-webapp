package info.rmapproject.webapp.model;

public enum NodeType {
	DISCO("DISCO"), 
	DATASET("DATASET"), 
	AGENT("AGENT"), 
	TEXT("TEXT"), 
	CODE("CODE"), 
	LITERAL("LITERAL"), 
	UNDEFINED("UNDEFINED"), 
	TYPE("TYPE");

	private final String nodeType;

	private NodeType (String nodeType) {
		this.nodeType = nodeType;
	}
	public String getNodeType()  {
		return nodeType;
	}
	

}
