package info.rmapproject.webapp.controllers;

import info.rmapproject.core.model.statement.RMapStatement;
import info.rmapproject.core.rmapservice.RMapService;
import info.rmapproject.core.rmapservice.RMapServiceFactoryIOC;

import java.net.URI;
import java.net.URLDecoder;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * Handles requests for the application event pages.
 */
@Controller
public class StmtController {
	
	private static final Logger logger = LoggerFactory.getLogger(StmtController.class);
	
	/**
	 * Get details of a Statement
	 */

	@RequestMapping(value="/stmts", method = RequestMethod.GET)
	public String statement(@RequestParam("uri") String stmtUri, Model model) throws Exception {
		logger.info("Stmt requested");
				
		URI uriStmtUri = null;
		stmtUri = URLDecoder.decode(stmtUri, "UTF-8");
		uriStmtUri = new URI(stmtUri);
		
		RMapService rmapService = RMapServiceFactoryIOC.getFactory().createService();
		RMapStatement rmapStmt = rmapService.readStatement(uriStmtUri);
		
	    model.addAttribute("STMT_URI", stmtUri);
	    model.addAttribute("STMT_STATUS", rmapService.getStatementStatus(uriStmtUri));
	    model.addAttribute("STMT_SUBJ", rmapStmt.getSubject().toString());
	    model.addAttribute("STMT_PRED", rmapStmt.getPredicate().toString());
	    model.addAttribute("STMT_OBJ", rmapStmt.getObject().toString());
    	
	    List <URI> events = rmapService.getStatementEvents(uriStmtUri);
	    model.addAttribute("STMT_EVENTS", events);
	    	    
	    rmapService.closeConnection();

		return "stmts";
	}
		
}
