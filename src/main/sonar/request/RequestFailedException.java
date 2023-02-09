package main.sonar.request;

public class RequestFailedException extends Exception {
	RequestFailedException(String message) {
		super(message);
	}
}
