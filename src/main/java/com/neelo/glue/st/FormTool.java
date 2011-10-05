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

import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import com.google.inject.Inject;
import com.google.inject.Provider;
import com.neelo.glue.util.ObjectMapper;

public class FormTool extends Tool {

	private final ObjectMapper mapper;
	private final Provider<HttpServletRequest> requestProvider;

	@Inject
	public FormTool(ObjectMapper mapper, Provider<HttpServletRequest> requestProvider) {
		this.mapper = mapper;
		this.requestProvider = requestProvider;
	}

	public String get(Object key) {
		try {
			// Map<String, Object> def = mapper.readValue(key.toString(), new
			// TypeReference<Map<String, Object>>() {
			// });
			Map<String, Object> def = mapper.read(key.toString());
			return skip(def).toString();
		} catch (Exception e) {
			e.printStackTrace();
		}

		return "";
	}

	private StringBuilder skip(Map<String, Object> def) {
		StringBuilder sb = new StringBuilder();
		sb.append("<form ");
		for (Map.Entry<String, Object> entry : def.entrySet()) {
			sb.append(entry.getKey()).append("=\"").append(entry.getValue()).append("\" ");
		}
		sb.append(">");
		HttpServletRequest request = requestProvider.get();
		String uri = request.getRequestURI();
		if (request.getParameter("_input_page") != null)
			uri = request.getParameter("_input_page");
		sb.append("<input type=\"hidden\" id=\"_input_page\" name=\"_input_page\" value=\"");
		sb.append(uri).append("\"/>");
		return sb;
	}
}
