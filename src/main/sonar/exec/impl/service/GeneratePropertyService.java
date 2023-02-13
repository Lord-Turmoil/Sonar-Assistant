package main.sonar.exec.impl.service;

import main.sonar.common.exceptions.ExecutionFailedException;
import main.sonar.common.exceptions.PropertyNotSetException;
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
public class GeneratePropertyService implements ISonarExecutable {
	private GeneratePropertyService() {
	}

	public static ISonarExecutableFactory getFactory() {
		return new ISonarExecutableFactory() {
			@Override
			public ISonarExecutable create() {
				return new GeneratePropertyService();
			}
		};
	}

	@Override
	public void execute(Map<String, String> params) throws ExecutionFailedException {
		if (!FileUtil.isFile(SonarGlobal.SONAR_ASSIST_PROPERTIES_FILE)) {
			Logger.getGlobal().log(Level.SEVERE, "Missing essential file {0}", SonarGlobal.SONAR_ASSIST_PROPERTIES_FILE);
			throw new ExecutionFailedException("Missing " + SonarGlobal.SONAR_ASSIST_PROPERTIES_FILE);
		}

		PropertyFile assist = new PropertyFile();
		PropertyFile sonar = new PropertyFile();

		loadSonarProperties(sonar);
		loadAssistProperties(assist);
		updateSonarProperties(assist, sonar);
	}

	private void loadSonarProperties(PropertyFile file) {
		try {
			file.load(SonarGlobal.SONAR_PROJECT_PROPERTIES_FILE);
		} catch (FileNotFoundException e) {
			// Nothing to load.
		} catch (IOException e) {
			// Nothing...
		}
	}

	private void loadAssistProperties(PropertyFile file) throws ExecutionFailedException {
		try {
			file.load(SonarGlobal.SONAR_ASSIST_PROPERTIES_FILE);
		} catch (FileNotFoundException e) {
			Logger.getGlobal().log(Level.SEVERE, "Missing {0}", SonarGlobal.SONAR_ASSIST_PROPERTIES_FILE);
			throw new ExecutionFailedException("Missing " + SonarGlobal.SONAR_ASSIST_PROPERTIES_FILE);
		} catch (IOException e) {
			Logger.getGlobal().log(Level.SEVERE, "Failed to load {0}", SonarGlobal.SONAR_ASSIST_PROPERTIES_FILE);
			throw new ExecutionFailedException("Cannot log " + SonarGlobal.SONAR_ASSIST_PROPERTIES_FILE);
		}
	}

	private void updateSonarProperties(PropertyFile src, PropertyFile dst) throws ExecutionFailedException {
		if (dst.get(SonarGlobal.SONAR_PROJECT_KEY) == null) {
			StringBuilder key = new StringBuilder(SonarGlobal.SONAR_PROJECT_KEY_PREFIX);
			key.append(UUID.randomUUID().toString());
			dst.put(SonarGlobal.SONAR_PROJECT_KEY, key.toString());
		}

		/*
			Remove password entry if it is not set, which means token is used.
		 */
		copyProperty(src, dst, SonarGlobal.SONAR_PROJECT_NAME);
		copyProperty(src, dst, SonarGlobal.SONAR_SOURCE);
		copyProperty(src, dst, SonarGlobal.SONAR_BINARY);
		copyProperty(src, dst, SonarGlobal.SONAR_LIBRARY);

		updateLogin(dst);

		try {
			dst.save(SonarGlobal.SONAR_PROJECT_PROPERTIES_FILE);
		} catch (IOException e) {
			Logger.getGlobal().log(Level.SEVERE, "Failed to update {0}", SonarGlobal.SONAR_PROJECT_PROPERTIES_FILE);
			throw new ExecutionFailedException("Failed to update " + SonarGlobal.SONAR_PROJECT_PROPERTIES_FILE);
		}
	}

	private void updateLogin(PropertyFile file) {
		String login = null;
		String password = null;
		try {
			login = SonarGlobal.getSonarLogin();
		} catch (PropertyNotSetException e) {
			Logger.getGlobal().log(Level.INFO, "Missing login");
		}
		try {
			password = SonarGlobal.getSonarPassword();
		} catch (PropertyNotSetException e) {
			Logger.getGlobal().log(Level.INFO, "Missing password");
		}

		if ((login != null) && (!SonarGlobal.INVALID_PROPERTY.equals(login))) {
			file.put(SonarGlobal.SONAR_LOGIN, login);
		}
		if ((password != null) && (!SonarGlobal.INVALID_PROPERTY.equals(password))) {
			file.put(SonarGlobal.SONAR_PASSWORD, password);
		}
	}

	private void copyProperty(PropertyFile src, String srcKey, PropertyFile dst, String dstKey) {
		String value = src.get(srcKey);
		if ((value != null) && (!SonarGlobal.INVALID_PROPERTY.equals(value))) {
			dst.put(dstKey, value);
		} else {
			dst.remove(dstKey);
		}
	}

	private void copyProperty(PropertyFile src, PropertyFile dst, String key) {
		String value = src.get(key);
		if ((value != null) && (!SonarGlobal.INVALID_PROPERTY.equals(value))) {
			// If value is null, put will do nothing.
			dst.put(key, value);
		} else {
			dst.remove(key);
		}
	}
}
