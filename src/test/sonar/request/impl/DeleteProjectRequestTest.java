package test.sonar.request.impl;

import main.sonar.request.ISonarRequest;
import main.sonar.request.RequestHost;
import main.sonar.request.impl.DeleteProjectRequest;
import test.sonar.request.RequestTestBase;

import java.util.HashMap;

public class DeleteProjectRequestTest extends RequestTestBase {
	public static void main(String[] args) {
		RequestHost host = new RequestHost("http://tonys-studio.top:9000");

		ISonarRequest request = new DeleteProjectRequest();
		HashMap<String, String> params = new HashMap<>();
		params.put("project", "bot-test-2");
		send(host, request, params, "delete project");
	}
}
