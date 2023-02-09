package test.sonar.request;

import main.sonar.request.RequestFailedException;
import main.sonar.request.RequestHost;
import main.sonar.request.RequestMethod;
import okhttp3.Response;

import java.io.IOException;

public class RequestHostTest {
	public static void main(String[] args) {
		RequestHost host = new RequestHost();
		Response response;

		// server must have protocol!
		host.setServer("http://www.tonys-studio.top:9000");

		// login test
		response = null;
		host.setApi("api/authentication/login");
		host.addParam("login", "visitor");
		host.addParam("password", "password");
		try {
			response = host.send(RequestMethod.POST);
		} catch (RequestFailedException e) {
			e.printStackTrace();
		}
		showResponse(response, "login");


		// validate text
		response = null;
		host.setApi("api/authentication/validate");
		host.clearParams();
		try {
			response = host.send(RequestMethod.GET);
		} catch (RequestFailedException e) {
			e.printStackTrace();
		}
		showResponse(response, "validate");
	}

	private static void showResponse(Response response, String title) {
		System.out.print("---------- ");
		System.out.println(title);

		if (response == null) {
			System.out.println("Null");
		} else {
			System.out.print("Code: ");
			System.out.println(response.code());
			System.out.println("Body:");
			try {
				System.out.println(response.body().string());
			} catch (IOException e) {
				System.out.println("\nIOException");
			}
		}
	}
}
