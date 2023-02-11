package main.sonar.exec.impl;

import main.sonar.exec.ExecutionFailedException;
import main.sonar.exec.ISonarExecutable;
import main.sonar.exec.ISonarExecutableFactory;
import main.sonar.global.SonarGlobal;
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
	 *               project -- sonar.projectName and sonar.projectKey
	 *               src     -- sonar.sources
	 *               bin     -- sonar.java.binaries
	 *               lib     -- sonar.java.libraries
	 * @throws ExecutionFailedException
	 */
	@Override
	public void execute(Map<String, String> params) throws ExecutionFailedException {
		if (FileUtil.exists(SonarGlobal.SONAR_ASSIST_PROPERTIES_FILE)) {
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

		if (params != null) {
			project = params.get("project");
			src = formatPath(params.get("src"));
			bin = formatPath(params.get("bin"));
			lib = formatPath(params.get("lib"));
		}

		PropertyFile file = new PropertyFile();
		file.put("project", project, SonarGlobal.INVALID_PROPERTY);
		file.put("src", src, SonarGlobal.INVALID_PROPERTY);
		file.put("bin", bin, SonarGlobal.INVALID_PROPERTY);
		file.put("lib", lib, SonarGlobal.INVALID_PROPERTY);

		try {
			file.save(Paths.get(SonarGlobal.SONAR_ASSIST_PROPERTIES_FILE));
		} catch (IOException e) {
			Logger.getGlobal().log(Level.SEVERE, "Failed to create init file due to IOException");
			throw new ExecutionFailedException("Could not create init file");
		}
	}

	/**
	 * Generate sonar-project.properties based on sonar-assist.properties.
	 * @throws ExecutionFailedException
	 */
	private void initSonarQube() throws ExecutionFailedException {
	}

	private String formatPath(String path) {
		return (path == null) ? null : path.replace('\\', '/');
	}
}
