package main.sonar.common.exceptions;

public class ExecutionFailedException extends Exception {
	public ExecutionFailedException() {
	}

	public ExecutionFailedException(String message) {
		super(message);
	}

	public ExecutionFailedException(String message, Throwable cause) {
		super(message, cause);
	}
}
