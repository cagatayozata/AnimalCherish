package com.team1.animalproject.helpers.util;

import com.google.gson.GsonBuilder;
import org.apache.commons.lang3.StringUtils;

import java.util.List;

public class JsonUtil {

	public static String getJsonString(Object o) {
		return new GsonBuilder().create().toJson(o);
	}

	public static String getJsonStringForChart(List list) {
		String jsonString = getJsonString(list);

		//Remove first and last character  -> [ ]
		if(StringUtils.isNotBlank(jsonString) && jsonString.length() > 1){
			return jsonString.substring(1, jsonString.length() - 1);
		}
		return jsonString;
	}
}
