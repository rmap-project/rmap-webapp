package info.rmapproject.webapp.service;

import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;
import info.rmapproject.webapp.service.dto.AgentDTO;
import info.rmapproject.webapp.service.dto.DiSCODTO;
import info.rmapproject.webapp.service.dto.EventDTO;

import java.net.URI;
import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@RunWith( SpringJUnit4ClassRunner.class )
@ContextConfiguration({ "classpath:/servlet-test-context.xml" })
public class DataDisplayServiceImplTest {

	@Autowired
	private DataDisplayService dataDisplayService;
	
	@SuppressWarnings("unused")
	@Test
	public void testGetDiSCODTO() {
		String discoId = "ark:/22573/rmd18n8xfs";
		
		//String discoId = "ark:/27927/rmp1825qnv";
		try{
			DiSCODTO discoDTO = dataDisplayService.getDiSCODTO(discoId);
			List <URI> agentvers = discoDTO.getAgentVersions();
			List <URI> allvers = discoDTO.getOtherAgentVersions();
			List <URI> othervers = discoDTO.getAllVersions();
			//assertTrue(discoDTO.getAgentVersions().size()==5);
		} catch (Exception e) {
			fail(e.getMessage());
		}
	}

	@Test
	public void testGetAgentDTO()  {
		String agentId = "ark:/22573/rmaptestagent";
		try{
			AgentDTO agentDTO = dataDisplayService.getAgentDTO(agentId);
			assertTrue(agentDTO.getName().equals("RMap test Agent"));
		} catch (Exception e) {
			fail(e.getMessage());
		}
	}

	@Test
	public void testGetEventDTO() {
		//https://dev.rmap-project.org/appdev/events/ark%3A%2F22573%2Frmd1c022jv

		String eventId = "ark:/22573/rmd1c022jv";
		try{
			EventDTO eventDTO = dataDisplayService.getEventDTO(eventId);
			assertTrue(eventDTO.getType()!=null);
		} catch (Exception e) {
			fail(e.getMessage());
		}
	}

	@Test
	public void testGetResourceDTO() {
		fail("Not yet implemented");
	}

}
