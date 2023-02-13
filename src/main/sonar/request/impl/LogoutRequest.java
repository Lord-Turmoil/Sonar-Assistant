package main.sonar.request.impl;

import main.sonar.api.SonarQubeApi;
import main.sonar.api.SonarQubeApiEnum;
import main.sonar.api.SonarQubeApiFactory;
import main.sonar.request.ISonarRequest;
import main.sonar.common.exceptions.RequestFailedException;
import main.sonar.request.RequestHost;
import main.sonar.request.SonarResponse;

import java.util.Map;

public class LogoutRequest implements ISonarRequest {
	@Override
	public SonarResponse send(RequestHost host, Map<String, String> params) throws RequestFailedException {
		SonarQubeApi api = SonarQubeApiFactory.get(SonarQubeApiEnum.LOGOUT);
		host.clear();
		host.setApi(api.getApi());
		return new SonarResponse(host.send(api.getMethod()));
	}
}
