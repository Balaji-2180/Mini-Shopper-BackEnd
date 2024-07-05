package minishopper.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import minishopper.entity.User;
import minishopper.response.RegisterResponse;
import minishopper.service.UserService;

@CrossOrigin(origins = "*")
@Controller
@RequestMapping("/users")
public class RegisterController {
 
	@Autowired
	UserService userService;
	
//	@Autowired
//	ShopkeeperService shopkeeperService;   
 
	@PostMapping("/newUser")
	public ResponseEntity<RegisterResponse> saveUser(@RequestBody User u) {
		System.out.println("in register controller");
		RegisterResponse registerResponse = new RegisterResponse();
		if (u.getFirstName() != null && u.getLastName() != null && u.getEmail() != null && u.getPassword() != null && u.getUserId() != null) {
			User loginUser = userService.checkUserId(u.getUserId());
			if (loginUser == null) {
				userService.saveUser(u);
				registerResponse.setStatus("201");
				registerResponse.setStatusMessage("Created");
				registerResponse.setMessage("User Created SUccessfully");
				return new ResponseEntity<RegisterResponse>(registerResponse, HttpStatus.CREATED);
			} else {
				registerResponse.setStatus("208");
				registerResponse.setStatusMessage("Already Reported");
				registerResponse.setMessage("Entered Email address already exist, please try again with other Email address");
				return new ResponseEntity<RegisterResponse>(registerResponse, HttpStatus.ALREADY_REPORTED);
			}
   
		} else {
			registerResponse.setStatus("400");
			registerResponse.setStatusMessage("Bad Request");
			registerResponse.setMessage("Error in Data Transfer");
			return new ResponseEntity<RegisterResponse>(registerResponse, HttpStatus.BAD_REQUEST);
		}
	}	
	

}
