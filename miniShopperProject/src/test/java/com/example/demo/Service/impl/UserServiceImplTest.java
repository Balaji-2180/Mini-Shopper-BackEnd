package com.example.demo.Service.impl;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import com.example.demo.Entity.User;
import com.example.demo.Repository.UserRepository;
import com.example.demo.Service.UserService;

@SpringBootTest
class UserServiceImplTest {
	
    @Autowired
	private UserService userService;
    
    @MockBean
    private UserRepository userRepository;
    
    @BeforeEach
    void setUp() {
    	User user=new User("vinothkumar@gmail.com","Vinoth","Kumar","vinothkumar@gmail.com","Vinoth@123");
    	Mockito.when(userRepository.findByUserId("vinothkumar@gmail.com")).thenReturn(user);
    	
    }
    @Test
    public void testFindByUserId() {
    	String expectedUserId="vinothkumar@gmail.com";
    	User u=userService.checkUserId("vinothkumar@gmail.com");
    	//System.out.println(u.toString());
    	assertEquals(expectedUserId,u.getUserId());
    }
    
    
    @Test
    public void testFindByUserId_Null() {
    //	String expectedUserId=null;
    	User u=userService.checkUserId("vkumar@gmail.com");
    	//System.out.println(u.toString());
    	assertNull(u);
    }
    
   
    
    
    

}
