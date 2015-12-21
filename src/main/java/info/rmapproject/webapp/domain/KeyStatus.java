package info.rmapproject.webapp.domain;

/**
 * 
 * @author khanson
 *
 */
public enum KeyStatus {

	ACTIVE("ACTIVE"), 
	INACTIVE("INACTIVE"), 
	REVOKED("REVOKED");

	private final String keyStatus;

	private KeyStatus (String keyStatus) {
		this.keyStatus = keyStatus;
	}
	public String getKeyStatus()  {
		return keyStatus;
	}
		
}
