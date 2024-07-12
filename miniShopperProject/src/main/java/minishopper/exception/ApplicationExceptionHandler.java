package minishopper.exception;

import java.nio.file.AccessDeniedException;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ProblemDetail;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.security.SignatureException;

import org.springframework.web.bind.annotation.ExceptionHandler;

@RestControllerAdvice
public class ApplicationExceptionHandler {
	
	
	@ExceptionHandler(Exception.class)
    public ProblemDetail handleSecurityException(Exception ex) {
        ProblemDetail errorDetail = null;
        if (ex instanceof BadCredentialsException) {
            errorDetail = ProblemDetail.forStatusAndDetail(HttpStatusCode.valueOf(401), ex.getMessage());
            errorDetail.setProperty("access_denied_reason", "Authentication Failure");
        }

        if (ex instanceof AccessDeniedException) {
            errorDetail = ProblemDetail.forStatusAndDetail(HttpStatusCode.valueOf(403), ex.getMessage());
            errorDetail.setProperty("access_denied_reason", "not_authorized!");

        }

        if (ex instanceof SignatureException) {
            errorDetail = ProblemDetail.forStatusAndDetail(HttpStatusCode.valueOf(401), ex.getMessage());
            errorDetail.setProperty("access_denied_reason", "JWT Signature not valid");
        }
        if (ex instanceof ExpiredJwtException) {
            errorDetail = ProblemDetail.forStatusAndDetail(HttpStatusCode.valueOf(401), ex.getMessage());
            errorDetail.setProperty("access_denied_reason", "JWT Token already expired !");
        }
        
        if (ex instanceof SQLException) {
            errorDetail = ProblemDetail.forStatusAndDetail(HttpStatusCode.valueOf(500), ex.getMessage());
            errorDetail.setProperty("exception_reason", "Problem occured in server side !");
        }
        
        if (ex instanceof ResourceNotFoundException) {
            errorDetail = ProblemDetail.forStatusAndDetail(HttpStatusCode.valueOf(404), ex.getMessage());
            errorDetail.setProperty("exception_reason", ex.getMessage());
        }
        
        if (ex instanceof InvalidInputException) {
            errorDetail = ProblemDetail.forStatusAndDetail(HttpStatusCode.valueOf(401), ex.getMessage());
            errorDetail.setProperty("exception_reason", ex.getMessage());
        }

        return errorDetail;
    }
	
	
	@ResponseStatus(HttpStatus.BAD_REQUEST)
	@ExceptionHandler(MethodArgumentNotValidException.class)
	public Map<String, String> handleInvalidArugument(MethodArgumentNotValidException exception){
		
		Map<String, String> errorMap = new HashMap<>();
		exception.getBindingResult().getFieldErrors().forEach(error -> {
			errorMap.put(error.getField(), error.getDefaultMessage());
		});		
		return errorMap;
	}

//	
//	
//	@ResponseStatus(HttpStatus.UNAUTHORIZED)
//	@ExceptionHandler(UnauthorizedException.class)
//	public String handleUnAuthorizedException(UnauthorizedException exception){
//		return exception.getMessage();
//	}
//	
//	@ResponseStatus(HttpStatus.UNAUTHORIZED)
//	@ExceptionHandler(InvalidInputException.class)
//	public String handleInvalidInput(InvalidInputException exception){
//		System.out.println("in exception handler ");
//		System.out.println(exception.getMessage());
//		return exception.getMessage();
//	}
//	
//	@ResponseStatus(HttpStatus.NOT_FOUND)
//	@ExceptionHandler(ResourceNotFoundException.class)
//	public String handleResourceNotFoundException(ResourceNotFoundException exception){
//		return exception.getMessage();
//	}
//	
//
//	@ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
//	@ExceptionHandler(SQLException.class)
//	public Map<String, String> handleSqlException(SQLException exception){
//		
//		Map<String, String> errorMap = new HashMap<>();
////	    exception.forEach(error -> {
////	    	errorMap.put(error.getCause(), error.getMessage());
////	    });
//		return errorMap;
//	}

}
