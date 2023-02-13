package main.sonar.exec.impl;

import main.sonar.common.exceptions.ExecutionFailedException;
import main.sonar.exec.ISonarExecutable;
import main.sonar.exec.ISonarExecutableFactory;
import main.sonar.common.SonarGlobal;
import main.sonar.utils.FileUtil;
import main.sonar.utils.PropertyFile;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.Paths;
import java.util.Map;
import java.util.UUID;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Update sonar-project.properties with sonar-assist.properties
 */
public class UpdatePropertyService implements ISonarExecutable {
	private UpdatePropertyService() {
	}

	public static ISonarExecutableFactory getFactory() {
		return new ISonarExecutableFactory() {
			@Override
			public ISonarExecutable create() {
				return new UpdatePropertyService();
			}
		};
	}

	@Override
	public void execute(Map<String, String> params) throws ExecutionFailedException {
		if (!FileUtil.isFile(SonarGlobal.SONAR_ASSIST_PROPERTIES_FILE)) {
			Logger.getGlobal().log(Level.SEVERE, "Missing essential file {0}", SonarGlobal.SONAR_ASSIST_PROPERTIES_FILE);
			throw new ExecutionFailedException("Missing " + SonarGlobal.SONAR_ASSIST_PROPERTIES_FILE);
		}

		PropertyFile file = new PropertyFile();

		loadSonarProperties(file);
		loadAssistProperties(file);
		updateSonarProperties(file);
	}

	private void loadSonarProperties(PropertyFile file) {
		try {
			file.load(Paths.get(SonarGlobal.SONAR_PROJECT_PROPERTIES_FILE));
		} catch (FileNotFoundException e) {
			// Nothing to load.
		} catch (IOException e) {
			// Nothing...
		}
	}

	private void loadAssistProperties(PropertyFile file) throws ExecutionFailedException {
		try {
			file.load(Paths.get(SonarGlobal.SONAR_ASSIST_PROPERTIES_FILE));
		} catch (FileNotFoundException e) {
			Logger.getGlobal().log(Level.SEVERE, "Missing {0}", SonarGlobal.SONAR_ASSIST_PROPERTIES_FILE);
			throw new ExecutionFailedException("Missing " + SonarGlobal.SONAR_ASSIST_PROPERTIES_FILE);
		} catch (IOException e) {
			Logger.getGlobal().log(Level.SEVERE, "Failed to load {0}", SonarGlobal.SONAR_ASSIST_PROPERTIES_FILE);
			throw new ExecutionFailedException("Cannot log " + SonarGlobal.SONAR_ASSIST_PROPERTIES_FILE);
		}
	}

	private void updateSonarProperties(PropertyFile file) throws ExecutionFailedException {
		if (file.get(SonarGlobal.SONAR_PROJECT_KEY) == null) {
			StringBuilder key = new StringBuilder(SonarGlobal.SONAR_PROJECT_KEY_PREFIX);
			key.append(UUID.randomUUID().toString());
			file.put(SonarGlobal.SONAR_PROJECT_KEY, key.toString());
		}

		/*
			Remove password entry if it is not set, which means token is used.
		 */
		reduceProperty(file, SonarGlobal.SONAR_PASSWORD);
		reduceProperty(file, SonarGlobal.SONAR_PROJECT_NAME);
		reduceProperty(file, SonarGlobal.SONAR_SOURCE);
		reduceProperty(file, SonarGlobal.SONAR_BINARY);
		reduceProperty(file, SonarGlobal.SONAR_LIBRARY);
		reduceProperty(file, SonarGlobal.SONAR_LOGIN);
		reduceProperty(file, SonarGlobal.SONAR_PASSWORD);

		try {
			file.save(Paths.get(SonarGlobal.SONAR_PROJECT_PROPERTIES_FILE));
		} catch (IOException e) {
			Logger.getGlobal().log(Level.SEVERE, "Failed to update {0}", SonarGlobal.SONAR_PROJECT_PROPERTIES_FILE);
			throw new ExecutionFailedException("Failed to update " + SonarGlobal.SONAR_PROJECT_PROPERTIES_FILE);
		}
	}

	private void reduceProperty(PropertyFile file, String property) {
		if (SonarGlobal.INVALID_PROPERTY.equals(file.get(property))) {
			file.remove(property);
		}
	}
}
