package com.neelo.glue.mustache;

import java.io.PrintWriter;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;

import com.google.inject.Inject;
import com.google.inject.Provider;
import com.google.inject.Singleton;
import com.neelo.glue.GlueException;
import com.neelo.glue.Lifecycle;
import com.neelo.glue.resolve.Route;
import com.neelo.glue.resolve.response.ResponseResolution;
import com.sampullara.mustache.Mustache;
import com.sampullara.mustache.MustacheBuilder;
import com.sampullara.mustache.Scope;
import com.sampullara.util.FutureWriter;

@Singleton
public class RenderMustacheResponseResolution implements ResponseResolution {
	private final Provider<MustacheBuilder> mustacheBuilderProvider;
	private final Provider<Scope> scopeProvider;

	@Inject
	public RenderMustacheResponseResolution(Provider<MustacheBuilder> mustacheBuilderProvider,
			Provider<Scope> scopeProvider) {
		this.mustacheBuilderProvider = mustacheBuilderProvider;
		this.scopeProvider = scopeProvider;
	}

	@SuppressWarnings({ "rawtypes", "unchecked" })
	public void process(Lifecycle lifecycle) throws GlueException {
		try {
			HttpServletRequest req = lifecycle.getRequest();
			HttpServletResponse resp = lifecycle.getResponse();
			resp.setContentType("text/html;charset=UTF-8");

			Object result = lifecycle.getResult();

			if (StringUtils.isNotBlank(lifecycle.getRequestResolution().getRoute().getContent()))
				resp.setContentType(lifecycle.getRequestResolution().getRoute().getContent());

			MustacheBuilder builder = mustacheBuilderProvider.get();

			Route route = lifecycle.getRequestResolution().getRoute();
			String base = route.getResourcePath();

			if (base.startsWith("/"))
				base = base.substring(1);

			String action = route.getMethod().getName();

			if (result instanceof String) {
				action = result.toString();
			} else if (result instanceof Map) {
				Map rmap = (Map) result;
				if (rmap.get("template") != null)
					action = (String) rmap.get("template");

				if (rmap.get("headers") != null && rmap.get("headers") instanceof Map) {
					Map<String, String> headers = (Map<String, String>) rmap.get("headers");
					for (String name : headers.keySet()) {
						resp.setHeader(name, headers.get(name));
					}
				}
			}

			Mustache page = builder.parseFile(base + "/" + action + ".must");

			Scope scope = scopeProvider.get();
			if (result instanceof Map) {
				scope.putAll((Map) result);
			} else {
				scope.put("return", result);
			}
			scope.put("contextPath", req.getContextPath());
			scope.put("this", lifecycle.getBean());

			PrintWriter sw = resp.getWriter();
			FutureWriter writer = new FutureWriter(sw);

			page.execute(writer, scope);
			writer.close();
		} catch (Exception e) {
			throw new GlueException(e);
		}
	}
}
