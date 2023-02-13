package main.sonar.exec.impl;

import main.sonar.common.exceptions.ExecutionFailedException;
import main.sonar.exec.ISonarExecutable;
import main.sonar.exec.ISonarExecutableFactory;
import main.sonar.common.SonarGlobal;
import main.sonar.utils.FileUtil;
import main.sonar.utils.PropertyFile;

import java.io.IOException;
import java.nio.file.Paths;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Initialize project, just like 'git init'.
 * This will not overwrite previous init.
 */
public class InitCommand implements ISonarExecutable {
	private InitCommand() {}

	public static ISonarExecutableFactory getFactory() {
		return new ISonarExecutableFactory() {
			@Override
			public ISonarExecutable create() {
				return new InitCommand();
			}
		};
	}


	/**
	 *
	 * @param params optional parameters.
	 *
	 * @throws ExecutionFailedException
	 */
	@Override
	public void execute(Map<String, String> params) throws ExecutionFailedException {
		boolean overwrite = false;
		if (params != null) {
			if ("true".equals(params.get("overwrite"))) {
				overwrite = true;
			}
		}

		if (!overwrite && FileUtil.exists(SonarGlobal.SONAR_ASSIST_PROPERTIES_FILE)) {
			Logger.getGlobal().log(Level.WARNING, "Project already initialized!");
			throw new ExecutionFailedException("Already initialized");
		}

		initSonarAssist(params);

		UpdatePropertyService.getFactory().create().execute(null);
	}

	/**
	 * Generate sonar-assist.properties
	 * @throws ExecutionFailedException
	 */
	private void initSonarAssist(Map<String, String> params) throws ExecutionFailedException {
		String project = null;
		String src = null;
		String bin = null;
		String lib = null;
		String login = null;
		String password = null;

		if (params != null) {
			project = params.get(SonarGlobal.SONAR_PROJECT_NAME);
			src = formatPath(params.get(SonarGlobal.SONAR_SOURCE));
			bin = formatPath(params.get(SonarGlobal.SONAR_BINARY));
			lib = formatPath(params.get(SonarGlobal.SONAR_LIBRARY));
			login = params.get(SonarGlobal.SONAR_LOGIN);
			password = params.get(SonarGlobal.SONAR_PASSWORD);
		}

		PropertyFile file = new PropertyFile();
		file.put(SonarGlobal.SONAR_PROJECT_NAME, project, SonarGlobal.INVALID_PROPERTY);
		file.put(SonarGlobal.SONAR_SOURCE, src, SonarGlobal.INVALID_PROPERTY);
		file.put(SonarGlobal.SONAR_BINARY, bin, SonarGlobal.INVALID_PROPERTY);
		file.put(SonarGlobal.SONAR_LIBRARY, lib, SonarGlobal.INVALID_PROPERTY);
		file.put(SonarGlobal.SONAR_LOGIN, login, SonarGlobal.INVALID_PROPERTY);
		file.put(SonarGlobal.SONAR_PASSWORD, password, SonarGlobal.INVALID_PROPERTY);

		try {
			file.save(Paths.get(SonarGlobal.SONAR_ASSIST_PROPERTIES_FILE));
		} catch (IOException e) {
			Logger.getGlobal().log(Level.SEVERE, "Failed to create init file due to IOException");
			throw new ExecutionFailedException("Could not create init file");
		}
	}

	private String formatPath(String path) {
		return (path == null) ? null : path.replace('\\', '/');
	}
}
