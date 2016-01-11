package info.rmapproject.webapp.service;

import info.rmapproject.auth.model.AgentType;

import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class UserServiceImplTest {
	//@Autowired
	UserMgtService userMgtservice;

	@Before
	public void setup(){
		//@SuppressWarnings("resource")
		ApplicationContext context = new ClassPathXmlApplicationContext("servlet-context.xml");
		userMgtservice = (UserMgtService)context.getBean("userMgtService", UserMgtService.class);    
		//userservice = new UserServiceImpl();
	}
	
	
	@Test
	public void testGetAgentTypes() {
		@SuppressWarnings("unused")
		List<AgentType> agentTypes = userMgtservice.getAgentTypes();
		
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
