/**
 * Err, just make it easier to show response.
 */
package main.sonar.utils;

import okhttp3.Response;

import java.io.IOException;

public class ResponsePresenter {
	private ResponsePresenter() {}

	public static void show(Response response) {
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
