/**
 * Deal with all requests. This solution is not general, since
 * SonarQube API does not accept request body, all parameters are
 * appended to the request url.
 */

package main.sonar.request;

import main.sonar.api.RequestMethodEnum;
import okhttp3.*;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Request host is an instance to make requests to one SonarQube server.
 * It is recommended that one host deals with one server, since it stores
 * the cookies during the request.
 */
public class RequestHost {
	public static final long CONNECT_TIMEOUT = 10;
	public static final long READ_TIMEOUT = 60;
	public static final long WRITE_TIMEOUT = 60;

	private OkHttpClient client;
	private final HashMap<String, List<Cookie>> cookieStore = new HashMap<>();

	private HashMap<String, String> parameters = new HashMap<>();;
	private HashMap<String, String> headers = new HashMap<>();
	private String api;	// target request api
	private String server;	// server url
	private String authorization = null;	// authentication, Basic Auth

	public RequestHost() {
		initializeClient();
	}

	public RequestHost(String server) {
		this.server = server;
		initializeClient();
	}

	public RequestHost(String server, String api) {
		this.server = server;
		this.api = api;
		initializeClient();
	}

	private void initializeClient() {
		OkHttpClient.Builder builder = new OkHttpClient.Builder();
		builder.cookieJar(new CookieJar() {
			@Override
			public void saveFromResponse(HttpUrl httpUrl, List<Cookie> list) {
				cookieStore.put(httpUrl.host(), list);
			}

			@Override
			public List<Cookie> loadForRequest(HttpUrl httpUrl) {
				List<Cookie> cookies = cookieStore.get(httpUrl.host());
				return (cookies != null) ? cookies : new ArrayList<Cookie>();
			}
		});
		builder.connectTimeout(CONNECT_TIMEOUT, TimeUnit.SECONDS)
				.readTimeout(READ_TIMEOUT, TimeUnit.SECONDS)
				.writeTimeout(WRITE_TIMEOUT, TimeUnit.SECONDS);
		client = builder.build();
	}


	//////////////////////////////////////// Basic Setters

	public RequestHost setApi(String api) {
		this.api = api;
		return this;
	}

	public RequestHost setServer(String server) {
		this.server = server;
		return this;
	}


	//////////////////////////////////////// Parameters

	/**
	 * Add one parameter.
	 * Key should not be the same, duplicated key will replace the
	 * old one.
	 * @param key parameter key
	 * @param value parameter value
	 * @return itself
	 */
	public RequestHost addParam(String key, String value) {
		parameters.put(key, value);
		return this;
	}

	public RequestHost removeParam(String key) {
		parameters.remove(key);
		return this;
	}

	public void clearParams() {
		parameters.clear();
	}


	//////////////////////////////////////// Headers

	public RequestHost addHeader(String key, String value) {
		headers.put(key, value);
		return this;
	}

	public RequestHost removeHeader(String key) {
		headers.remove(key);
		return this;
	}

	public void clearHeaders() {
		headers.clear();
	}


	//////////////////////////////////////// Authentication

	/**
	 * Apply Basic-Auth to the request.
	 * @param username username
	 * @param password password
	 * @return itself
	 */
	public RequestHost setAuthorization(String username, String password) {
		authorization = Credentials.basic(username, password);
		return this;
	}

	public void removeAuthorization() {
		authorization = null;
	}


	//////////////////////////////////////// Cookies

	public void clearCookies() {
		cookieStore.clear();
	}

	//////////////////////////////////////// Top functions

	/**
	 * Reset everything except server and api. Use with caution.
	 */
	public void reset() {
		clearParams();
		clearHeaders();
		clearCookies();
		removeAuthorization();
	}

	/**
	 * Clear unnecessary parameters and headers, and authorization.
	 * Cookies are kept
	 */
	public void clear() {
		clearParams();
		clearHeaders();
		removeAuthorization();
	}

	/**
	 * Send request.
	 * @return the server's response, null if anything bad happens
	 */
	public Response send(RequestMethodEnum method) throws RequestFailedException {
		Request.Builder builder  = new Request.Builder();

		// send method
		if (method == RequestMethodEnum.POST) {
			// SonarQube post do not have body
			builder.post(RequestBody.create(null, ""));
		}
		else if (method == RequestMethodEnum.GET) {
			builder.get();
		} else {
			throw new RequestFailedException("Unknown send method.");
		}

		// request url contains all parameters
		builder.url(generateRequestUrl());

		// apply Authorization if needed
		if (authorization != null) {
			builder.addHeader("Authorization", authorization);
		}

		// sending air~ mail!
		Response response = null;
		try {
			response = client.newCall(builder.build()).execute();
		} catch (IOException e) {
			Logger.getGlobal().log(Level.SEVERE, "IOException occurred when sending request");
			throw new RequestFailedException("IOException occurred");
		}

		return response;
	}


	//////////////////////////////////////// Private Util Functions
	/**
	 * Generate request url by api and parameters
	 * @return full request url
	 */
	private String generateRequestUrl() {
		StringBuilder url = new StringBuilder(server);
		if (!server.endsWith("/")) {
			 url.append("/");
		}
		url.append(api);
		if (api.endsWith("/")) {
			url.delete(url.length() - 1, url.length());
		}

		boolean isFirst = true;
		for (Map.Entry<String, String> param : parameters.entrySet()) {
			if (isFirst) {
				url.append('?');
				isFirst = false;
			} else {
				url.append('&');
			}

			url.append(param.getKey()).append("=").append(param.getValue());
		}

		return url.toString();
	}

	private void wrapHeaders(Request.Builder builder) {
		for (Map.Entry<String, String> header : headers.entrySet()) {
			builder.addHeader(header.getKey(), header.getValue());
		}
	}
}
