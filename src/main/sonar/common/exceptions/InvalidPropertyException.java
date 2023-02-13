package main.sonar.common.exceptions;

public class InvalidPropertyException extends Exception {
	public InvalidPropertyException() {
	}

	public InvalidPropertyException(String message) {
		super(message);
	}

	public InvalidPropertyException(String message, Throwable cause) {
		super(message, cause);
	}
}
