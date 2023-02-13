package main.sonar.common.exceptions;

public class ExecutionFailedException extends Exception {
	public ExecutionFailedException() {
		super();
	}

	public ExecutionFailedException(String message) {
		super(message);
	}
}
