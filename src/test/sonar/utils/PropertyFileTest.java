package test.sonar.utils;

import main.sonar.utils.PropertyFile;

import java.io.IOException;

public class PropertyFileTest {
	public static void main(String[] args) {
		PropertyFile file = new PropertyFile();

		System.out.println("----- Load");
		try {
			file.load("res/sonar-project.properties");
		} catch (IOException e) {
			e.printStackTrace();
		}

//		System.out.println("--- Modification");
//		file.put("custom", "balbalbla");
//		file.remove("sources");

		System.out.println("----- Load");
		try {
			file.load("res/sonar.properties");
		} catch (IOException e) {
			e.printStackTrace();
		}

		System.out.println("----- Save");
		try {
			file.save("res/mix.properties");
		} catch (IOException e) {
			e.printStackTrace();
		}

		System.out.println("----- Show");
		System.out.print("sonar.projectName=");
		System.out.println(file.get("snoar.projectName"));
		System.out.print("null=");
		System.out.println(file.get("null"));

		if ("".equals(file.get("empty"))) {
			System.out.println("Yes");
		}
	}
}
