package info.rmapproject.webapp.utils;

import javax.servlet.http.HttpServletRequest;

import org.springframework.web.util.UrlPathHelper;

/**
 * This class resolves an issue where Spring automatically decodes the HTTP path.  In other words 
 * without this, /app/discos/ark%3A%2F22573%2Frmd18mdcxp becomes /appdev/discos/ark:/22573/rmd18mdcxp
 * and returns a "file not found" error.
 * @author khanson
 *
 */
public class UrlPathHelperNonDecoding extends UrlPathHelper {
	 
	public UrlPathHelperNonDecoding() {
		super.setUrlDecode(false);
	}
	
	@Override
	public void setUrlDecode(boolean urlDecode) {
		if (urlDecode) {
			throw new IllegalArgumentException("Handler does not support URL decoding.");
		}
	}
 
	@Override
	public String getServletPath(HttpServletRequest request) {
		String servletPath = getOriginatingServletPath(request);
		return servletPath;
	}
	
 
	@Override
	public String getOriginatingServletPath(HttpServletRequest request) {
		String servletPath = request.getRequestURI().substring(request.getContextPath().length());
		return servletPath;
	}
}