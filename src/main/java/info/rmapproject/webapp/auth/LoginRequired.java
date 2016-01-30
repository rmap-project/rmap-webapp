package info.rmapproject.webapp.auth;

import java.lang.annotation.ElementType; 
import java.lang.annotation.Retention; 
import java.lang.annotation.RetentionPolicy; 
import java.lang.annotation.Target; 

/**
 * Custom annotation to label Controllers that require a login.
 * If this annotation is added to a controller, and the user has not logged in, 
 * it will redirect to the /user/login page.
 * @author khanson
 *
 */
@Retention(RetentionPolicy.RUNTIME) 
@Target(ElementType.METHOD) 
public @interface LoginRequired{ 
}