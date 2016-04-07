package info.rmapproject.webapp.utils;

import info.rmapproject.core.vocabulary.impl.openrdf.ORE;
import info.rmapproject.core.vocabulary.impl.openrdf.PROV;
import info.rmapproject.core.vocabulary.impl.openrdf.RMAP;

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
		else if (url.contains("http://purl.org/ontology/bibo/"))	{
			return url.replace("http://purl.org/ontology/bibo/",  "bibo:");		
		}
		else if (url.contains("http://purl.org/spar/cito/")) {
			return url.replace("http://purl.org/spar/cito/",  "cito:");		
		}
		else if (url.contains("http://purl.org/spar/pso/")) {
			return url.replace("http://purl.org/spar/pso/",  "pso:");		
		}
		else if (url.contains("http://purl.org/spar/pro/")) {
			return url.replace("http://purl.org/spar/pro/",  "pro:");		
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
		else if (url.contains("http://www.w3.org/2006/vcard/ns#")) {
			return url.replace("http://www.w3.org/2006/vcard/ns#","vcard:");						
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
		
	
}
