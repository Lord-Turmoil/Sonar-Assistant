package test.sonar.utils;

import main.sonar.utils.InvalidPropertyException;
import main.sonar.utils.PropertyFile;

import java.io.IOException;
import java.nio.file.Paths;

public class PropertyFileTest {
	public static void main(String[] args) {
		PropertyFile file = new PropertyFile();

		System.out.println("----- Load");
		try {
			file.Load(Paths.get("res/sonar-project.properties"));
		} catch (InvalidPropertyException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}

		System.out.println("--- Modification");
		file.put("custom", "balbalbla");
		file.remove("sources");

		System.out.println("----- Save");
		try {
			file.Save(Paths.get("res/sonar.properties"));
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
