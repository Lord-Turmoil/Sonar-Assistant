package test.sonar.exec.impl;

import main.sonar.exec.ISonarExecutable;
import main.sonar.exec.impl.service.GeneratePropertyService;
import test.sonar.exec.ExecutableTestBase;

public class GeneratePropertyServiceTest extends ExecutableTestBase {
	public static void main(String[] args) {
		ISonarExecutable executable = GeneratePropertyService.getFactory().create();

		execute(executable, null, "update property");
	}
}
