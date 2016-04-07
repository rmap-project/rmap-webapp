package info.rmapproject.webapp.utils;

public final class Constants  {
	//TODO:read configurable constants in from properties file
	
	/**File path for error message text*/
	public static final String ERROR_MSGS_PROPS_FILEPATH = "/error_msgs.properties";
	
	/** maximum length for the short graph node text */
	public static final Integer MAX_NODETEXT_LENGTH = 21;

	/** maximum length for the short graph edge text */
    public static final Integer MAX_EDGETEXT_LENGTH = 30;
	
    /**Default value by which to increment the node weight*/
    public static final int NODE_WEIGHT_INCREMENT=10;

    /**Prefix for resource path*/
    public static final String RESOURCE_PATH_PREFIX="/resources/";
    
    /**Default maximum number of records returned from triple store in one go*/
    public static final int QUERY_LIMIT=200;
    /**Default offset for triple store queries*/
    public static final int QUERY_OFFSET=0;
       
    
	private Constants(){
		    //this prevents even the native class from calling this ctor as well :
		    throw new AssertionError();
		  }
}
