package info.rmapproject.webapp.auth;

import info.rmapproject.auth.model.User;

import java.lang.reflect.Method;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import com.github.scribejava.core.model.Token;

public class AuthenticationInterceptor implements HandlerInterceptor {

//	private static final Logger logger = LoggerFactory.getLogger(AuthenticationInterceptor.class);
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {

    	HandlerMethod hm=(HandlerMethod)handler; 
    	Method method=hm.getMethod(); 
    	
    	if(method.getDeclaringClass().isAnnotationPresent(Controller.class)){ 
    		if(method.isAnnotationPresent(LoginRequired.class)) { 
    	        Token accessToken = (Token) request.getSession().getAttribute("accesstoken");
	    		if(accessToken == null) {
	    			response.sendRedirect(request.getContextPath() + "/user/login");
	    			return false;
	    		}   
	    		
	    		User user = (User) request.getSession().getAttribute("user");
	    		if((!method.getName().equals("signupForm")
	    				&&!method.getName().equals("addUser")) 
	    				&& (user == null || user.getUserId()==0)) {
	    			//new user, get them signed up!
	    			response.sendRedirect(request.getContextPath() + "/user/signup");
	    			return false;
	    		}   
    	    }
        }
        return true;
        
    }

	@Override
	public void afterCompletion(HttpServletRequest arg0,
			HttpServletResponse arg1, Object arg2, Exception arg3)
			throws Exception {
		//do nothing
		
	}

	@Override
	public void postHandle(HttpServletRequest arg0, HttpServletResponse arg1,
			Object arg2, ModelAndView arg3) throws Exception {
		// do nothing
		
	}

}
