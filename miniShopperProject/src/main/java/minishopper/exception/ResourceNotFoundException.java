package minishopper.exception;

public class ResourceNotFoundException extends RuntimeException{
	
	public ResourceNotFoundException() {
		super("Resourcce Not Found On Server");
	}
	
	public ResourceNotFoundException(String message) {
		super(message);
	}

}
