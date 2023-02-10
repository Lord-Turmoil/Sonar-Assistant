package test.sonar.request.impl;

import main.sonar.request.ISonarRequest;
import main.sonar.request.RequestFailedException;
import main.sonar.request.RequestHost;
import main.sonar.request.impl.LogoutRequest;
import main.sonar.request.impl.ValidateRequest;
import main.sonar.utils.ResponsePresenter;

public class LogoutRequestTest {
	public static void main(String[] args) {
		RequestHost host = new RequestHost("http://tonys-studio.top:9000");

		ISonarRequest request = new LogoutRequest();
		try {
			ResponsePresenter.show(request.send(host, null), "----- logout");
		} catch (RequestFailedException e) {
			System.out.println(e);
		}
	}
}
