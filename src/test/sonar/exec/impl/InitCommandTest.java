package test.sonar.exec.impl;

import main.sonar.common.SonarGlobal;
import main.sonar.exec.ISonarExecutable;
import main.sonar.exec.impl.command.InitCommand;
import test.sonar.exec.ExecutableTestBase;

import java.util.HashMap;

public class InitCommandTest extends ExecutableTestBase {
	public static void main(String[] args) {
		ISonarExecutable executable = InitCommand.getFactory().create();

		HashMap<String, String> params = new HashMap<>();
		params.put(SonarGlobal.SONAR_PROJECT_NAME, "BotTest");
		params.put(SonarGlobal.SONAR_SOURCE, "src");
		params.put(SonarGlobal.SONAR_BINARY, "out\\production\\scs-3");
		params.put(SonarGlobal.ASSIST_LOGIN, "squ_dbde44401a96fe0ef6748959ad2cde6a67a8fe7d");
		params.put(SonarGlobal.ASSIST_PASSWORD, "");
		params.put(SonarGlobal.ASSIST_SERVER, "http://tonys-studio.top:9000");
		// execute(executable, null, "init");
		execute(executable, params, "init");
	}
}
