package com.example.demo.exception;

public class LoginException {
	
	private String status;
	
	private String message;

	
	public LoginException() {
		super();
		// TODO Auto-generated constructor stub
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public LoginException(String status, String message) {
		super();
		this.status = status;
		this.message = message;
	}

	@Override
	public String toString() {
		return "LoginException [status=" + status + ", message=" + message + "]";
	}
	

}
