package info.rmapproject.webapp.service;

import static org.junit.Assert.assertTrue;
import info.rmapproject.auth.model.AgentType;

import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class UserServiceImplTest {

	UserMgtService userMgtservice;

	@Before
	public void setup(){
		@SuppressWarnings("resource")
		//NOTE: This uses a local servlet-context.xml for testing!
		ApplicationContext context = new ClassPathXmlApplicationContext("servlet-context.xml");
		userMgtservice = (UserMgtService)context.getBean("userMgtService", UserMgtService.class);    
		//userservice = new UserServiceImpl();
	}
	
	
	@Test
	public void testGetAgentTypes() {
		List<AgentType> agentTypes = userMgtservice.getAgentTypes();
		assertTrue(agentTypes.size()>0);
	}

//	@Test
//	public void testAddApiKey() {
//		fail("Not yet implemented");
//	}
//
//	@Test
//	public void testUpdateApiKey() {
//		fail("Not yet implemented");
//	}
//
//	@Test
//	public void testGetApiKeyById() {
//		fail("Not yet implemented");
//	}
//
//	@Test
//	public void testListApiKeyByUser() {
//		fail("Not yet implemented");
//	}
//
//	@Test
//	public void testAddUser() {
//		fail("Not yet implemented");
//	}
//
//	@Test
//	public void testUpdateUserSettings() {
//		fail("Not yet implemented");
//	}
//
//	@Test
//	public void testGetUserById() {
//		fail("Not yet implemented");
//	}

}
