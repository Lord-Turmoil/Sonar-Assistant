/**
 * Err, just make it easier to show response.
 */
package main.sonar.utils;

import main.sonar.request.SonarResponse;
import okhttp3.Response;
import okhttp3.ResponseBody;
import org.json.JSONObject;

import java.io.IOException;

public class ResponseUtil {
	private ResponseUtil() {
	}

	public static void show(SonarResponse response) {
		if (response == null) {
			System.out.println("Null");
		} else {
			System.out.print("Code: ");
			System.out.println(response.getCode());
			System.out.println("Body:");
			System.out.println(response.getBody());
		}
	}

	public static String interpretCode(int code) {
		switch (code) {
			case 200:
				return "200 OK";
			case 204:
				return "204 No Content";
			case 400:
				return "400 Bad Request";
			case 401:
				return "401 Unauthorized";
			case 404:
				return "404 Not Found";
			default:
				return String.format("%d Unknown", code);
		}
	}

	public static JSONObject getResponseAsJSON(Response response) throws IOException {
		if (response != null) {
			ResponseBody body = response.body();
			if (body != null) {
				return new JSONObject(body.string());
			}
		}
		return new JSONObject();
	}
}
