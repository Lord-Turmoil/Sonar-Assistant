package test.sonar.request.impl;

import main.sonar.global.SonarGlobal;
import main.sonar.request.ISonarRequest;
import main.sonar.request.RequestFailedException;
import main.sonar.request.RequestHost;
import main.sonar.request.impl.LoginRequest;
import main.sonar.request.impl.ValidateRequest;
import main.sonar.utils.ResponsePresenter;

import java.util.HashMap;

public class ValidateRequestTest {
	public static void main(String[] args) {
		RequestHost host = new RequestHost("http://tonys-studio.top:9000");

		ISonarRequest request = new ValidateRequest();
		try {
			ResponsePresenter.show(request.send(host, null), "----- validate");
		} catch (RequestFailedException e) {
			System.out.println(e);
		}

		request = new LoginRequest();
		HashMap<String, String> params = new HashMap<>();
		params.put("login", SonarGlobal.VISITOR_USERNAME);
		params.put("password", SonarGlobal.VISITOR_PASSWORD);
		try {
			ResponsePresenter.show(request.send(host, params), "----- login");
		} catch (RequestFailedException e) {
			System.out.println(e);
		}

		request = new ValidateRequest();
		try {
			ResponsePresenter.show(request.send(host, null), "----- validate");
		} catch (RequestFailedException e) {
			System.out.println(e);
		}
	}
}
