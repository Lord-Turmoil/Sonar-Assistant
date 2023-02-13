package test.sonar.exec.impl;

import main.sonar.common.SonarGlobal;
import main.sonar.common.exceptions.ExecutionFailedException;
import main.sonar.exec.ISonarExecutable;
import main.sonar.exec.impl.command.CreateProjectCommand;
import main.sonar.exec.impl.command.InitCommand;
import main.sonar.exec.impl.service.StartSonarService;
import main.sonar.exec.impl.service.UpdateConfigService;
import main.sonar.exec.impl.service.UpdatePropertyService;
import test.sonar.global.SonarGlobalTest;

import java.util.HashMap;
import java.util.logging.Logger;

/**
 * Call init command to initialize first.
 */
public class CreateProjectCommandTest {
	public static void main(String[] args) throws ExecutionFailedException {
		StartSonarService.getFactory().create().execute(null);
		CreateProjectCommand.getFactory().create().execute(null);
	}
}
