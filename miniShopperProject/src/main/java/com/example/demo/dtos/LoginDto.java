package com.example.demo.dtos;



public class LoginDto {

	private String UserId;
	private String password;
	public String getUserId() {
		return UserId;
	}
	public void setUserId(String userId) {
		UserId = userId;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public LoginDto(String userId, String password) {
		super();
		UserId = userId;
		this.password = password;
	}
	public LoginDto() {
		super();
		// TODO Auto-generated constructor stub
	}
	@Override
	public String toString() {
		return "LoginDto [UserId=" + UserId + ", password=" + password + "]";
	}
	
}
