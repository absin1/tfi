
package tfi.exceptions;

/**
 * @author absin
 *
 */
public class TfiException extends Exception {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	String message;

	public String getmessage() {
		return message;
	}

	public void setmessage(String message) {
		this.message = message;
	}

	public TfiException(String message) {

		super(message);
		this.message = message;
	}

}