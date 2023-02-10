package main.sonar.request;

public class RequestFailedException extends Exception {
	public RequestFailedException(String message) {
		super(message);
	}
}
