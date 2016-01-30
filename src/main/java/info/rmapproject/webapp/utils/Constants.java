package info.rmapproject.webapp.utils;

public final class Constants  {
	//TODO:read these in from properties file
	
	/**File path for error message text*/
	public static final String ERROR_MSGS_PROPS_FILEPATH = "/error_msgs.properties";
	
	/** maximum length for the short graph node text */
	public static final Integer MAX_NODETEXT_LENGTH = 21;

	/** maximum length for the short graph edge text */
    public static final Integer MAX_EDGETEXT_LENGTH = 30;
	
    /**Default value by which to increment the node weight*/
    public static final int NODE_WEIGHT_INCREMENT=10;
	
   
    /*public static final List<String> list = Collections.unmodifiableList(
    	    new ArrayList<String>() {{
    	        add("foo");
    	        add("bar");
    	        // etc
    	    }});*/
    
    
	private Constants(){
		    //this prevents even the native class from calling this ctor as well :
		    throw new AssertionError();
		  }
}
