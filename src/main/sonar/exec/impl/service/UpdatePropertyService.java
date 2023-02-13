package main.sonar.exec.impl.service;

import main.sonar.common.SonarGlobal;
import main.sonar.common.exceptions.ExecutionFailedException;
import main.sonar.exec.ISonarExecutable;
import main.sonar.exec.ISonarExecutableFactory;
import main.sonar.utils.PropertyFile;

import java.io.IOException;
import java.nio.file.Paths;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Update one or more properties.
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

	@Override
	public void execute(Map<String, String> params) throws ExecutionFailedException {
		if (params == null) {
			Logger.getGlobal().log(Level.INFO, "Nothing to update...");
			return;
		}

		PropertyFile file = new PropertyFile();
		try {
			file.load(SonarGlobal.SONAR_ASSIST_PROPERTIES_FILE);
		} catch (IOException e) {
			Logger.getGlobal().log(Level.SEVERE, "Did you forget to initialize?");
			throw new ExecutionFailedException("Could not load property file", e);
		}

		for (Map.Entry<String, String> param : params.entrySet()) {
			file.put(param.getKey(), param.getValue());
		}

		try {
			file.save(SonarGlobal.SONAR_ASSIST_PROPERTIES_FILE);
		} catch (IOException e) {
			Logger.getGlobal().log(Level.SEVERE, "Failed to save property file");
			throw new ExecutionFailedException("Could not save property file", e);
		}

		GeneratePropertyService.getFactory().create().execute(null);
	}
}
