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

public class SearchProjectsRequest implements ISonarRequest {
	/**
	 * Search projects.
	 * @param host host
	 * @param params projects -- comma-separated list of project keys
	 *               q        -- limit search to projects contains given string
	 *               ps       -- page size, range between [1, 500], 100 by default
	 *               p        -- 1-based page
	 *
	 * @return
	 * @throws RequestFailedException
	 */
	@Override
	public Response send(RequestHost host, Map<String, String> params) throws RequestFailedException {
		String projects = null;
		String q = null;
		String ps = null;
		String p = null;

		if (params != null) {
			projects = params.get("projects");
			q = params.get("q");
			ps = params.get("ps");
			p = params.get("p");
		}

		SonarQubeApi api = SonarQubeApiFactory.get(SonarQubeApiEnum.PROJECT_SEARCH);
		host.clear();
		host.setApi(api.getApi());
		host.addParam("projects", projects)
				.addParam("q", q)
				.addParam("ps", ps)
				.addParam("p", p);
		host.setAuthorization(SonarGlobal.ADMIN_LOGIN, SonarGlobal.ADMIN_PASSWORD);

		return host.send(api.getMethod());
	}
}
