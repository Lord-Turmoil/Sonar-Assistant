package main.sonar.utils;

import java.io.File;
import java.io.IOException;
import java.nio.file.*;

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

	public static boolean mkdirs(String dir) {
		Path path = Paths.get(dir);
		File file = new File(dir.toString());
		if (file.exists()) {
			return true;
		}
		try {
			return file.mkdirs();
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println(e);
			return false;
		}
	}
}
