package main.sonar.utils;

import java.nio.file.Files;
import java.nio.file.InvalidPathException;
import java.nio.file.LinkOption;
import java.nio.file.Paths;

public class FileUtil {
	private FileUtil() {}

	public static boolean isFile(String dir) {
		if (!isValid(dir)) {
			return false;
		}

		return Files.isRegularFile(Paths.get(dir), LinkOption.NOFOLLOW_LINKS);
	}

	public static boolean isValid(String dir) {
		try {
			Paths.get(dir);
		} catch (InvalidPathException e) {
			return false;
		}

		return true;
	}

	public static boolean exists(String dir) {
		if (!isValid(dir)) {
			return false;
		}

		return Files.exists(Paths.get(dir), LinkOption.NOFOLLOW_LINKS);
	}
}
