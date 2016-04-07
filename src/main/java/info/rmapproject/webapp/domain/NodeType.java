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
	PHYSICALOBJECT("dcmitype:PhysicalObject"), 
	LITERAL, 
	UNDEFINED, 
	TYPE;

	//TODO:move namespace and type configurations to a properties file
	//TODO:consider other categories - Still image, Moving image, Audio, Physical Object
	
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
	
}
