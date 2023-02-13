package test.sonar.exec.impl;

import main.sonar.common.SonarGlobal;
import main.sonar.common.exceptions.ExecutionFailedException;
import main.sonar.exec.impl.service.UpdateConfigService;
import test.sonar.exec.ExecutableTestBase;
import test.sonar.global.SonarGlobalTest;

import java.util.HashMap;

public class UpdateConfigServiceTest extends ExecutableTestBase {
	public static void main(String[] args) throws ExecutionFailedException {
		HashMap<String, String> configUpdates = new HashMap<>();
		configUpdates.put(SonarGlobal.ASSIST_SERVER, SonarGlobalTest.SONAR_SERVER);
		UpdateConfigService.getFactory().create().execute(configUpdates);
	}
}
