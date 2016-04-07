package info.rmapproject.webapp.controllers;

import info.rmapproject.webapp.service.DataDisplayService;
import info.rmapproject.webapp.service.dto.DiSCODTO;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@RunWith( SpringJUnit4ClassRunner.class )
@ContextConfiguration({ "classpath:/servlet-test-context.xml" })
public class DiSCOControllerTest {

	@Autowired
	private DataDisplayService dataDisplayService;
	
	@SuppressWarnings("unused")
	@Test
	public void testDisco() throws Exception{
		String discoUri = "ark:/22573/rmd18mddgf";
		DiSCODTO discoDTO = dataDisplayService.getDiSCODTO(discoUri);
	}

}
