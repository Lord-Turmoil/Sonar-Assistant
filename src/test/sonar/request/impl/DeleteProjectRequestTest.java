package test.sonar.request.impl;

import main.sonar.common.exceptions.PropertyNotSetException;
import main.sonar.common.SonarGlobal;
import main.sonar.request.ISonarRequest;
import main.sonar.request.RequestHost;
import main.sonar.request.impl.DeleteProjectRequest;
import test.sonar.global.SonarGlobalTest;
import test.sonar.request.RequestTestBase;

import java.util.HashMap;

public class DeleteProjectRequestTest extends RequestTestBase {
	public static void main(String[] args) throws PropertyNotSetException {
		SonarGlobalTest.init();

		RequestHost host = new RequestHost(SonarGlobal.getSonarServer());

		ISonarRequest request = new DeleteProjectRequest();
		HashMap<String, String> params = new HashMap<>();
		params.put("project", "bot-test-2");

		send(host, request, params, "delete project");
	}
}
