package main.sonar.request.impl;

import main.sonar.api.SonarQubeApi;
import main.sonar.api.SonarQubeApiEnum;
import main.sonar.api.SonarQubeApiFactory;
import main.sonar.common.exceptions.PropertyNotSetException;
import main.sonar.common.SonarGlobal;
import main.sonar.request.ISonarRequest;
import main.sonar.common.exceptions.RequestFailedException;
import main.sonar.request.RequestHost;
import main.sonar.request.SonarResponse;

import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

public class BulkDeleteProjectsRequest implements ISonarRequest {
	/**
	 * Bulk delete projects.
	 *
	 * @param host   host
	 * @param params projects, comma-separated project keys
	 * @return response
	 * @throws RequestFailedException
	 */
	@Override
	public SonarResponse send(RequestHost host, Map<String, String> params) throws RequestFailedException {
		if (params == null) {
			Logger.getGlobal().log(Level.SEVERE, "Must provide parameters!");
			throw new RequestFailedException("Arguments missing");
		}

		String projects = params.get("projects");

		if (projects == null) {
			Logger.getGlobal().log(Level.SEVERE, "Missing projects");
			throw new RequestFailedException("Arguments illegal");
		}

		SonarQubeApi api= SonarQubeApiFactory.get(SonarQubeApiEnum.PROJECT_BULK_DELETE);

		host.clear();
		host.setApi(api.getApi());
		host.addParam("projects", projects);
		try {
			host.setAuthorization(SonarGlobal.getSonarLogin(), SonarGlobal.getSonarPassword());
		} catch (PropertyNotSetException e) {
			Logger.getGlobal().log(Level.SEVERE, "Did you forget to set login and password?");
			throw new RequestFailedException("Authorization info missing");
		}

		return new SonarResponse(host.send(api.getMethod()));
	}
}
