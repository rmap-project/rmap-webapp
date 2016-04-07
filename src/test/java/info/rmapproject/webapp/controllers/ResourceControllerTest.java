package info.rmapproject.webapp.controllers;

import info.rmapproject.webapp.service.DataDisplayService;
import info.rmapproject.webapp.service.dto.ResourceDTO;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@RunWith( SpringJUnit4ClassRunner.class )
@ContextConfiguration({ "classpath:/servlet-test-context.xml" })
public class ResourceControllerTest {

	@Autowired
	private DataDisplayService dataDisplayService;
	
	@SuppressWarnings("unused")
	@Test
	public void testResource() throws Exception{

		//WARNING: NOT A PROPER TEST JUST A COPY OF THE CODE!
		
		String resourceUri = "http://dx.doi.org/10.1109/InPar.2012.6339604";	
		ResourceDTO resourceDTO = dataDisplayService.getResourceDTO(resourceUri);
		
	}

}
