/**
 * Emm... A server.
 */

package main.sonar.request;

public class SonarQube {
	private SonarQube() {}

	private static RequestHost host = null;
	public static RequestHost getHost() {
		if (host == null) {
			synchronized (SonarQube.class) {
				if (host == null) {
					host = new RequestHost();
				}
			}
		}
		return host;
	}
}
