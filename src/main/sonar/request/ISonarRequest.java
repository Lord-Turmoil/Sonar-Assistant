/**
 * SonarQube request.
 */

package main.sonar.request;

import main.sonar.common.exceptions.RequestFailedException;
import okhttp3.Response;

import java.util.Map;

public interface ISonarRequest {
	Response send(RequestHost host, Map<String, String> params) throws RequestFailedException;
}
