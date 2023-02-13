package test.sonar.exec;

import main.sonar.common.exceptions.ExecutionFailedException;
import main.sonar.exec.ISonarExecutable;

import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

public class ExecutableTestBase {
	protected static void execute(ISonarExecutable executable, Map<String, String> params, String title) {
		System.out.print("----- Executable Test: ");
		System.out.println(title);
		try {
			executable.execute(params);
		} catch (ExecutionFailedException e) {
			Logger.getGlobal().log(Level.SEVERE, e.toString());
		}
	}
}
