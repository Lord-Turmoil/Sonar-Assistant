package main.sonar.exec;

public class ExecutionFailedException extends Exception {
	public ExecutionFailedException() {
		super();
	}

	public ExecutionFailedException(String message) {
		super(message);
	}
}
