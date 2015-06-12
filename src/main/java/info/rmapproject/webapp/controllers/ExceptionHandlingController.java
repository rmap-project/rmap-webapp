package info.rmapproject.webapp.controllers;

import info.rmapproject.core.exception.RMapAgentNotFoundException;
import info.rmapproject.core.exception.RMapDeletedObjectException;
import info.rmapproject.core.exception.RMapDiSCONotFoundException;
import info.rmapproject.core.exception.RMapEventNotFoundException;
import info.rmapproject.core.exception.RMapException;
import info.rmapproject.core.exception.RMapObjectNotFoundException;
import info.rmapproject.core.exception.RMapStatementNotFoundException;
import info.rmapproject.core.exception.RMapTombstonedObjectException;

import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;


@ControllerAdvice
public class ExceptionHandlingController {

	
	@ExceptionHandler({RMapDiSCONotFoundException.class, RMapAgentNotFoundException.class, RMapEventNotFoundException.class, 
						RMapStatementNotFoundException.class, RMapObjectNotFoundException.class})
	 public String objectNotFoundError() {
		return "objectnotfound";
	  }
	
	@ExceptionHandler({RMapDeletedObjectException.class, RMapTombstonedObjectException.class})
	 public String deletionError() {
		return "deleted";
	  }
	
	@ExceptionHandler({RMapException.class, Exception.class})
	 public String genericError() {
		return "error";
	  }
	
}
