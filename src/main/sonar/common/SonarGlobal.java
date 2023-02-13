package main.sonar.common;

import main.sonar.common.exceptions.PropertyNotSetException;

import java.util.logging.Level;
import java.util.logging.Logger;

public class SonarGlobal {
	private SonarGlobal() {}

	//////////////////// Umm... C# style?

	/**
	 * Login is preferred to be replaced by token! Then password should be
	 * set to empty string. However, in this case login request can not be
	 * performed, which matters not.
	 */
	private static String sonarLogin = null;
	public static void setSonarLogin(String login) {
		sonarLogin = login;
	}
	public static String getSonarLogin() throws PropertyNotSetException {
		if (sonarLogin == null) {
			Logger.getGlobal().log(Level.WARNING, "sonarLogin not set");
			throw new PropertyNotSetException();
		}
		return sonarLogin;
	}

	private static String sonarPassword;
	public static void setSonarPassword(String password) {
		sonarPassword = password;
	}
	public static String getSonarPassword() throws PropertyNotSetException {
		if (sonarLogin == null) {
			Logger.getGlobal().log(Level.WARNING, "sonarPassword not set");
			throw new PropertyNotSetException();
		}
		return sonarPassword;
	}

	private static String sonarServer = null;
	public static void setSonarServer(String server) {
		sonarServer = server;
	}
	public static String getSonarServer() throws PropertyNotSetException {
		if (sonarServer == null) {
			Logger.getGlobal().log(Level.WARNING, "sonarPassword not set");
			throw new PropertyNotSetException();
		}
		return sonarServer;
	}

	public static final String INVALID_PROPERTY = "";

	public static final String SONAR_ASSIST_DIR = "sonarassist/";
	public static final String SONAR_ASSIST_PROPERTIES_FILE = SONAR_ASSIST_DIR + "sonar-assist.properties";
	public static final String SONAR_ASSIST_CONFIG_FILE = SONAR_ASSIST_DIR + "sonar-config.properties";
	public static final String SONAR_PROJECT_PROPERTIES_FILE = "sonar.properties";

	//////////////////////////////////// SonarQube Properties
	public static final String SONAR_PROJECT_KEY = "sonar.projectKey";
	public static final String SONAR_PROJECT_NAME = "sonar.projectName";
	public static final String SONAR_VERSION = "sonar.version";
	public static final String SONAR_SOURCE = "sonar.source";
	public static final String SONAR_BINARY = "sonar.binary";
	public static final String SONAR_LIBRARY = "sonar.library";
	public static final String SONAR_LOGIN = "sonar.login";
	public static final String SONAR_PASSWORD = "sonar.password";

	//////////////////////////////////// Sonar Assist Configs
	public static final String ASSIST_QUALITY_PROFILE = "assist.qualityProfile";
	public static final String ASSIST_QUALITY_GATE = "assist.qualityGate";
	public static final String ASSIST_SERVER = "assist.server";
	public static final String ASSIST_LOGIN = "assist.login";
	public static final String ASSIST_PASSWORD = "assist.password";

	public static final String SONAR_PROJECT_KEY_PREFIX = "bOt_";


}
