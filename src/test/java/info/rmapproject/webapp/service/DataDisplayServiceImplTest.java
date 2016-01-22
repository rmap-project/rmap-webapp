package info.rmapproject.webapp.service;

import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;
import info.rmapproject.webapp.service.dto.AgentDTO;

import org.junit.Test;

public class DataDisplayServiceImplTest {

	@Test
	public void testGetDiSCODTO() {
		fail("Not yet implemented");
	}

	@Test
	public void testGetAgentDTO()  {
		String agentId = "ark:/22573/rmaptestagent";
		DataDisplayService dataDisplayService = new DataDisplayServiceImpl();
		try{
			AgentDTO agentDTO = dataDisplayService.getAgentDTO(agentId);
			assertTrue(agentDTO.getName().equals("RMap test Agent"));
		} catch (Exception e) {
			fail(e.getMessage());
		}
	}

	@Test
	public void testGetEventDTO() {
		fail("Not yet implemented");
	}

	@Test
	public void testGetResourceDTO() {
		fail("Not yet implemented");
	}

}
