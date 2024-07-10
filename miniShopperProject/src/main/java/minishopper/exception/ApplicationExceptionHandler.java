package minishopper.exception;

import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import io.jsonwebtoken.ExpiredJwtException;

import org.springframework.web.bind.annotation.ExceptionHandler;

@RestControllerAdvice
public class ApplicationExceptionHandler {
	
	
	@ResponseStatus(HttpStatus.BAD_REQUEST)
	@ExceptionHandler(MethodArgumentNotValidException.class)
	public Map<String, String> handleInvalidArugument(MethodArgumentNotValidException exception){
		
		Map<String, String> errorMap = new HashMap<>();
		exception.getBindingResult().getFieldErrors().forEach(error -> {
			errorMap.put(error.getField(), error.getDefaultMessage());
		});		
		return errorMap;
	}
	
	@ResponseStatus(HttpStatus.UNAUTHORIZED)
	@ExceptionHandler(ExpiredJwtException.class)
	public String handleExpiredJwtException(ExpiredJwtException exception){
		
		Map<String, String> errorMap = new HashMap<>();
		
		System.out.println(exception.getMessage());
		return exception.getMessage();
//		return errorMap;
	}
	
	
	
	
	
	@ResponseStatus(HttpStatus.UNAUTHORIZED)
	@ExceptionHandler(UnauthorizedException.class)
	public String handleUnAuthorizedException(UnauthorizedException exception){
		return exception.getMessage();
	}
	
	@ResponseStatus(HttpStatus.UNAUTHORIZED)
	@ExceptionHandler(InvalidInputException.class)
	public String handleInvalidInput(InvalidInputException exception){
		return exception.getMessage();
	}
	
	@ResponseStatus(HttpStatus.NOT_FOUND)
	@ExceptionHandler(ResourceNotFoundException.class)
	public String handleResourceNotFoundException(ResourceNotFoundException exception){
		return exception.getMessage();
	}
	

	@ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
	@ExceptionHandler(SQLException.class)
	public Map<String, String> handleSqlException(SQLException exception){
		
		Map<String, String> errorMap = new HashMap<>();
//	    exception.forEach(error -> {
//	    	errorMap.put(error.getCause(), error.getMessage());
//	    });
		return errorMap;
	}

}
