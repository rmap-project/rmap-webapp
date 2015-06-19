package info.rmapproject.webapp.controllers;

import info.rmapproject.core.exception.RMapAgentNotFoundException;
import info.rmapproject.core.exception.RMapDeletedObjectException;
import info.rmapproject.core.exception.RMapDiSCONotFoundException;
import info.rmapproject.core.exception.RMapEventNotFoundException;
import info.rmapproject.core.exception.RMapException;
import info.rmapproject.core.exception.RMapObjectNotFoundException;
import info.rmapproject.core.exception.RMapStatementNotFoundException;
import info.rmapproject.core.exception.RMapTombstonedObjectException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;


@ControllerAdvice
public class ExceptionHandlingController {

	private static final Logger logger = LoggerFactory.getLogger(DiSCOController.class);
	
	@ExceptionHandler({RMapDiSCONotFoundException.class, RMapAgentNotFoundException.class, RMapEventNotFoundException.class, 
						RMapStatementNotFoundException.class, RMapObjectNotFoundException.class})
	 public String objectNotFoundError(Exception exception) {		
		logger.error(exception.getMessage(), exception);
		return "objectnotfound";
	  }
	
	@ExceptionHandler({RMapDeletedObjectException.class, RMapTombstonedObjectException.class})
	 public String deletionError(Exception exception) {	
		logger.error(exception.getMessage(), exception);
		return "deleted";
	  }
	
	@ExceptionHandler({RMapException.class, Exception.class})
	 public String genericError(Exception exception) {	
		logger.error(exception.getMessage(), exception);
		return "error";
	  }
	
}
