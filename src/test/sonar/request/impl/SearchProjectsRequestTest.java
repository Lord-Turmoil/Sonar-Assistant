package test.sonar.request.impl;

import main.sonar.global.PropertyNotSetException;
import main.sonar.global.SonarGlobal;
import main.sonar.request.ISonarRequest;
import main.sonar.request.RequestHost;
import main.sonar.request.impl.SearchProjectsRequest;
import test.sonar.global.SonarGlobalTest;
import test.sonar.request.RequestTestBase;

public class SearchProjectsRequestTest extends RequestTestBase {
	public static void main(String[] args) throws PropertyNotSetException {
		SonarGlobalTest.init();

		RequestHost host = new RequestHost(SonarGlobal.getSonarServer());
		ISonarRequest request = new SearchProjectsRequest();

		send(host, request, null, "search projects");
	}
}
