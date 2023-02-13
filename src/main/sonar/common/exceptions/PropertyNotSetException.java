package main.sonar.common.exceptions;

public class PropertyNotSetException extends Exception {
	public PropertyNotSetException() {
	}

	public PropertyNotSetException(String message) {
		super(message);
	}
}
