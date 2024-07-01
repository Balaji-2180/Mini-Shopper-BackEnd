package com.example.demo.Response;

public class BaseResponse {
	
	private String status;
	
	private String statusMessage;
	
	private String message;

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getStatusMessage() {
		return statusMessage;
	}

	public void setStatusMessage(String statusMessage) {
		this.statusMessage = statusMessage;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public BaseResponse() {
		super();
		// TODO Auto-generated constructor stub
	}

	public BaseResponse(String status, String statusMessage, String message) {
		super();
		this.status = status;
		this.statusMessage = statusMessage;
		this.message = message;
	}

	@Override
	public String toString() {
		return "BaseResponse [status=" + status + ", statusMessage=" + statusMessage + ", message=" + message + "]";
	}
	
	
	

}
