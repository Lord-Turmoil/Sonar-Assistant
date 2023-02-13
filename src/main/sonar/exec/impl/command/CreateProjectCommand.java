package main.sonar.exec.impl.command;

import main.sonar.common.SonarGlobal;
import main.sonar.common.exceptions.ExecutionFailedException;
import main.sonar.common.exceptions.RequestFailedException;
import main.sonar.exec.ISonarExecutable;
import main.sonar.exec.ISonarExecutableFactory;
import main.sonar.request.ISonarRequest;
import main.sonar.request.SonarQube;
import main.sonar.request.SonarResponse;
import main.sonar.request.impl.CreateProjectRequest;
import main.sonar.utils.PropertyFile;
import main.sonar.utils.ResponseUtil;
import okhttp3.ResponseBody;
import org.json.JSONArray;
import org.json.JSONObject;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;


/**
 * Create project based on sonar-project.properties.
 * needs name and key
 */
public class CreateProjectCommand implements ISonarExecutable {
	private CreateProjectCommand() {}
	public static ISonarExecutableFactory getFactory() {
		return new ISonarExecutableFactory() {
			@Override
			public ISonarExecutable create() {
				return new CreateProjectCommand();
			}
		};
	}

	@Override
	public void execute(Map<String, String> params) throws ExecutionFailedException {
		PropertyFile file = new PropertyFile();
		loadProperties(file);

		String name = file.get(SonarGlobal.SONAR_PROJECT_NAME);
		String key = file.get(SonarGlobal.SONAR_PROJECT_KEY);

		if ((name == null) || (key == null)) {
			Logger.getGlobal().log(Level.SEVERE, "Missing project Name and Project key");
			throw new ExecutionFailedException("Missing properties");
		}

		HashMap<String, String> p = new HashMap<>();
		p.put("name", name);
		p.put("project", key);

		sendRequest(p);
	}

	private void loadProperties(PropertyFile file) throws ExecutionFailedException {
		try {
			file.load(SonarGlobal.SONAR_PROJECT_PROPERTIES_FILE);
		} catch (FileNotFoundException e) {
			Logger.getGlobal().log(Level.SEVERE, "{0} not found", SonarGlobal.SONAR_PROJECT_PROPERTIES_FILE);
			throw new ExecutionFailedException("Missing " + SonarGlobal.SONAR_PROJECT_PROPERTIES_FILE);
		} catch (IOException e) {
			Logger.getGlobal().log(Level.SEVERE, "Could not load properties");
			throw new ExecutionFailedException("Could not load properties", e);
		}
	}

	private void sendRequest(Map<String, String> params) throws ExecutionFailedException {
		ISonarRequest request = new CreateProjectRequest();
		SonarResponse response = null;
		try {
			response = request.send(SonarQube.getHost(), params);
		} catch (RequestFailedException e) {
			Logger.getGlobal().log(Level.SEVERE, "Failed to create project: {0}", e.toString());
			throw new ExecutionFailedException("Failed to create project", e);
		}

		handleResponse(response);
	}

	private void handleResponse(SonarResponse response) throws ExecutionFailedException {
		if (response == null) {
			throw new ExecutionFailedException("Error response!");
		}

		int code = response.getCode();
		switch (response.getCode()) {
			case 200:
				response200(response);
				break;
			case 400:
				response400(response);
				break;
			default:
				responseUnknown(response);
				break;
		}
	}

	private void response200(SonarResponse response) throws ExecutionFailedException {
		JSONObject body = response.getBody();
		if (body.has("project")) {
			JSONObject project = body.getJSONObject("project");
			Logger.getGlobal().log(Level.INFO, "Project {0} created!", project.getString("name"));
		} else {
			Logger.getGlobal().log(Level.SEVERE, "Good response, bad content");
			throw new ExecutionFailedException();
		}
	}

	private void response400(SonarResponse response) throws ExecutionFailedException {
		JSONArray errors = response.getBody().getJSONArray("errors");
		String msg = null;
		if (errors != null) {
			JSONObject obj = errors.getJSONObject(0);
			if (obj != null) {
				msg = obj.getString("msg");
			}
		}
		Logger.getGlobal().log(Level.SEVERE, (msg == null) ? "Unknown error" : msg);
		throw new ExecutionFailedException(ResponseUtil.interpretCode(response.getCode()));
	}

	private void responseUnknown(SonarResponse response) throws ExecutionFailedException {
		String msg = ResponseUtil.interpretCode(response.getCode());
		Logger.getGlobal().log(Level.SEVERE, msg);
		throw new ExecutionFailedException(msg);
	}
}
