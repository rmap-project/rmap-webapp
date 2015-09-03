package info.rmapproject.webapp.utils;

import info.rmapproject.core.model.RMapLiteral;
import info.rmapproject.core.model.RMapValue;
import info.rmapproject.core.rmapservice.RMapService;
import info.rmapproject.core.rmapservice.RMapServiceFactoryIOC;
import info.rmapproject.core.rmapservice.impl.openrdf.vocabulary.BIBO;
import info.rmapproject.core.rmapservice.impl.openrdf.vocabulary.ORE;
import info.rmapproject.core.rmapservice.impl.openrdf.vocabulary.PROV;
import info.rmapproject.core.rmapservice.impl.openrdf.vocabulary.RMAP;
import info.rmapproject.webapp.model.NodeType;

import java.net.URI;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import org.openrdf.model.vocabulary.DC;
import org.openrdf.model.vocabulary.DCTERMS;
import org.openrdf.model.vocabulary.FOAF;
import org.openrdf.model.vocabulary.RDF;
import org.openrdf.model.vocabulary.RDFS;

public class WebappUtils {

	/**
	 * Replace the namespace URL with something more readable
	 * @param url
	 * @return
	 */
	public static String replaceNamespace(String url) {
		//TODO: make these into a properties file
		if (url.contains(RDF.NAMESPACE))	{
				return url.replace(RDF.NAMESPACE,"rdf:");
		}
		else if (url.contains(RDFS.NAMESPACE))	{
			return url.replace(RDFS.NAMESPACE,"rdfs:");
		}
		else if (url.contains(RMAP.NAMESPACE))	{
			return url.replace(RMAP.NAMESPACE,"rmap:");
		}
		else if (url.contains(PROV.NAMESPACE))	{
			return url.replace(PROV.NAMESPACE,"prov:");			
		}
		else if (url.contains(ORE.NAMESPACE))	{
			return url.replace(ORE.NAMESPACE,"ore:");	
		}
		else if (url.contains(FOAF.NAMESPACE))	{
			return url.replace(FOAF.NAMESPACE,"foaf:");	
		}
		else if (url.contains(DC.NAMESPACE))	{
			return url.replace(DC.NAMESPACE,"dc:");	
		}
		else if (url.contains(DCTERMS.NAMESPACE))	{
			return url.replace(DCTERMS.NAMESPACE,  "dcterms:");			
		}
		else if (url.contains(BIBO.NAMESPACE))	{
			return url.replace(BIBO.NAMESPACE,  "bibo:");		
		}
		else if (url.contains("http://purl.org/spar/cito/")) {
			return url.replace("http://purl.org/spar/cito/",  "cito:");		
		}
		else if (url.contains("http://purl.org/spar/pso/")) {
			return url.replace("http://purl.org/spar/pso/",  "pso:");		
		}
		else if (url.contains("http://purl.org/vocab/frbr/core#")) {
			return url.replace("http://purl.org/vocab/frbr/core#",  "frbr:");		
		}
		else if (url.contains("http://www.loc.gov/mods/modsrdf/v1#")) {
			return url.replace("http://www.loc.gov/mods/modsrdf/v1#",  "modsrdf:");		
		}
		else if (url.contains("http://www.loc.gov/premis/rdf/v1#")) {
			return url.replace("http://www.loc.gov/premis/rdf/v1#", "premis:");						
		}
		else if (url.contains("http://purl.org/spar/scoro/")) {
			return url.replace("http://purl.org/spar/scoro/", "scoro:");
		}
		else if (url.contains("http://purl.org/spar/fabio/")) {
			return url.replace("http://purl.org/spar/fabio/","fabio:");						
		}
		else if (url.contains("http://prismstandard.org/namespaces/basic/2.0/")) {
			return url.replace("http://prismstandard.org/namespaces/basic/2.0/", "prism:");
		}
		else if (url.contains("http://purl.org/dc/dcmitype/")) {
			return url.replace("http://purl.org/dc/dcmitype/", "dcmitype:");
		}
		else {
			return url;
		}
	}
	
	/**
	 * Determine whether the URI provided is an RMap object, or just a regular resource.
	 * @param uriResourceUri
	 * @return
	 * @throws Exception
	 */
	public static String getRMapType(URI uriResourceUri) throws Exception{

		RMapService rmapService = RMapServiceFactoryIOC.getFactory().createService();
		Map <URI, Set<URI>> types = rmapService.getResourceRdfTypesAllContexts(uriResourceUri);
		rmapService.closeConnection();
		
		if (types != null) {
			for (Map.Entry<URI, Set<URI>> type : types.entrySet()){
				Set<URI> contexttypes = type.getValue();
				for (URI contexttype : contexttypes) {
					if (contexttype.toString().equals(RMAP.DISCO.toString())) {
						return "disco";
					}
					else if (contexttype.toString().equals(RMAP.AGENT.toString())) {
						return "agent";
					}
					else if (contexttype.toString().equals(RMAP.EVENT.toString())) {
						return "event";
					}
				}
			}
		}
		//otherwise
		return "";
	}
	
	/**
	 * Get a NodeType to aid parsing the display of the graph - limited by context... so types must come from within context.
	 * @param value
	 * @param contextUri
	 * @return NodeType
	 * @throws Exception
	 */
	public static NodeType getNodeType(RMapValue value, URI contextUri) throws Exception{
		NodeType nodeType = NodeType.UNDEFINED;
		if (value instanceof RMapLiteral) {
			nodeType = NodeType.LITERAL;
		}
		else {
			nodeType = getNodeType(new URI(value.toString()), contextUri);
		}
		return nodeType;
	}
	
	/**
	 * Get a NodeType to aid parsing the display of the graph - not limited by context.
	 * @param value
	 * @param contextUri
	 * @return NodeType
	 * @throws Exception
	 */
	public static NodeType getNodeType(RMapValue value) throws Exception{
		NodeType nodeType = NodeType.UNDEFINED;
		if (value instanceof RMapLiteral) {
			nodeType = NodeType.LITERAL;
		}
		else {
			nodeType = getNodeType(new URI(value.toString()));
		}
		return nodeType;
	}
	
	
	/**
	 * Get a NodeType to aid parsing the display of the graph - limited by context... so types must come from within context.
	 * @param resourceUri
	 * @param contextUri
	 * @return
	 * @throws Exception
	 * (QUICK FIX FOR DEMO - NEED TO DO THIS PROPERLY!)
	 */
	public static NodeType getNodeType(URI resourceUri, URI contextUri) throws Exception{

		RMapService rmapService = RMapServiceFactoryIOC.getFactory().createService();
		Set <URI> rdfTypes = rmapService.getResourceRdfTypes(resourceUri, contextUri);

		if (rdfTypes!=null){
			Set <String> rdfTypeStrings = new HashSet<String>();
	
			for (URI type : rdfTypes){
				rdfTypeStrings.add(replaceNamespace(type.toString()));
			}

			NodeType nodeType = selectNodeType(rdfTypeStrings);
			return nodeType;
		}
		else {
			return NodeType.UNDEFINED;
		}
		
	}
	
	/**
	 * Get a NodeType to aid parsing the display of the graph - not limited by context - used for general resource links
	 * @param resourceUri
	 * @return
	 * @throws Exception
	 * (QUICK FIX FOR DEMO - NEED TO DO THIS PROPERLY!)
	 */
	public static NodeType getNodeType(URI resourceUri) throws Exception{
		
		RMapService rmapService = RMapServiceFactoryIOC.getFactory().createService();
		Map <URI, Set<URI>> types = rmapService.getResourceRdfTypesAllContexts(resourceUri);

		if (types!=null){
			Set <String> rdfTypes = new HashSet<String>();
	
			for (Map.Entry<URI, Set<URI>> type : types.entrySet()){
				Set<URI> contexttypes = type.getValue();
				for (URI contexttype : contexttypes) {
					if (contexttype!=null) {
						rdfTypes.add(replaceNamespace(contexttype.toString()));
					}
				}
			}
			NodeType nodeType = selectNodeType(rdfTypes);
			return nodeType;
		}
		else {
			return NodeType.UNDEFINED;
		}
	}

	//TODO: types should be configured in the properties.
	public static NodeType selectNodeType(Set<String> rdfTypes) throws Exception{
		NodeType nodeType = NodeType.UNDEFINED;
		
		if (rdfTypes.contains("rmap:DiSCO")) {
			nodeType = NodeType.DISCO;
		}
		else if (rdfTypes.contains("fabio:Dataset")){
			nodeType = NodeType.DATASET;					
		}
		else if (rdfTypes.contains("dcmitype:Software")
				|| rdfTypes.contains("fabio:Algorithm")){
			nodeType = NodeType.CODE;					
		}
		else if (rdfTypes.contains("foaf:Agent")
				|| rdfTypes.contains("foaf:Person")
				|| rdfTypes.contains("foaf:Organization")
				|| rdfTypes.contains("rmap:Agent")
				|| rdfTypes.contains("dcterms:Agent")){
			nodeType = NodeType.AGENT;					
		}	
		else if (rdfTypes.contains("dcmitype:Text")
				|| rdfTypes.contains("fabio:JournalArticle")
				|| rdfTypes.contains("fabio:ConferencePaper")){
			nodeType = NodeType.TEXT;					
		}
		
		return nodeType;
	}
	
	
/**
 * Get a list of links for all RDF types associated with the resource
 * @param uriResourceUri
 * @return
 * @throws Exception
 * (NOT CURRENTLY USED, LEAVING IT HERE IN CASE WE DECIDE TO USE IT ANYWHERE)
 */
public static Map<URI,String> getAllRdfTypes(URI uriResourceUri) throws Exception{

	RMapService rmapService = RMapServiceFactoryIOC.getFactory().createService();
	Map <URI, Set<URI>> types = rmapService.getResourceRdfTypesAllContexts(uriResourceUri);
	rmapService.closeConnection();
	
	Map <URI, String> allRdfTypes = new HashMap<URI, String>();

	for (Map.Entry<URI, Set<URI>> type : types.entrySet()){
		Set<URI> contexttypes = type.getValue();
		for (URI contexttype : contexttypes) {
			if (contexttype!=null && !allRdfTypes.containsKey(contexttype)) {
				String link = "<a href=\"" + contexttype.toString() + "\">" 
								+ replaceNamespace(contexttype.toString()) + "</a>";
				allRdfTypes.put(contexttype, link);
			}
		}
	}
	return allRdfTypes;
}
	
	
}
