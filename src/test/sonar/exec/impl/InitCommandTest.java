package test.sonar.exec.impl;

import main.sonar.exec.ISonarExecutable;
import main.sonar.exec.impl.InitCommand;
import test.sonar.exec.ExecutableTestBase;

public class InitCommandTest extends ExecutableTestBase {
	public static void main(String[] args) {
		ISonarExecutable executable = InitCommand.getFactory().create();

		execute(executable, null, "init");
	}
}
