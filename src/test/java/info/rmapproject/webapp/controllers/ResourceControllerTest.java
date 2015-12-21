package info.rmapproject.webapp.controllers;

import info.rmapproject.webapp.service.DataDisplayService;
import info.rmapproject.webapp.service.DataDisplayServiceImpl;
import info.rmapproject.webapp.service.dto.ResourceDTO;

import org.junit.Test;

public class ResourceControllerTest {

	@SuppressWarnings("unused")
	@Test
	public void testResource() throws Exception{

		//WARNING: NOT A PROPER TEST JUST A COPY OF THE CODE!
		
		String resourceUri = "http://dx.doi.org/10.1109/InPar.2012.6339604";
		DataDisplayService dataDisplayService = new DataDisplayServiceImpl();
		ResourceDTO resourceDTO = dataDisplayService.getResourceDTO(resourceUri);
		
	}

}
