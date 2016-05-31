package info.rmapproject.webapp.utils;

import static org.junit.Assert.assertTrue;

import java.net.URI;
import java.util.HashSet;
import java.util.Set;

import org.junit.Test;

public class WebappUtilsTest {

	@Test
	public void testOntologyPrefixes(){
		String url = "http://rmap-project.org/rmap/terms/DiSCO";
		String newUrl = WebappUtils.replaceNamespace(url);
		assertTrue(newUrl.equals("rmap:DiSCO"));

		url = "http://purl.org/vocab/frbr/core#test";
		newUrl = WebappUtils.replaceNamespace(url);
		assertTrue(newUrl.equals("frbr:test"));
	}
	

	@Test
	public void testNodeTypeRetrieval() throws Exception{
		String url = "http://rmap-project.org/rmap/terms/DiSCO";
		String nodeType = WebappUtils.getNodeType(new URI(url));
		assertTrue(nodeType.equals("DiSCO"));

		Set<URI> uris = new HashSet<URI>();
		uris.add(new URI("http://rmap-project.org/rmap/terms/DiSCO"));
		uris.add(new URI("http://purl.org/dc/terms/Agent"));
		uris.add(new URI("http://purl.org/dc/dcmitype/Text"));
		uris.add(new URI("http://purl.org/spar/fabio/JournalArticle"));
		nodeType = WebappUtils.getNodeType(uris);
		assertTrue(nodeType.equals("Text"));
	}
	
	
}
