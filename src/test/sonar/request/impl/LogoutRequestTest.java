package test.sonar.request.impl;

import main.sonar.common.exceptions.PropertyNotSetException;
import main.sonar.common.SonarGlobal;
import main.sonar.request.ISonarRequest;
import main.sonar.request.RequestHost;
import main.sonar.request.impl.LogoutRequest;
import test.sonar.global.SonarGlobalTest;
import test.sonar.request.RequestTestBase;

public class LogoutRequestTest extends RequestTestBase {
	public static void main(String[] args) throws PropertyNotSetException {
		SonarGlobalTest.init();

		RequestHost host = new RequestHost(SonarGlobal.getSonarServer());

		ISonarRequest request = new LogoutRequest();

		send(host, request, null, "logout");
	}
}
