package main.sonar.utils;

import java.io.*;
import java.nio.file.Path;
import java.util.Map;
import java.util.Properties;

public class PropertyFile {
	private final Properties properties = new Properties();

	/**
	 * Load properties from a file and add them to current properties.
	 * @param path property file path
	 * @throws IOException
	 */
	public void load(Path path) throws IOException {
		File file = new File(path.toString());
		FileInputStream input = null;
		try {
			input = new FileInputStream(file);
		} catch (FileNotFoundException e) {
			throw e;
		}

		try {
			properties.load(input);
		} finally {
			input.close();
		}
	}

	/**
	 * Save all properties and write them to a file.
	 * @param path output file path
	 * @throws IOException
	 */
	public void save(Path path) throws IOException {
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

	/**
	 * add a property, do nothing if value is null
	 * @param key key
	 * @param value value
	 */
	public void put(String key, String value) {
		if (value != null) {
			properties.put(key, value);
		}
	}

	/**
	 * add a property, add default value if value is null,
	 * do nothing if both values are null
	 * @param key key
	 * @param value value
	 * @param defaultValue default value
	 */
	public void put(String key, String value, String defaultValue) {
		if (value != null) {
			properties.put(key, value);
		} else if (defaultValue != null) {
			properties.put(key, defaultValue);
		}
	}

	public void remove(String key) {
		properties.remove(key);
	}

	public void clear() {
		properties.clear();
	}
}
