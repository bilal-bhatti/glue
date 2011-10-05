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

import org.apache.commons.beanutils.PropertyUtils;
import org.apache.commons.lang.ObjectUtils;

import com.google.inject.Inject;
import com.google.inject.Provider;
import com.neelo.glue.Lifecycle;
import com.neelo.glue.util.Literals;
import com.neelo.glue.util.ObjectMapper;

public class SelectTool extends Tool {
	private final Provider<HttpServletRequest> requestProvider;
	private final ObjectMapper mapper;

	@Inject
	public SelectTool(Provider<HttpServletRequest> requestProvider, ObjectMapper mapper) {
		this.requestProvider = requestProvider;
		this.mapper = mapper;
	}

	@SuppressWarnings("unchecked")
	public String get(Object key) {
		String tag = key.toString();

		try {
			Map<String, Object> def = mapper.read(tag);
			Map<String, Object> options = (Map<String, Object>) def.get("options");

			if (options.get("list") != null) {
				return list(def, options, requestProvider.get()).toString();
			} else if (options.get("map") != null) {
				return map(def, options, requestProvider.get()).toString();
			}

			return skip(def).toString();
		} catch (Exception e) {
			e.printStackTrace();
		}

		return "select: " + tag;
	}

	private void start(Map<String, Object> def, StringBuilder sb) {
		sb.append("<select ");
		for (Map.Entry<String, Object> entry : def.entrySet()) {
			if (!entry.getKey().equalsIgnoreCase("options")) {
				sb.append(entry.getKey()).append("=\"").append(entry.getValue()).append("\" ");
			}
		}
		sb.append(">\n");
	}

	private void end(Map<String, Object> def, StringBuilder sb) {
		sb.append("</select>");
	}

	private StringBuilder skip(Map<String, Object> def) {
		StringBuilder sb = new StringBuilder();
		start(def, sb);
		end(def, sb);
		return sb;
	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	private StringBuilder map(Map<String, Object> def, Map<String, Object> options, HttpServletRequest request)
			throws Exception {
		String listProperty = options.get("map").toString();

		Lifecycle wrapper = (Lifecycle) request.getAttribute(Lifecycle.class.getName());
		if (wrapper == null)
			return skip(def);

		Map<String, Object> context = Literals.map("this", wrapper.getBean());

		Object map = PropertyUtils.getProperty(context, listProperty);

		if (!(map instanceof Map))
			return skip(def);

		Iterable<Entry<String, Object>> it = ((Map) map).entrySet();

		StringBuilder sb = new StringBuilder();
		start(def, sb);

		Object prompt = options.get("prompt");

		if (prompt != null)
			sb.append("<option value=\"\">").append(prompt).append("</option>\n");

		Object defaults = options.get("default");

		String name = def.get("name").toString();

		// TODO: selected object may be a list for multi-select
		Object selected = PropertyUtils.getProperty(wrapper.getBean(), name);

		for (Entry<String, Object> option : it) {
			sb.append("<option value=\"").append(option.getKey()).append("\"");

			if (selected != null) {
				if (ObjectUtils.equals(option.getKey(), selected.toString())) {
					sb.append(" selected=\"selected\"");
				}
			} else if (defaults != null) {
				if (ObjectUtils.equals(option.getKey(), defaults.toString())) {
					sb.append(" selected=\"selected\"");
				}
			}

			sb.append(">");
			sb.append(option.getValue()).append("</option>\n");
		}

		end(def, sb);
		return sb;

	}

	@SuppressWarnings({ "rawtypes" })
	private StringBuilder list(Map<String, Object> def, Map<String, Object> options, HttpServletRequest request)
			throws Exception {
		String listProperty = options.get("list").toString();

		Lifecycle wrapper = (Lifecycle) request.getAttribute(Lifecycle.class.getName());
		if (wrapper == null)
			return skip(def);

		Map<String, Object> context = Literals.map("this", wrapper.getBean());

		Object list = PropertyUtils.getProperty(context, listProperty);

		if (list == null)
			return skip(def);

		if (!(list instanceof Iterable))
			return skip(def);

		Iterable it = (Iterable) list;
		String valueProperty = options.get("value").toString();
		String textProperty = options.get("text").toString();

		StringBuilder sb = new StringBuilder();
		start(def, sb);

		Object prompt = options.get("prompt");

		if (prompt != null)
			sb.append("<option value=\"\">").append(prompt).append("</option>\n");

		Object defaults = options.get("default");

		String name = def.get("name").toString();

		// TODO: selected object may be a list for multi-select
		Object selected = PropertyUtils.getProperty(wrapper.getBean(), name);

		for (Object option : it) {
			Object value = PropertyUtils.getProperty(option, valueProperty);
			Object text = PropertyUtils.getProperty(option, textProperty);
			sb.append("<option value=\"").append(value).append("\"");

			boolean mark = false;

			if (selected != null) {
				// check for multi selected array
				if (selected.getClass().isArray()) {
					Object[] selectedArray = (Object[]) selected;
					for (Object object : selectedArray)
						if (ObjectUtils.equals(object, option))
							mark = true;
					// check for multi selected list
				} else if (selected instanceof List) {
					List<?> selectedList = (List<?>) selected;
					if (selectedList.contains(option))
						mark = true;
					// check if option and selected objects are equal
					// or the value property of option and string representation
					// of
					// selected object are equal (user.id (long) == "2")
				} else if (ObjectUtils.equals(option, selected)
						|| ObjectUtils.equals(value.toString(), selected.toString()))
					mark = true;

			} else if (defaults != null) {
				if (ObjectUtils.equals(value.toString(), defaults.toString()))
					mark = true;
			}

			if (mark)
				sb.append(" selected=\"selected\"");

			sb.append(">");
			sb.append(text).append("</option>\n");
		}

		end(def, sb);
		return sb;
	}
}
