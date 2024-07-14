package minishopper.controller;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.http.HttpHeaders;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.MalformedJwtException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import minishopper.dto.AddressDto;
import minishopper.dto.JwtResponseDto;
import minishopper.dto.LoginDto;
import minishopper.dto.UserDto;
import minishopper.entity.LoginData;
import minishopper.entity.User;
import minishopper.exception.InvalidInputException;
import minishopper.exception.ResourceNotFoundException;
import minishopper.exception.UnauthorizedException;
import minishopper.repository.LoginDataRepository;
import minishopper.repository.ProductRepository;
import minishopper.repository.UserRepository;
import minishopper.security.JwtHelper;
import minishopper.service.CustomUserDetailsService;
import minishopper.service.LoginDataService;
import minishopper.service.UserService;
import minishopper.serviceimpl.UserServiceImpl;


@RestController
@RequestMapping("/users")
public class LoginController {
	
	@Autowired
	private AuthenticationManager manager;
	
	@Autowired
	private CustomUserDetailsService userDetailService;

	@Autowired
	private JwtHelper jwtHelper;

	@Autowired
	private UserService userService;
	
	public boolean checkUserId(String userId) {
		User loginUser = userService.checkUserId(userId);
		if(loginUser == null) {
			return false;
		}
		return true;
	}

	@PostMapping("/loginUser")
	public ResponseEntity<JwtResponseDto> loginUser(@Valid @RequestBody LoginDto loginDto) throws BadCredentialsException{
		if(userService.checkUserId(loginDto.getUserId()) == null) {
			throw new BadCredentialsException("Invalid UserId!");
		}
	    doAuthenticate(loginDto.getUserId(), loginDto.getPassword());
	   
		UserDetails userDetails = userDetailService.loadUserByUsername(loginDto.getUserId());
		String jwtToken = this.jwtHelper.generateToken(userDetails);
		String refreshToken = this.jwtHelper.generateRefreshToken(userDetails);
		UserDto userDto = userService.fetchUserDetailsById(userDetails.getUsername());
		userDto.setUserId(userDetails.getUsername());
		JwtResponseDto response = new JwtResponseDto();
		if(!(loginDto.getRole().equalsIgnoreCase(userDto.getRole()))){
				throw new BadCredentialsException("Invalid Credentials!");
		}
		response = JwtResponseDto.builder().accessToken(jwtToken).refreshToken(refreshToken).user(userDto).build();
		
		return new ResponseEntity<JwtResponseDto>(response, HttpStatus.OK);

	}

	@PostMapping("/{userId}")
	public ResponseEntity<UserDto> getUserById(@PathVariable String userId) throws ResourceNotFoundException{
		if(!checkUserId(userId)) {
			throw new InvalidInputException("Invalid UserId !");
		}
		UserDto userDto = userService.fetchUserDetailsById(userId);
		return new ResponseEntity<UserDto>(userDto, HttpStatus.OK);
	}

	@PutMapping("/{userId}")
	public ResponseEntity<UserDto> updateUserDetails(@PathVariable String userId, @Valid @RequestBody AddressDto address) {
		if(!checkUserId(userId)) {
			throw new InvalidInputException("Invalid UserId !");
		}
		UserDto userDto = userService.updateUser(userId, address);
		return new ResponseEntity<UserDto>(userDto, HttpStatus.OK);
	}
	
	@PutMapping("/addAddress/{userId}")
	public ResponseEntity<UserDto> addAddressDetails(@PathVariable String userId, @Valid @RequestBody AddressDto address) {
		if(!checkUserId(userId)) {
			throw new InvalidInputException("Invalid UserId !");
		}
		UserDto userDto = userService.addAddress(userId, address);
		return new ResponseEntity<UserDto>(userDto, HttpStatus.OK);
	}

	
	private void doAuthenticate(String email, String password) throws BadCredentialsException{
		UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(email, password);
		manager.authenticate(authentication);
	}

}
