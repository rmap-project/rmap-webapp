package info.rmapproject.webapp.controllers;

import info.rmapproject.core.exception.RMapAgentNotFoundException;
import info.rmapproject.core.exception.RMapDeletedObjectException;
import info.rmapproject.core.exception.RMapDiSCONotFoundException;
import info.rmapproject.core.exception.RMapEventNotFoundException;
import info.rmapproject.core.exception.RMapException;
import info.rmapproject.core.exception.RMapObjectNotFoundException;
import info.rmapproject.core.exception.RMapTombstonedObjectException;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.SessionAttributes;

/**
 * Handles display of error messages
 * @author khanson
 *
 */
@ControllerAdvice
@SessionAttributes({"user","account"})
public class ExceptionHandlingController {

	/** path parameter for widget view	 */
	private static final String WIDGET_VIEW = "widget";
	
	private static final Logger logger = LoggerFactory.getLogger(DataDisplayController.class);
	/**
	 * Handles object not found exceptions
	 * @param exception
	 * @return
	 */
	@ExceptionHandler({RMapDiSCONotFoundException.class, RMapAgentNotFoundException.class, RMapEventNotFoundException.class,
		RMapObjectNotFoundException.class}) // 	RMapStatementNotFoundException.class,
	 public String objectNotFoundError(Exception exception, HttpServletRequest req) {		
		logger.error(exception.getMessage(), exception);
		if (req.getRequestURL().toString().contains("/" + WIDGET_VIEW)){
			return "objectnotfoundwidget";
		}
		else {
			return "objectnotfound";			
		}
		
	  }
	
	/**
	 * Handles deleted object exceptions
	 * @param exception
	 * @return
	 */
	@ExceptionHandler({RMapDeletedObjectException.class, RMapTombstonedObjectException.class})
	 public String deletionError(Exception exception) {	
		logger.error(exception.getMessage(), exception);
		return "deleted";
	  }
	
	/**
	 * Generic error message to handle all other exceptions e.g. system errors
	 * @param exception
	 * @return
	 */
	@ExceptionHandler({RMapException.class, Exception.class})
	 public String genericError(Exception exception) {	
		logger.error(exception.getMessage(), exception);
		return "error";
	  }
	
}
