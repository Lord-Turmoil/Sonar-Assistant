package main.sonar.exec.impl.service;

import main.sonar.common.SonarGlobal;
import main.sonar.common.exceptions.ExecutionFailedException;
import main.sonar.exec.ISonarExecutable;
import main.sonar.exec.ISonarExecutableFactory;
import main.sonar.utils.PropertyFile;

import java.io.IOException;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

public class UpdateConfigService implements ISonarExecutable {
	private UpdateConfigService() {}
	public static ISonarExecutableFactory getFactory() {
		return new ISonarExecutableFactory() {
			@Override
			public ISonarExecutable create() {
				return new UpdateConfigService();
			}
		};
	}

	@Override
	public void execute(Map<String, String> params) throws ExecutionFailedException {
		if (params == null) {
			Logger.getGlobal().log(Level.INFO, "Nothing to update...");
			return;
		}

		PropertyFile file = new PropertyFile();
		try {
			file.load(SonarGlobal.SONAR_ASSIST_CONFIG_FILE);
		} catch (IOException e) {
			Logger.getGlobal().log(Level.SEVERE, "Did you forget to initialize?");
			throw new ExecutionFailedException("Could not load config file", e);
		}

		for (Map.Entry<String, String> param : params.entrySet()) {
			file.put(param.getKey(), param.getValue());
		}

		try {
			file.save(SonarGlobal.SONAR_ASSIST_CONFIG_FILE);
		} catch (IOException e) {
			Logger.getGlobal().log(Level.SEVERE, "Failed to save config file");
			throw new ExecutionFailedException("Could not save config file", e);
		}

		// updateGlobal(file);
	}

	private void updateGlobal(PropertyFile file) {
		String login = file.get(SonarGlobal.ASSIST_LOGIN);
		if (!SonarGlobal.INVALID_PROPERTY.equals(login)) {
			SonarGlobal.setSonarLogin(login);
		}
		String password = file.get(SonarGlobal.ASSIST_PASSWORD);
		if (!SonarGlobal.INVALID_PROPERTY.equals(password)) {
			SonarGlobal.setSonarLogin(password);
		}
		String server = file.get(SonarGlobal.ASSIST_SERVER);
		if (!SonarGlobal.INVALID_PROPERTY.equals(server)) {
			SonarGlobal.setSonarLogin(server);
		}
	}
}
