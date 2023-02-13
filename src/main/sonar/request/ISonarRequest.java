/**
 * SonarQube request.
 */

package main.sonar.request;

import main.sonar.common.exceptions.RequestFailedException;

import java.util.Map;

public interface ISonarRequest {
	SonarResponse send(RequestHost host, Map<String, String> params) throws RequestFailedException;
}
