package com.example.demo.Repository;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;

import com.example.demo.Entity.User;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
class UserRepositoryTest {
	@Autowired
	private UserRepository userRepository;

	@Autowired
	private TestEntityManager entityManger;

	@BeforeEach
	void setup() {
	  User user = new User("vinothkumar@gmail.com","Vinoth","Kumar","vinothkumar@gmail.com","Vinoth@123");
	  entityManger.persist(user);

	}

	@Test
	public void testFindById() {
	   String firstName="Vinoth";
	   User u = userRepository.findByUserId("vinothkumar@gmail.com");
	   System.out.println(u.toString());
	   assertEquals(firstName,u.getFirstName());
	}
	
	
//	@Test
//	public void testSaveExistingUser() {
//		  User user = new User("vinothkumar@gmail.com","Vinoth","Kumar","vinothkumar@gmail.com","Vinoth@123");
//		//User user=new User();
//	   User u = userRepository.save(user);
//	//   System.out.println("in junit "+u.toString());
//	   assertNull(u);
//	}
	
	


}
