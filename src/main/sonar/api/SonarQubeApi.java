package main.sonar.api;

public class SonarQubeApi {
	public RequestMethodEnum method;
	public String api;

	public SonarQubeApi(RequestMethodEnum method, String api) {
		this.method = method;
		this.api = api;
	}
}
