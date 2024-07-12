package minishopper.controller;

import java.util.Date;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import jakarta.validation.Valid;
import minishopper.dto.UserDto;
import minishopper.entity.Address;
import minishopper.entity.User;
import minishopper.response.RegisterResponse;
import minishopper.service.UserService;

@Controller
@RequestMapping("/users")
public class RegisterController {

	@Autowired
	private UserService userService;

	@PostMapping("/newUser")
	public ResponseEntity<String> saveUser(@Valid @RequestBody UserDto userDto) {
		System.out.println("in register controller "+userDto.toString());
		User loginUser = userService.checkUserId(userDto.getUserId());
			if (loginUser == null) {
				userService.saveUser(userDto);
				return new ResponseEntity<>("User Created Successfully", HttpStatus.CREATED);
			} else {
				return new ResponseEntity<>("The Email id already exist please register using another Email id",HttpStatus.ALREADY_REPORTED);
			}
	}

}
