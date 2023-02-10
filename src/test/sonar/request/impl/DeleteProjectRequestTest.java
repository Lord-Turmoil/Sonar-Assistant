package test.sonar.request.impl;

import main.sonar.request.ISonarRequest;
import main.sonar.request.RequestFailedException;
import main.sonar.request.RequestHost;
import main.sonar.request.impl.CreateProjectRequest;
import main.sonar.request.impl.DeleteProjectRequest;
import main.sonar.utils.ResponsePresenter;

import java.util.HashMap;

public class DeleteProjectRequestTest {
	public static void main(String[] args) {
		RequestHost host = new RequestHost("http://tonys-studio.top:9000");

		ISonarRequest request = new DeleteProjectRequest();
		HashMap<String, String> params = new HashMap<>();
		params.put("project", "bot-test-2");
		try {
			ResponsePresenter.show(request.send(host, params), "----- delete project");
		} catch (RequestFailedException e) {
			System.out.println(e);
		}
	}
}
