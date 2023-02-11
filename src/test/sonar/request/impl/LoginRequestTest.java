package test.sonar.request.impl;

import main.sonar.global.SonarGlobal;
import main.sonar.request.ISonarRequest;
import main.sonar.request.RequestHost;
import main.sonar.request.impl.LoginRequest;
import test.sonar.request.RequestTestBase;

import java.util.HashMap;

public class LoginRequestTest extends RequestTestBase {
	public static void main(String[] args) {
		RequestHost host = new RequestHost("http://tonys-studio.top:9000");

		ISonarRequest request = new LoginRequest();
		HashMap<String, String> params = new HashMap<>();
		params.put("login", SonarGlobal.VISITOR_LOGIN);
		params.put("password", SonarGlobal.VISITOR_PASSWORD);

		send(host, request, params, "login");
	}
}
