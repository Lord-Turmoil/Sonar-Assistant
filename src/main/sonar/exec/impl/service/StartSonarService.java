package main.sonar.exec.impl.service;

import main.sonar.common.SonarGlobal;
import main.sonar.common.exceptions.ExecutionFailedException;
import main.sonar.exec.ISonarExecutable;
import main.sonar.exec.ISonarExecutableFactory;
import main.sonar.request.SonarQube;
import main.sonar.utils.PropertyFile;

import java.io.IOException;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Start sonar service.
 */
public class StartSonarService implements ISonarExecutable {
	private StartSonarService() {}
	public static ISonarExecutableFactory getFactory() {
		return new ISonarExecutableFactory() {
			@Override
			public ISonarExecutable create() {
				return new StartSonarService();
			}
		};
	}

	@Override
	public void execute(Map<String, String> params) throws ExecutionFailedException {
		PropertyFile file = new PropertyFile();
		try {
			file.load(SonarGlobal.SONAR_ASSIST_CONFIG_FILE);
		} catch (IOException e) {
			Logger.getGlobal().log(Level.SEVERE, "Failed to load config");
			throw new ExecutionFailedException("Failed to load config");
		}

		String login = "";
		String password = "";
		String server = "";

		login = file.get(SonarGlobal.ASSIST_LOGIN);
		password = file.get(SonarGlobal.ASSIST_PASSWORD);
		server = file.get(SonarGlobal.ASSIST_SERVER);

		if (!PropertyFile.isValid(login) || !PropertyFile.isValid(server)) {
			Logger.getGlobal().log(Level.SEVERE, "Missing vital parameters");
			throw new ExecutionFailedException("Missing vital parameters");
		}

		SonarGlobal.setSonarLogin(login);
		SonarGlobal.setSonarPassword(password);
		SonarGlobal.setSonarServer(server);

		SonarQube.getHost().setServer(server);
	}
}
