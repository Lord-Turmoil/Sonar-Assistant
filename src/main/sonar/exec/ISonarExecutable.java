/**
 * SonarQube operation. Uses factory method pattern.
 * No matter what command, must change working directory to the
 * target project's root directory.
 * xxxCommand is what user mainly calls.
 * xxxService is utility executables that assist Commands.
 */

package main.sonar.exec;

import java.util.Map;

public interface ISonarExecutable {
	public void execute(Map<String, String> params) throws ExecutionFailedException;
}
