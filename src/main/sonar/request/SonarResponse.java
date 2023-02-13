package main.sonar.request;

import okhttp3.Response;
import okhttp3.ResponseBody;
import org.json.JSONObject;

import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

public class SonarResponse {
	private int code;
	public int getCode() {
		return code;
	}


	private JSONObject body;
	public JSONObject getBody() {
		return body;
	}

	public SonarResponse(Response response) {
		code = response.code();
		ResponseBody responseBody = response.body();
		if (responseBody == null) {
			body = new JSONObject();
		} else {
			try (responseBody) {
				body = new JSONObject(responseBody.string());
			} catch (IOException e) {
				Logger.getGlobal().log(Level.SEVERE, "Response too long?");
				body = new JSONObject();
			}
		}
	}
}
