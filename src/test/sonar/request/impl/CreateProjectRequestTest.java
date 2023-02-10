package test.sonar.request.impl;

import main.sonar.request.ISonarRequest;
import main.sonar.request.RequestFailedException;
import main.sonar.request.RequestHost;
import main.sonar.request.impl.CreateProjectRequest;
import main.sonar.request.impl.ValidateRequest;
import main.sonar.utils.ResponsePresenter;

import java.util.HashMap;

public class CreateProjectRequestTest {
	public static void main(String[] args) {
		RequestHost host = new RequestHost("http://tonys-studio.top:9000");

		ISonarRequest request = new CreateProjectRequest();
		HashMap<String, String> params = new HashMap<>();
		params.put("name", "bot-test");
		try {
			ResponsePresenter.show(request.send(host, params), "----- create project");
		} catch (RequestFailedException e) {
			System.out.println(e);
		}
	}
}
