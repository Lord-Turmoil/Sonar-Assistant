package test.sonar.request.impl;

import main.sonar.request.ISonarRequest;
import main.sonar.request.RequestHost;
import main.sonar.request.impl.LogoutRequest;
import test.sonar.request.RequestTestBase;

public class LogoutRequestTest extends RequestTestBase {
	public static void main(String[] args) {
		RequestHost host = new RequestHost("http://tonys-studio.top:9000");

		ISonarRequest request = new LogoutRequest();

		send(host, request, null, "logout");
	}
}
