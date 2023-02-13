package test.sonar.request;

import main.sonar.request.ISonarRequest;
import main.sonar.common.exceptions.RequestFailedException;
import main.sonar.request.RequestHost;
import main.sonar.utils.ResponseUtil;

import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

public class RequestTestBase {
	protected static void send(RequestHost host, ISonarRequest request, Map<String, String> params, String title) {
		System.out.print("----- Request Test: ");
		System.out.println(title);
		try {
			ResponseUtil.show(request.send(host, params));
		} catch (RequestFailedException e) {
			Logger.getGlobal().log(Level.SEVERE, e.toString());
		}
	}
}
