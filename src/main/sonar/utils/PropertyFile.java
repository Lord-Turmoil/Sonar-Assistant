package main.sonar.utils;

import java.io.*;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Hashtable;
import java.util.Map;
import java.util.Properties;

public class PropertyFile {
	private final Properties properties = new Properties();

	/**
	 * Load properties from a file and add them to current properties.
	 * @param path property file path
	 * @throws InvalidPropertyException
	 * @throws IOException
	 */
	public void Load(Path path) throws InvalidPropertyException, IOException {
		File file = new File(path.toString());
		FileInputStream input = null;
		try {
			input = new FileInputStream(file);
		} catch (FileNotFoundException e) {
			throw new InvalidPropertyException("Property file not found");
		}

		try {
			properties.load(input);
		} catch (IOException e) {
			throw new InvalidPropertyException("Failed to load property file");
		} finally {
			input.close();
		}
	}

	/**
	 * Save all properties and write them to a file.
	 * @param path output file path
	 * @throws IOException
	 */
	public void Save(Path path) throws IOException {
		try (BufferedWriter output = new BufferedWriter(new FileWriter(path.toString(), false))) {
			for (Map.Entry<Object, Object> property : properties.entrySet()) {
				output.write((String) property.getKey());
				output.write('=');
				output.write((String) property.getValue());
				output.newLine();
			}
		}
	}

	public String get(String key) {
		return (String)properties.get(key);
	}

	public void put(String key, String value) {
		properties.put(key, value);
	}

	public void remove(String key) {
		properties.remove(key);
	}

	public void clear() {
		properties.clear();
	}
}
