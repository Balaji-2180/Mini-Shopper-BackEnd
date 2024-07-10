package minishopper.exception;

public class InvalidInputException extends RuntimeException{
	
	public InvalidInputException() {
		super("Unauthorized");
	}
	
	public InvalidInputException(String message) {
		super(message);
	}

}
