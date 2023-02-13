package main.sonar.common.exceptions;

public class RequestFailedException extends Exception {
	public RequestFailedException() {
	}

	public RequestFailedException(String message) {
		super(message);
	}

	public RequestFailedException(String message, Throwable cause) {
		super(message, cause);
	}
}
