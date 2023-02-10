package main.sonar.request.impl;

import com.sun.java.accessibility.util.TopLevelWindowListener;
import main.sonar.api.SonarQubeApi;
import main.sonar.api.SonarQubeApiEnum;
import main.sonar.api.SonarQubeApiFactory;
import main.sonar.global.SonarGlobal;
import main.sonar.request.ISonarRequest;
import main.sonar.request.RequestFailedException;
import main.sonar.request.RequestHost;
import okhttp3.Response;

import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

public class DeleteProjectRequest implements ISonarRequest {
	@Override
	public Response send(RequestHost host, Map<String, String> params) throws RequestFailedException {
		if (params == null) {
			Logger.getGlobal().log(Level.SEVERE, "Must provide parameters!");
			throw new RequestFailedException("Arguments missing");
		}

		String project = params.get("project");

		if (project == null) {
			Logger.getGlobal().log(Level.SEVERE, "Missing project");
			throw new RequestFailedException("Arguments illegal");
		}

		SonarQubeApi api= SonarQubeApiFactory.get(SonarQubeApiEnum.PROJECT_DELETE);

		host.clear();
		host.setApi(api.getApi());
		host.addParam("project", project);
		host.setAuthorization(SonarGlobal.VISITOR_USERNAME, SonarGlobal.VISITOR_PASSWORD);

		return host.send(api.getMethod());
	}
}
