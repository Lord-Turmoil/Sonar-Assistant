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

public class InitPropertyService implements ISonarExecutable {
	private InitPropertyService() {}
	public static ISonarExecutableFactory getFactory() {
		return new ISonarExecutableFactory() {
			@Override
			public ISonarExecutable create() {
				return new InitPropertyService();
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
		if (FileUtil.exists(SonarGlobal.SONAR_ASSIST_PROPERTIES_FILE)) {
			Logger.getGlobal().log(Level.WARNING, "Property already initialized!");
			if (!overwrite) {
				throw new ExecutionFailedException("Already initialized");
			}
		}

		PropertyFile file = new PropertyFile();
		String name = null;
		String src = null;
		String bin = null;
		String lib = null;

		if (params != null) {
			name = params.get(SonarGlobal.SONAR_PROJECT_NAME);
			src = formatPath(params.get(SonarGlobal.SONAR_SOURCE));
			bin = formatPath(params.get(SonarGlobal.SONAR_BINARY));
			lib = formatPath(params.get(SonarGlobal.SONAR_LIBRARY));
		}
		file.put(SonarGlobal.SONAR_PROJECT_NAME, name, SonarGlobal.INVALID_PROPERTY);
		file.put(SonarGlobal.SONAR_SOURCE, src, SonarGlobal.INVALID_PROPERTY);
		file.put(SonarGlobal.SONAR_BINARY, bin, SonarGlobal.INVALID_PROPERTY);
		file.put(SonarGlobal.SONAR_LIBRARY, lib, SonarGlobal.INVALID_PROPERTY);

		try {
			file.save(SonarGlobal.SONAR_ASSIST_PROPERTIES_FILE);
		} catch (IOException e) {
			Logger.getGlobal().log(Level.SEVERE, "Failed to save properties");
			throw new ExecutionFailedException("Failed to save properties", e);
		}
	}

	private String formatPath(String path) {
		return (path == null) ? null : path.replace('\\', '/');
	}
}
