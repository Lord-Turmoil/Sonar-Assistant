package main.sonar.request.impl;

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

public class CreateProjectRequest implements ISonarRequest {
	/**
	 * Create a project.
	 * name and project can't both be null. Will be set to the same if
	 * one of them is null.
	 * @param host request host
	 * @param params name       -- project name
	 *               project    -- project key
	 *               visibility -- public or private, public by default
	 * @return request response
	 * @throws RequestFailedException
	 */
	@Override
	public Response send(RequestHost host, Map<String, String> params) throws RequestFailedException {
		if (params == null) {
			Logger.getGlobal().log(Level.SEVERE, "Must provide parameters!");
			throw new RequestFailedException("Arguments missing");
		}

		String name = params.get("name");
		String project = params.get("project");
		String visibility = params.computeIfAbsent("visibility", (String k) -> "public");

		if (name == null && project == null) {
			Logger.getGlobal().log(Level.SEVERE, "name and project can't both be null.");
			throw new RequestFailedException("Arguments illegal");
		}

		if (name == null) {
			name = project;
		} else if (project == null) {
			project = name;
		}

		SonarQubeApi api = SonarQubeApiFactory.get(SonarQubeApiEnum.PROJECT_CREATE);

		// This request can't succeed when logged in! Must log out first, or clear cookies.
		host.reset();
		host.setApi(api.api);
		host.addParam("name", name).addParam("project", project).addParam("visibility", visibility);
		host.setAuthorization(SonarGlobal.VISITOR_USERNAME, SonarGlobal.VISITOR_PASSWORD);

		return host.send(api.method);
	}
}
