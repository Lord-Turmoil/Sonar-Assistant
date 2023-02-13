package main.sonar.exec.impl.command;

import main.sonar.common.exceptions.ExecutionFailedException;
import main.sonar.exec.ISonarExecutable;
import main.sonar.exec.ISonarExecutableFactory;
import main.sonar.common.SonarGlobal;
import main.sonar.exec.impl.service.InitConfigService;
import main.sonar.exec.impl.service.InitPropertyService;
import main.sonar.exec.impl.service.GeneratePropertyService;
import main.sonar.utils.FileUtil;

import java.util.Map;

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
	 * Initialize properties. Can have many parameters.
	 * @param params optional parameters.
	 *
	 * @throws ExecutionFailedException
	 */
	@Override
	public void execute(Map<String, String> params) throws ExecutionFailedException {
		if (!FileUtil.mkdirs(SonarGlobal.SONAR_ASSIST_DIR)) {
			throw new ExecutionFailedException("Failed to create root directory");
		}

		ISonarExecutable initConfig = InitConfigService.getFactory().create();
		initConfig.execute(params);

		ISonarExecutable initProperty = InitPropertyService.getFactory().create();
		initProperty.execute(params);

		ISonarExecutable generateProperty = GeneratePropertyService.getFactory().create();
		generateProperty.execute(null);
	}
}
