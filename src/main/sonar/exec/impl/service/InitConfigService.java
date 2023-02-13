package main.sonar.exec.impl.service;

import main.sonar.common.SonarGlobal;
import main.sonar.common.exceptions.ExecutionFailedException;
import main.sonar.exec.ISonarExecutable;
import main.sonar.exec.ISonarExecutableFactory;
import main.sonar.utils.FileUtil;
import main.sonar.utils.PropertyFile;

import java.io.IOException;
import java.nio.file.Paths;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

public class InitConfigService implements ISonarExecutable {
	private InitConfigService() {}
	public static ISonarExecutableFactory getFactory() {
		return new ISonarExecutableFactory() {
			@Override
			public ISonarExecutable create() {
				return new InitConfigService();
			}
		};
	}

	@Override
	public void execute(Map<String, String> params) throws ExecutionFailedException {
		boolean overwrite = false;
		if (params != null) {
			if ("true".equals(params.get("overwrite"))) {
				overwrite = true;
			}
		}
		if (FileUtil.exists(SonarGlobal.SONAR_ASSIST_CONFIG_FILE)) {
			Logger.getGlobal().log(Level.WARNING, "Configuration already initialized!");
			if (!overwrite) {
				throw new ExecutionFailedException("Already initialized");
			}
		}

		PropertyFile file = new PropertyFile();
		String profile = null;
		String gate = null;
		String server = null;
		String login = null;
		String password = null;

		if (params != null) {
			profile = params.get(SonarGlobal.ASSIST_QUALITY_PROFILE);
			gate = params.get(SonarGlobal.ASSIST_QUALITY_GATE);
			server = params.get(SonarGlobal.ASSIST_SERVER);
			login = params.get(SonarGlobal.ASSIST_LOGIN);
			password = params.get(SonarGlobal.ASSIST_PASSWORD);
		}
		file.put(SonarGlobal.ASSIST_QUALITY_PROFILE, profile, SonarGlobal.INVALID_PROPERTY);
		file.put(SonarGlobal.ASSIST_QUALITY_GATE, gate, SonarGlobal.INVALID_PROPERTY);
		file.put(SonarGlobal.ASSIST_SERVER, server, SonarGlobal.INVALID_PROPERTY);
		file.put(SonarGlobal.ASSIST_LOGIN, login, SonarGlobal.INVALID_PROPERTY);
		file.put(SonarGlobal.ASSIST_PASSWORD, password, SonarGlobal.INVALID_PROPERTY);

		try {
			file.save(SonarGlobal.SONAR_ASSIST_CONFIG_FILE);
		} catch (IOException e) {
			Logger.getGlobal().log(Level.SEVERE, "Failed to save config");
			throw new ExecutionFailedException("Failed to save config", e);
		}

		SonarGlobal.setSonarLogin(login);
		SonarGlobal.setSonarPassword(password);
		SonarGlobal.setSonarServer(server);
	}
}
