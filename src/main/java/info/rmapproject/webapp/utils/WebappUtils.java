package info.rmapproject.webapp.utils;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

public class WebappUtils {

	private static final String PREFIX_PROPFILE = "ontologyprefixes";
	private static final String TYPEMAPPINGS_PROPFILE = "typemappings";

	private static Map<String, String> prefixes = ConfigUtils.getPropertyValues(PREFIX_PROPFILE);
	private static Map<String, String> typeMappings = ConfigUtils.getPropertyValues(TYPEMAPPINGS_PROPFILE);
	
	/**
	 * Replace the namespace URL with something more readable
	 * @param url
	 * @return
	 */
	public static String replaceNamespace(String url) {
		try{
			URI uri = new URI(url);
			String path = null;
			String term = null;

			if (url.contains("#")){
				term = uri.getFragment();
				path = url.substring(0,url.lastIndexOf("#")+1);
			}
			else if (url.contains("/") && path==null){
				term = url.substring(url.lastIndexOf("/")+1);
				path=url.substring(0,url.lastIndexOf("/")+1);
			}
			
			if (term!=null && path!=null && term.length()>0 && path.length()>0) {
				String prefix = prefixes.get(path);
				if (prefix!=null && prefix.length()>0){
					url = prefix + ":" + term;
				} else {
					url = "x" + ":" + term;
				}
			}
			return url;
		} catch (URISyntaxException e){
			//it's not a uri... that's OK, send it back...
			return url;
		}
	}	
	
	/**
	 * Retrieve the node type based on URI provided.  The graph visualization is colored based on 
	 * node type.  Node types also appear in the legend.
	 * @param type
	 * @return
	 */
	public static String getNodeType(URI type){
		if (type==null){return Constants.NODETYPE_UNDEFINED;}
		String nodeType = null;
		nodeType = typeMappings.get(type.toString());
		if (nodeType==null){
			nodeType = Constants.NODETYPE_UNDEFINED;
		}
		return nodeType;
	}
	
	
	/**
	 * Based on the list of URIs provided, select the most common.  The graph visualization is colored based on 
	 * node type.  Node types also appear in the legend.
	 * @param type
	 * @return
	 */
	public static String getNodeType(Set<URI> types){
		if (types==null || types.size()==0){
			return Constants.NODETYPE_UNDEFINED;
		}
		String nodeType = null;
		Map<String, Integer> typemap = new HashMap<String, Integer>();
		for (URI type:types){
			String thisNodeType = getNodeType(type);
			if (!thisNodeType.equals(Constants.NODETYPE_UNDEFINED)){
				if (typemap.containsKey(thisNodeType)) {
					//increment count
					typemap.put(thisNodeType, typemap.get(thisNodeType)+1);
				} else {
					typemap.put(thisNodeType,  1);
				}
			}
		}
		if (typemap.size()>0){
			//figure out which is most common type
			Integer highestCount=0;
			for (Map.Entry<String, Integer> entry : typemap.entrySet()) {
			    if (entry.getValue()>highestCount){
			    	nodeType = entry.getKey();
			    	highestCount = entry.getValue();
			    }			    
			}
		}
		if (nodeType != null) {
			return nodeType;
		} else {
			return Constants.NODETYPE_UNDEFINED;
		}
	}
	
}
