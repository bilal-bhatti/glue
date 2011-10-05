package com.neelo.glue.util;

import java.util.Map;

import org.json.simple.JSONValue;

public class ObjectMapper {

	@SuppressWarnings("unchecked")
	public Map<String, Object> read(String string) {
		return (Map<String, Object>) JSONValue.parse(string);
	}
}
