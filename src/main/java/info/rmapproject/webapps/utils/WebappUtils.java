package info.rmapproject.webapps.utils;

import info.rmapproject.core.rmapservice.RMapService;
import info.rmapproject.core.rmapservice.RMapServiceFactoryIOC;
import info.rmapproject.core.rmapservice.impl.openrdf.vocabulary.BIBO;
import info.rmapproject.core.rmapservice.impl.openrdf.vocabulary.ORE;
import info.rmapproject.core.rmapservice.impl.openrdf.vocabulary.PROV;
import info.rmapproject.core.rmapservice.impl.openrdf.vocabulary.RMAP;

import java.net.URI;

import org.openrdf.model.vocabulary.DC;
import org.openrdf.model.vocabulary.DCTERMS;
import org.openrdf.model.vocabulary.FOAF;
import org.openrdf.model.vocabulary.RDF;
import org.openrdf.model.vocabulary.RDFS;

public class WebappUtils {

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
		else {
			return url;
		}
	}
	
	public static String getRMapType(URI uriResourceUri){

		RMapService rmapService = RMapServiceFactoryIOC.getFactory().createService();
		
		try {
			rmapService.getDiSCOStatus(uriResourceUri);
			rmapService.closeConnection();
			return "disco"; 
		}
		catch (Exception e) {
			//not a DiSCO
		}

		try {
			rmapService.getAgentStatus(uriResourceUri);
			rmapService.closeConnection();
			return "agent"; 
		}
		catch (Exception e) {
			//not an Agent
		}

		try {
			rmapService.getStatementEvents(uriResourceUri);
			rmapService.closeConnection();
			return "stmt";
		}
		catch (Exception e) {
			//not a Statement
		}
		
		try {
			rmapService.readEvent(uriResourceUri);
			rmapService.closeConnection();
			return "event";
		}
		catch (Exception e) {
			//not an Event
		}
		
		return "";
				
	}
	
	
	
	
}
