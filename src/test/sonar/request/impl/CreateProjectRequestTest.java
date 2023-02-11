package test.sonar.request.impl;

import main.sonar.request.ISonarRequest;
import main.sonar.request.RequestHost;
import main.sonar.request.impl.CreateProjectRequest;
import test.sonar.request.RequestTestBase;

import java.util.HashMap;

public class CreateProjectRequestTest extends RequestTestBase {
	public static void main(String[] args) {
		RequestHost host = new RequestHost("http://tonys-studio.top:9000");

		ISonarRequest request = new CreateProjectRequest();
		HashMap<String, String> params = new HashMap<>();
		params.put("name", "bot-test-2");

		send(host, request, params, "create project");
	}
}
