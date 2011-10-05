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

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.code.regex.NamedPattern;
import com.google.inject.Inject;
import com.google.inject.Provider;
import com.neelo.glue.resolve.Route;
import com.neelo.glue.resolve.request.RequestResolver;
import com.neelo.glue.util.ObjectMapper;

public class LinkTool extends Tool {
	private final Logger log = LoggerFactory.getLogger(LinkTool.class);

	private final Provider<HttpServletRequest> requestProvider;
	private final RequestResolver resolver;
	private final ObjectMapper mapper;

	@Inject
	public LinkTool(Provider<HttpServletRequest> requestProvider, RequestResolver resolver, ObjectMapper mapper) {
		this.requestProvider = requestProvider;
		this.resolver = resolver;
		this.mapper = mapper;
	}

	public String get(Object key) {
		try {
			return link(mapper.read(key.toString())).toString();
		} catch (Exception e) {
			log.error("Error encountered while looking up route", e);
		}

		return "";
	}

	public StringBuilder link(Map<String, Object> def) {
		StringBuilder url = new StringBuilder();
		try {
			if (def.get("url") != null)
				return url.append(def.get("url"));
			else if (def.get("path") != null)
				return url.append(def.get("path"));
			else if (def.get("name") != null)
				return byName(def, url, def.get("name"));

			Object params = def.get("params");
			if (params == null)
				return url;

			if (params instanceof Map)
				return encode(url, params);

		} catch (Exception e) {
			log.error("Error encountered while looking up route: {}", def, e);
		}
		return url;
	}

	@SuppressWarnings("rawtypes")
	private StringBuilder byName(Map<String, Object> def, StringBuilder url, Object name) {
		HttpServletRequest httpServletRequest = requestProvider.get();
		url.append(httpServletRequest.getContextPath());

		Route route = resolver.lookup(name.toString());
		if (route == null)
			return url;

		NamedPattern np = NamedPattern.compile(route.getPath());

		String path = np.standardPattern().replaceAll("\\(\\?:.*?\\)", "").replaceAll("\\(.*?\\)", "\\%s");

		url.append(httpServletRequest.getServletPath()).append(route.getResourcePath());

		Object args = def.get("args");
		if (args == null)
			url.append(path);
		else if (args instanceof List)
			url.append(String.format(path, ((List) args).toArray()));

		return url;
	}

	@SuppressWarnings("unchecked")
	private StringBuilder encode(StringBuilder url, Object params) {
		Map<Object, Object> param = (Map<Object, Object>) params;
		int count = 0;

		for (Map.Entry<Object, Object> entry : param.entrySet()) {
			if (count == 0)
				url.append("?");
			else
				url.append("&");

			url.append(entry.getKey()).append("=").append(entry.getValue());

			count++;
		}
		return url;
	}
}
