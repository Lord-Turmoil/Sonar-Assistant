package main.sonar.api;

public class SonarQubeApi {
	private RequestMethodEnum method;
	private String api;

	public SonarQubeApi(RequestMethodEnum method, String api) {
		this.method = method;
		this.api = api;
	}

	public RequestMethodEnum getMethod() {
		return method;
	}

	public String getApi() {
		return api;
	}
}
