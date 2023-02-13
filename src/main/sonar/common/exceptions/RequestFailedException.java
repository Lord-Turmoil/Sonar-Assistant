package main.sonar.common.exceptions;

public class RequestFailedException extends Exception {
	public RequestFailedException(String message) {
		super(message);
	}
}
