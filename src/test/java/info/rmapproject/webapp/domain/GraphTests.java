package info.rmapproject.webapp.domain;

import static org.junit.Assert.assertTrue;

import org.junit.Test;

public class GraphTests {

	@Test
	public void testGraphNodeTypeCreation(){
		GraphNodeType nodetype = new GraphNodeType("Physical_object");
		assertTrue(nodetype.getName().equals("Physical_object"));
		assertTrue(nodetype.getShape().equals("dot"));
		assertTrue(nodetype.getColor().equals("#996600"));
		
	}
}
