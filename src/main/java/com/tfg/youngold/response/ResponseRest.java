package com.tfg.youngold.response;

import java.util.HashMap;
import java.util.Map;

public class ResponseRest {

	private Map<String, String> metadata = new HashMap<>();

	public Map<String, String> getMetadata() {
		return metadata;
	}

	public void setMetadata(String type, String code, String date) {
		metadata.put("type", type);
		metadata.put("code", code);
		metadata.put("date", date);
	}
}
