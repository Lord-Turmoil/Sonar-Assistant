package test.sonar.global;

import main.sonar.common.SonarGlobal;

public class SonarGlobalTest {
	private SonarGlobalTest() {}

	public static final String VISITOR_LOGIN = "bot";
	public static final String VISITOR_PASSWORD = "bot123456";

	// Login can be replaced by token.
	public static final String ADMIN_LOGIN = " squ_dbde44401a96fe0ef6748959ad2cde6a67a8fe7d";
	public static final String ADMIN_PASSWORD = "";

	public static final String SONAR_SERVER = "http://tonys-studio.top:9000";
	public static void init() {
		SonarGlobal.setSonarServer(SONAR_SERVER);
		SonarGlobal.setSonarLogin(ADMIN_LOGIN);
		SonarGlobal.setSonarPassword(ADMIN_PASSWORD);
	}
}
