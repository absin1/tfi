package tfi.exceptions;

import tfi.web.pojos.TfiResponse;

public class TfiExceptionWeb extends TfiException {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	String message;
	Integer errorCode;

	public TfiExceptionWeb(String message, Integer errorCode) {
		super(message);
		this.errorCode = errorCode;
	}

	public TfiResponse toResponse() {
		return new TfiResponse(errorCode, message, false, null);
	}
}
