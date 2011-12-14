package com.neelo.glue.mustache;

import java.util.HashMap;
import java.util.Map;

import com.sampullara.mustache.Scope;

@SuppressWarnings({ "rawtypes", "unchecked" })
public class CustomScope extends Scope {
	private static Map<Class, Renderer> renderers = new HashMap<Class, Renderer>();

	public static void register(Class<?> klass, Renderer renderer) {
		renderers.put(klass, renderer);
	}

	public CustomScope(Map<String, Object> data) {
		super(data);
	}

	@Override
	public Object get(Object o, Scope scope) {
		String name = o.toString();
		String[] options = name.split(";", 2);
		Object value = super.get(options[0].trim(), scope);

		if (value == null)
			return value;

		if (options.length > 1)
			return format(value, options[1].replace("\"", "").trim());
		
		return value;
	}

	private Object format(Object value, String format) {
		Renderer r = renderers.get(value.getClass());
		if (r == null)
			return value;
		return r.stringify(value, format);
	}
}
