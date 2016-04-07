package info.rmapproject.webapp.service;

import org.junit.Before;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@RunWith( SpringJUnit4ClassRunner.class )
@ContextConfiguration({ "classpath:/servlet-test-context.xml" })
public class UserServiceImplTest {

	@Autowired
	UserMgtService userMgtservice;

	@Before
	public void setup(){
		//NOTE: This uses a local servlet-context.xml for testing! 
		//userservice = new UserServiceImpl();
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
