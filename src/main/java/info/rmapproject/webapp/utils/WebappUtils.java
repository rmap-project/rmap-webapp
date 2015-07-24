package info.rmapproject.webapp.utils;

import info.rmapproject.core.rmapservice.RMapService;
import info.rmapproject.core.rmapservice.RMapServiceFactoryIOC;
import info.rmapproject.core.rmapservice.impl.openrdf.vocabulary.BIBO;
import info.rmapproject.core.rmapservice.impl.openrdf.vocabulary.ORE;
import info.rmapproject.core.rmapservice.impl.openrdf.vocabulary.PROV;
import info.rmapproject.core.rmapservice.impl.openrdf.vocabulary.RMAP;

import java.net.URI;
import java.util.HashMap;
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
