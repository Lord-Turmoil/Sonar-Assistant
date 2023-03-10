package main.sonar.request.impl;

import main.sonar.api.SonarQubeApi;
import main.sonar.api.SonarQubeApiEnum;
import main.sonar.api.SonarQubeApiFactory;
import main.sonar.request.ISonarRequest;
import main.sonar.common.exceptions.RequestFailedException;
import main.sonar.request.RequestHost;
import main.sonar.request.SonarResponse;

import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

public class LoginRequest implements ISonarRequest {

	@Override
	public SonarResponse send(RequestHost host, Map<String, String> params) throws RequestFailedException {
		if (params == null) {
			Logger.getGlobal().log(Level.SEVERE, "Must provide parameters!");
			throw new RequestFailedException("Arguments missing");
		}

		String login = params.get("login");
		String password = params.get("password");

		if (login == null || password == null) {
			Logger.getGlobal().log(Level.SEVERE, "Must provide both login and password");
			throw new RequestFailedException("Arguments illegal");
		}

		SonarQubeApi api = SonarQubeApiFactory.get(SonarQubeApiEnum.LOGIN);

		host.reset();
		host.setApi(api.getApi());
		host.addParam("login", login).addParam("password", password);

		return new SonarResponse(host.send(api.getMethod()));
	}
}
