/**
 * SonarQube request.
 */

package main.sonar.request;

import main.sonar.request.RequestFailedException;
import main.sonar.request.RequestHost;
import okhttp3.Response;

import java.util.Map;

public interface ISonarRequest {
	Response send(RequestHost host, Map<String, String> params) throws RequestFailedException;
}
