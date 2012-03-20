package com.neelo.glue.mustache;

import java.io.StringWriter;

import javax.inject.Inject;
import javax.inject.Provider;
import javax.inject.Singleton;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

import com.google.common.base.Function;
import com.sampullara.mustache.Mustache;
import com.sampullara.mustache.MustacheBuilder;
import com.sampullara.mustache.Scope;

@Singleton
public class ExecuteFunction implements Function<String, StringWriter> {
	private final Provider<MustacheBuilder> mustacheBuilderProvider;
	private final Provider<Scope> scopeProvider;

	@Inject
	public ExecuteFunction(Provider<MustacheBuilder> mustacheBuilderProvider, Provider<Scope> scopeProvider) {
		this.mustacheBuilderProvider = mustacheBuilderProvider;
		this.scopeProvider = scopeProvider;
	}

	public StringWriter apply(String input) {
		Scope temp = new Scope();
		temp.put("execute", this);

		try {
			JSONObject json = (JSONObject) new JSONParser().parse(input);
			Scope scope = scopeProvider.get();
			for (Object key : json.keySet()) {
				if (!"file".equals(key)) {
					temp.put(key, scope.get(json.get(key)));
				}
			}
			Mustache sub = mustacheBuilderProvider.get().parseFile((String) json.get("file"));
			StringWriter sw = new StringWriter();
			sub.execute(sw, temp);
			return sw;
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}
}
