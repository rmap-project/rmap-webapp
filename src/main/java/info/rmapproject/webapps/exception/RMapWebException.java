package info.rmapproject.webapps.exception;

import java.io.PrintStream;
import java.io.PrintWriter;
import java.util.Map;
import java.util.TreeMap;
/**
 * Creates HTTP responses for DiSCO REST API requests
 * Note: Code derived from: https://northconcepts.com/downloads/file/blog/exceptions/NorthConcepts-Exceptions.zip 
 * Therefore it should be stated that: The source code is licensed under the terms of the Apache License, Version 2.0.
 * @author khanson
 */
public class RMapWebException extends Exception {

    private static final long serialVersionUID = 1L;

    public static RMapWebException wrap(Throwable exception, ErrorCode errorCode) {
        if (exception instanceof RMapWebException) {
            RMapWebException se = (RMapWebException)exception;
        	if (errorCode != null && errorCode != se.getErrorCode()) {
                return new RMapWebException(exception.getMessage(), exception, errorCode);
			}
			return se;
        } else {
            return new RMapWebException(exception.getMessage(), exception, errorCode);
        }
    }
    
    public static RMapWebException wrap(Throwable exception) {
    	return wrap(exception, null);
    }
    
    private ErrorCode errorCode;
    private final Map<String,Object> properties = new TreeMap<String,Object>();
    
    public RMapWebException(ErrorCode errorCode) {
		this.errorCode = errorCode;
	}

	public RMapWebException(String message, ErrorCode errorCode) {
		super(message);
		this.errorCode = errorCode;
	}

	public RMapWebException(Throwable cause, ErrorCode errorCode) {
		super(cause);
		this.errorCode = errorCode;
	}

	public RMapWebException(String message, Throwable cause, ErrorCode errorCode) {
		super(message, cause);
		this.errorCode = errorCode;
	}
	
	public ErrorCode getErrorCode() {
        return errorCode;
    }
	
	public RMapWebException setErrorCode(ErrorCode errorCode) {
        this.errorCode = errorCode;
        return this;
    }
	
	public Map<String, Object> getProperties() {
		return properties;
	}
	
    @SuppressWarnings("unchecked")
	public <T> T get(String name) {
        return (T)properties.get(name);
    }
	
    public RMapWebException set(String name, Object value) {
        properties.put(name, value);
        return this;
    }
    
    public void printStackTrace(PrintStream s) {
        synchronized (s) {
            printStackTrace(new PrintWriter(s));
        }
    }

    public void printStackTrace(PrintWriter s) { 
        synchronized (s) {
            s.println(this);
            s.println("\t-------------------------------");
            if (errorCode != null) {
	        	s.println("\t" + errorCode + ":" + errorCode.getClass().getName()); 
			}
            for (String key : properties.keySet()) {
            	s.println("\t" + key + "=[" + properties.get(key) + "]"); 
            }
            s.println("\t-------------------------------");
            StackTraceElement[] trace = getStackTrace();
            for (int i=0; i < trace.length; i++)
                s.println("\tat " + trace[i]);

            Throwable ourCause = getCause();
            if (ourCause != null) {
                ourCause.printStackTrace(s);
            }
            s.flush();
        }
    }
    
}
