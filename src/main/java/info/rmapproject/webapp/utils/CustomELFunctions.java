package info.rmapproject.webapp.utils;

import java.io.UnsupportedEncodingException;
import java.net.URI;
import java.net.URLEncoder;

public class CustomELFunctions {
	public static String httpEncode(String value)  throws UnsupportedEncodingException{
		if (value==null){
			return "";
		}
		return URLEncoder.encode(value, "UTF-8"); 
	}
	
	public static String httpEncode(URI value)   throws UnsupportedEncodingException{
		if (value==null){
			return "";
		}
		return URLEncoder.encode(value.toString(), "UTF-8"); 
	}
	
}
