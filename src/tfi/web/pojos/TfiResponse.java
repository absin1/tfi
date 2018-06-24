package tfi.web.pojos;

public class TfiResponse {
	private Integer errorCode;
	private String errorMessage;
	private boolean success;
	private Object response;

	public Integer getErrorCode() {
		return errorCode;
	}

	public TfiResponse(Integer errorCode, String errorMessage, boolean success, Object response) {
		super();
		this.errorCode = errorCode;
		this.errorMessage = errorMessage;
		this.success = success;
		this.response = response;
	}

	public void setErrorCode(Integer errorCode) {
		this.errorCode = errorCode;
	}

	public String getErrorMessage() {
		return errorMessage;
	}

	public void setErrorMessage(String errorMessage) {
		this.errorMessage = errorMessage;
	}

	public boolean isSuccess() {
		return success;
	}

	public void setSuccess(boolean success) {
		this.success = success;
	}

	public Object getResponse() {
		return response;
	}

	public void setResponse(Object response) {
		this.response = response;
	}

}
