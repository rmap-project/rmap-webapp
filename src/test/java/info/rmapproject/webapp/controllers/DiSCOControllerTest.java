package info.rmapproject.webapp.controllers;

import info.rmapproject.webapp.service.DataDisplayService;
import info.rmapproject.webapp.service.DataDisplayServiceImpl;
import info.rmapproject.webapp.service.dto.DiSCODTO;

import org.junit.Test;

public class DiSCOControllerTest {

	@SuppressWarnings("unused")
	@Test
	public void testDisco() throws Exception{
		String discoUri = "ark:/22573/rmd18mddgf";
		DataDisplayService dataDisplayService = new DataDisplayServiceImpl();
		DiSCODTO discoDTO = dataDisplayService.getDiSCODTO(discoUri);
	}

}
