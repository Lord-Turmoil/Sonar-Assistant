/**
 * SonarQube request.
 */

package main.sonar.request;

import okhttp3.Response;

import java.util.Map;

public interface ISonarRequest {
	Response send(RequestHost host, Map<String, String> params) throws RequestFailedException;
}
