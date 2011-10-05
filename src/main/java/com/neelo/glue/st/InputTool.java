/*******************************************************************************
 * Copyright 2010 Bilal Bhatti, Neelo Consulting Inc.
 * 
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * 
 *   http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 ******************************************************************************/
package com.neelo.glue.st;

import java.util.Arrays;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import com.google.inject.Inject;
import com.google.inject.Provider;
import com.neelo.glue.util.ObjectMapper;

public class InputTool extends Tool {
	private final Provider<HttpServletRequest> request;
	private final ObjectMapper mapper;

	@Inject
	public InputTool(Provider<HttpServletRequest> request, ObjectMapper mapper) {
		this.request = request;
		this.mapper = mapper;
	}

	public String get(Object key) {
		String tag = key.toString();
		try {
			Map<String, Object> def = mapper.read(tag);

			if (def.get("name") == null || def.get("type") == null) {
				return skip(def).toString();
			}

			String[] params = request.get().getParameterValues(def.get("name").toString());
			if (params == null || params.length == 0)
				return skip(def).toString();

			List<String> values = Arrays.asList(params);

			String type = def.get("type").toString();

			if (type.equalsIgnoreCase("checkbox")) {
				return checkbox(def, values).toString();
			} else if (type.equalsIgnoreCase("radio")) {
				return radio(def, values).toString();
			} else {
				if (params.length == 2)
					return text(def, params[1]).toString();
				else
					return text(def, values).toString();
			}

		} catch (Exception e) {
			e.printStackTrace();
			return skip(tag).toString();
		}
	}

	private StringBuilder text(Map<String, Object> def, String value) {
		def.put("value", value);
		return skip(def);
	}

	private StringBuilder text(Map<String, Object> def, List<String> values) {
		StringBuilder sb = new StringBuilder();
		for (String value : values) {
			def.put("value", value);
			sb.append(skip(def));
		}
		return sb;
	}

	private StringBuilder checkbox(Map<String, Object> def, List<String> values) {
		Object value = "on";
		if (def.get("value") != null) {
			value = def.get("value");
		}

		if (values.contains(value)) {
			def.put("checked", "checked");
			return skip(def);
		}

		return skip(def);
	}

	private StringBuilder radio(Map<String, Object> def, List<String> values) {
		return checkbox(def, values);
	}

	private StringBuilder skip(String tag) {
		StringBuilder sb = new StringBuilder();
		sb.append("<input ");
		sb.append(tag);
		sb.append("/>");
		return sb;
	}

	private StringBuilder skip(Map<String, Object> def) {
		StringBuilder sb = new StringBuilder();
		sb.append("<input ");
		for (Map.Entry<String, Object> entry : def.entrySet()) {
			if (!entry.getKey().equalsIgnoreCase("options")) {
				sb.append(entry.getKey()).append("=\"").append(entry.getValue()).append("\" ");
			}
		}
		sb.append("/>");
		return sb;
	}
}
