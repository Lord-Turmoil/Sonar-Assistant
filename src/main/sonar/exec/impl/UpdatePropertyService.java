package main.sonar.exec.impl;

import main.sonar.exec.ExecutionFailedException;
import main.sonar.exec.ISonarExecutable;
import main.sonar.exec.ISonarExecutableFactory;
import main.sonar.global.SonarGlobal;
import main.sonar.utils.FileUtil;
import main.sonar.utils.PropertyFile;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Map;
import java.util.UUID;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Update sonar-project.properties with
 */
public class UpdatePropertyService implements ISonarExecutable {
	private UpdatePropertyService() {}
	public static ISonarExecutableFactory getFactory() {
		return new ISonarExecutableFactory() {
			@Override
			public ISonarExecutable create() {
				return new UpdatePropertyService();
			}
		};
	}

	private static final String SONAR_PROJECT_KEY = "sonar.projectKey";
	private static final String SONAR_PROJECT_NAME = "sonar.projectName";
	private static final String SONAR_VERSION = "sonar.version";
	private static final String SONAR_SOURCE = "sonar.source";
	private static final String SONAR_BINARY = "sonar.binary";
	private static final String SONAR_LIBRARY = "sonar.library";
	private static final String SONAR_LOGIN = "sonar.login";
	private static final String SONAR_PASSWORD = "sonar.password";

	@Override
	public void execute(Map<String, String> params) throws ExecutionFailedException {
		if (!FileUtil.isFile(SonarGlobal.SONAR_ASSIST_PROPERTIES_FILE)) {
			Logger.getGlobal().log(Level.SEVERE, "Missing essential file {0}", SonarGlobal.SONAR_ASSIST_PROPERTIES_FILE);
			throw new ExecutionFailedException("Missing " + SonarGlobal.SONAR_ASSIST_PROPERTIES_FILE);
		}

		Path sonarAssistFilePath = Paths.get(SonarGlobal.SONAR_ASSIST_PROPERTIES_FILE);
		Path sonarProjectFilePath = Paths.get(SonarGlobal.SONAR_PROJECT_PROPERTIES_FILE);

		// Load
		PropertyFile file = new PropertyFile();
		try {
			file.load(sonarAssistFilePath);
		} catch (FileNotFoundException e) {
			Logger.getGlobal().log(Level.SEVERE, "Missing {0}", SonarGlobal.SONAR_ASSIST_PROPERTIES_FILE);
			throw new ExecutionFailedException("Missing " + SonarGlobal.SONAR_ASSIST_PROPERTIES_FILE);
		} catch (IOException e) {
			Logger.getGlobal().log(Level.SEVERE, "IOException occurred, could not update");
			throw new ExecutionFailedException("IOException occurred");
		}

		if (file.get(SONAR_PROJECT_KEY) == null) {
			file.put(SONAR_PROJECT_KEY, UUID.randomUUID().toString());
		}
		file.put(SONAR_PROJECT_NAME, params.get("project"));
		file.put(SONAR_VERSION, "0.1.0");
		file.put(SONAR_SOURCE, params.get("src"));
		file.put(SONAR_BINARY, params.get("bin"));
		file.put(SONAR_LIBRARY, params.get("lib"));
		file.put(SONAR_LOGIN, SonarGlobal.VISITOR_LOGIN);
		file.put(SONAR_PASSWORD, SonarGlobal.VISITOR_PASSWORD);

		try {
			file.save(Paths.get(SonarGlobal.SONAR_ASSIST_PROPERTIES_FILE));
		} catch (IOException e) {
			Logger.getGlobal().log(Level.SEVERE, "Failed to update {0}", SonarGlobal.SONAR_PROJECT_PROPERTIES_FILE);
			throw new ExecutionFailedException("Could not update " + SonarGlobal.SONAR_PROJECT_PROPERTIES_FILE);
		}
	}
}
