package info.rmapproject.webapps.exception;

import info.rmapproject.auth.utils.Constants;

import java.util.Properties;


/**
 * @author khanson
 * Custom error codes for RMap Authentication
 */
public enum ErrorCode {		
	ER_RESOURCE_PROPERTY_VALUE_NULL (4013001),
	ER_RESOURCE_TYPE_NULL (4013002); 

	private final int number;

	private ErrorCode (int number) {
		this.number = number;
	}

	public int getNumber()  {
		return number;
	}

	private static Properties properties;

	private String message;
	
    private void init() {
		
		try {	
	        if (properties == null) {
	            properties = new Properties();
	            properties.load(ErrorCode.class.getResourceAsStream(Constants.ERROR_MSGS_PROPS_FILEPATH));
	        }
	        message = (String) properties.get(this.toString());
		} 
		catch(Exception e){
			message = Constants.DEFAULT_ERROR_MESSAGE;
			if (message == null){
				message = "";
			}
		}   
    }
    	
	/**
	 * @return String
	 * Returns the message that corresponds to the error code.
	 */
	public String getMessage() {
        if (this.message == null) {
            init();
        }
        return message;
	}
	
	
	
}

