package minishopper.controller;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import jakarta.validation.Valid;
import minishopper.dto.UserDto;
import minishopper.entity.User;
import minishopper.service.UserService;

@RestController
@RequestMapping("/users")
public class RegisterController {

	@Autowired
	private UserService userService;

	@PostMapping("/newUser")
	public ResponseEntity<String> saveUser(@Valid @RequestBody UserDto userDto) {
		User loginUser = userService.checkUserId(userDto.getUserId());
		if (loginUser == null) {
			userService.saveUser(userDto);
			return new ResponseEntity<>("Registered Successfully!", HttpStatus.CREATED);
		} else {
			return new ResponseEntity<>("The Email id already exist please register using another Email id",
					HttpStatus.ALREADY_REPORTED);
		}
	}

}
