package helper;

import org.json.simple.JSONArray;

public class JSONHelper {
	
	public static String[] getStringArray(JSONArray jsonArray) {
		String[] stringArray = null;
		if (jsonArray != null) {
			int size = jsonArray.size();
			stringArray = new String[size];
			for (int i = 0; i < size; i++) {
				stringArray[i] = jsonArray.get(i).toString();
			}
		}

		return stringArray;
	}
}
