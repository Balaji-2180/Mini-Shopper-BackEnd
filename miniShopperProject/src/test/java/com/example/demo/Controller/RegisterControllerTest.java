package com.example.demo.Controller;

import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import com.example.demo.Entity.User;
import com.example.demo.Service.impl.UserServiceImpl;
import com.fasterxml.jackson.databind.ObjectMapper;

@WebMvcTest(RegisterController.class)
class RegisterControllerTest {
	
	@MockBean
	UserServiceImpl userServiceImpl;
	
	@Autowired
	MockMvc mockMvc;
	

//	@Autowired
//	private TestEntityManager entityManger;
	
	
//	@BeforeEach
//	void setup() {
//	  User user = new User("vinothkumar@gmail.com","Vinoth","Kumar","vinothkumar@gmail.com","Vinoth@123");
//	  entityManger.persist(user);
//
//	}
	
	
	
	@Test
	public void registerUser() throws Exception{
		User u=new User("prakashraj@gmail.com","Prakash","Raj","prakashraj@gmail.com","Prakash@123");
		
		Mockito.when(userServiceImpl.saveUser(u)).thenReturn(u);
		mockMvc.perform(post("/users/newUser")
				.contentType(MediaType.APPLICATION_JSON)
				.content(new ObjectMapper().writeValueAsString(u)))
				.andExpect(status().isCreated());
	}

//	@Test
//	public void registerExistingUser() throws Exception{
//		User u1=new User("vinothkumar@gmail.com","Vinoth","Kumar","vinothkumar@gmail.com","Vinoth@123");
//		Mockito.when(userServiceImpl.saveUser(u1)).thenReturn(null);
//		mockMvc.perform(post("/users/newUser")
//				.contentType(MediaType.APPLICATION_JSON)
//				.content(new ObjectMapper().writeValueAsString(u1)))
//				.andExpect(status().isAlreadyReported());
//	}

	
}
