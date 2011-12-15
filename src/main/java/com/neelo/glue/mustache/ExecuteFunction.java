package com.neelo.glue.mustache;

import java.io.StringWriter;
import java.util.HashMap;
import java.util.Map;

import javax.inject.Inject;
import javax.inject.Provider;
import javax.inject.Singleton;

import org.codehaus.jackson.map.ObjectMapper;
import org.codehaus.jackson.type.TypeReference;

import com.google.common.base.Function;
import com.sampullara.mustache.Mustache;
import com.sampullara.mustache.MustacheBuilder;
import com.sampullara.mustache.Scope;

@Singleton
public class ExecuteFunction implements Function<String, StringWriter> {
	private final Provider<MustacheBuilder> mustacheBuilderProvider;
	private final Provider<Scope> scopeProvider;
	private final Helper helper;

	@Inject
	public ExecuteFunction(Provider<MustacheBuilder> mustacheBuilderProvider, Provider<Scope> scopeProvider,
			Helper helper) {
		this.mustacheBuilderProvider = mustacheBuilderProvider;
		this.scopeProvider = scopeProvider;
		this.helper = helper;
	}

	public StringWriter apply(String input) {
		ObjectMapper om = new ObjectMapper();
		Scope temp = new CustomScope();
		temp.put("execute", this);
		temp.put("link", helper.getLink());

		try {
			TypeReference<HashMap<String, Object>> typeRef = new TypeReference<HashMap<String, Object>>() {
			};
			HashMap<String, Object> o = om.readValue(input, typeRef);
			Scope scope = scopeProvider.get();
			for (Map.Entry<String, Object> entry : o.entrySet()) {
				if (!"file".equals(entry.getKey())) {
					temp.put(entry.getKey(), scope.get(entry.getValue()));
				}
			}
			Mustache sub = mustacheBuilderProvider.get().parseFile((String) o.get("file"));
			StringWriter sw = new StringWriter();
			sub.execute(sw, temp);
			return sw;
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}
}
