package info.rmapproject.webapp.domain;

import info.rmapproject.webapp.utils.WebappUtils;

import java.net.URI;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public enum NodeType {
	DISCO("rmap:DiSCO"), 
	DATASET("fabio:Dataset"), 
	AGENT("foaf:Agent", "foaf:Person", "foaf:Organization", "rmap:Agent", "dcterms:Agent"), 
	TEXT("dcmitype:Text", "fabio:ConferencePaper", "fabio:JournalArticle"), 
	CODE("dcmitype:Software", "fabio:Algorithm"), 
	LITERAL, 
	UNDEFINED, 
	TYPE;

	
	private final List<String> values;

    NodeType(String ...values) {
        this.values = Arrays.asList(values);
    }

    public List<String> getValues() {
        return values;
    }
	
	
    public static NodeType selectNodeType(String rdfType) {
        for (NodeType nodeType : NodeType.values()) {
            if (nodeType.getValues().contains(rdfType)) {
                return nodeType;
            }
        }
        return UNDEFINED;
    }
    
    
    public static NodeType selectNodeType(Set<String> rdfTypes) {
        for (String rdfType : rdfTypes) {
           NodeType nodeType = selectNodeType(rdfType);
           if (nodeType!=UNDEFINED){
        	   return nodeType;
           }
        }
        return UNDEFINED;
    }
    
    	
	/**
	 * Select an appropriate NodeType from the list of types provided.
	 * @param resourceUri
	 * @param contextUri
	 * @return
	 * @throws Exception
	 */
	public static NodeType getNodeType(Set<URI>rdfTypes) throws Exception{
	
		if (rdfTypes!=null){
			Set <String> rdfTypeStrings = new HashSet<String>();
	
			for (URI type : rdfTypes){
				rdfTypeStrings.add(WebappUtils.replaceNamespace(type.toString()));
			}
	
			NodeType nodeType = selectNodeType(rdfTypeStrings);
			return nodeType;
		}
		else {
			return UNDEFINED;
		}
		
	}
	
	//TODO: types should be configured in the properties.
	/*public static NodeType selectNodeType(Set<String> rdfTypes) throws Exception{
		NodeType nodeType = UNDEFINED;
		
		if (rdfTypes.contains("rmap:DiSCO")) {
			nodeType = DISCO;
		}
		else if (rdfTypes.contains("fabio:Dataset")){
			nodeType = DATASET;					
		}
		else if (rdfTypes.contains("dcmitype:Software")
				|| rdfTypes.contains("fabio:Algorithm")){
			nodeType = CODE;					
		}
		else if (rdfTypes.contains("foaf:Agent")
				|| rdfTypes.contains("foaf:Person")
				|| rdfTypes.contains("foaf:Organization")
				|| rdfTypes.contains("rmap:Agent")
				|| rdfTypes.contains("dcterms:Agent")){
			nodeType = AGENT;					
		}	
		else if (rdfTypes.contains("dcmitype:Text")
				|| rdfTypes.contains("fabio:JournalArticle")
				|| rdfTypes.contains("fabio:ConferencePaper")){
			nodeType = TEXT;					
		}
		
		return nodeType;
	}*/
	

}
