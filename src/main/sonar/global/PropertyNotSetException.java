package main.sonar.global;

public class PropertyNotSetException extends Exception {
	public PropertyNotSetException() {
	}

	public PropertyNotSetException(String message) {
		super(message);
	}
}
