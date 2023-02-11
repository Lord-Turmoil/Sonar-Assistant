package test.sonar.request;

import main.sonar.request.ISonarRequest;
import main.sonar.request.RequestFailedException;
import main.sonar.request.RequestHost;
import main.sonar.utils.ResponsePresenter;

import java.util.HashMap;
import java.util.logging.Level;
import java.util.logging.Logger;

public class RequestTestBase {
	protected static void send(RequestHost host, ISonarRequest request, HashMap<String, String> params, String title) {
		System.out.print("----- ");
		System.out.println(title);
		try {
			ResponsePresenter.show(request.send(host, params));
		} catch (RequestFailedException e) {
			Logger.getGlobal().log(Level.SEVERE, e.toString());
		}
	}
}
