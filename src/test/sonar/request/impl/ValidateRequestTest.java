package test.sonar.request.impl;

import main.sonar.global.PropertyNotSetException;
import main.sonar.global.SonarGlobal;
import main.sonar.request.ISonarRequest;
import main.sonar.request.RequestHost;
import main.sonar.request.impl.LoginRequest;
import main.sonar.request.impl.ValidateRequest;
import test.sonar.global.SonarGlobalTest;
import test.sonar.request.RequestTestBase;

import java.util.HashMap;

public class ValidateRequestTest extends RequestTestBase {
	public static void main(String[] args) throws PropertyNotSetException {
		SonarGlobalTest.init();

		RequestHost host = new RequestHost(SonarGlobal.getSonarServer());

		ISonarRequest request = new ValidateRequest();
		send(host, request, null, "validate");

		request = new LoginRequest();
		HashMap<String, String> params = new HashMap<>();
		params.put("login", SonarGlobal.getSonarLogin());
		params.put("password", SonarGlobal.getSonarPassword());
		send(host, request, params, "login");

		request = new ValidateRequest();
		send(host, request, null, "validate");
	}
}
