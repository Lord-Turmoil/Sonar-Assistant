package main.sonar.exec.impl;

import main.sonar.common.exceptions.ExecutionFailedException;
import main.sonar.exec.ISonarExecutable;

import java.util.Map;


/**
 * Create project based on sonar-project.properties.
 */
public class CreateProjectCommand implements ISonarExecutable {
	@Override
	public void execute(Map<String, String> params) throws ExecutionFailedException {

	}
}
