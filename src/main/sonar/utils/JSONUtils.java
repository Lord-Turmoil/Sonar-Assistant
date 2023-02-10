/**
 * JSON utils.
 */

package main.sonar.utils;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.Map;

/**
 * Utility tool for JSON object.
 */
public class JSONUtils {
	private JSONUtils() {}

	/**
	 * Convert map to json string.
	 * @param map keys and values
	 * @return json string
	 */
	public static String mapToJSONString(Map<String, Object> map) {
		try {
			JSONObject jsonObject = new JSONObject(map);
			return jsonObject.toString();
		} catch (JSONException e) {
			return "";
		}
	}

	/**
	 * Convert map to json object.
	 * @param map keys and values
	 * @return json object, empty object if any error occurs
	 */
	public static JSONObject mapToJSON(Map<String, Object> map) {
		try {
			return new JSONObject(map);
		} catch (JSONException e) {
			return new JSONObject();
		}
	}

	/**
	 * A utility function to get the key of a json entry.
	 * @param entry json entry, e.g. "name: Tony"
	 * @return the key, e.g. "name"
	 */
	public static String entryKey(String entry) {
		int index = entry.indexOf(':');
		return entry.substring(0, index);
	}

	/**
	 * A utility function to get the value of a json entry
	 * @param entry json entry
	 * @return the value
	 */
	public static String entryValue(String entry) {
		int index = entry.indexOf(':');		// first occurrence
		index++;
		while (entry.charAt(index) == ' ') {
			index++;
		}
		return entry.substring(index);
	}
}
