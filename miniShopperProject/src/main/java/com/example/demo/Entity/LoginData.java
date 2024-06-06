package com.example.demo.Entity;

import java.time.LocalDate;
import java.time.LocalTime;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class LoginData {
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private int record;
	
	@Column(name="userId",length=100)
	private String userId;
	
	
	
	@Column(name="loginStatus", length=50)
	private String loginStatus;
	
	@Column(name="date")
	private LocalDate date;
	
	@Column(name="time")
	private LocalTime time;

	



	public LoginData(String userId, String loginStatus, LocalDate date, LocalTime time) {
		super();
		this.userId = userId;
		this.loginStatus = loginStatus;
		this.date = date;
		this.time = time;
	}
	
	

}
