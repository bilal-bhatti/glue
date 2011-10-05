package com.neelo.glue.resolve.response;

import java.io.PrintWriter;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.antlr.stringtemplate.NoIndentWriter;
import org.antlr.stringtemplate.StringTemplate;
import org.antlr.stringtemplate.StringTemplateGroup;
import org.apache.commons.lang.StringUtils;

import com.google.inject.Inject;
import com.google.inject.Provider;
import com.google.inject.Singleton;
import com.neelo.glue.GlueException;
import com.neelo.glue.Lifecycle;
import com.neelo.glue.resolve.Route;
import com.neelo.glue.st.ViewHelper;

@Singleton
public class RenderSTResponseResolution implements ResponseResolution {
	private final Provider<StringTemplateGroup> templateProvider;
	private final ViewHelper helper;

	@Inject
	public RenderSTResponseResolution(Provider<StringTemplateGroup> templateProvider, ViewHelper helper) {
		this.templateProvider = templateProvider;
		this.helper = helper;
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

			StringTemplateGroup stg = templateProvider.get();

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

			StringTemplate layout = stg.getInstanceOf(route.getLayout());
			StringTemplate page = stg.getInstanceOf(base + "/" + action);

			if (result instanceof Map) {
				layout.setAttributes((Map) result);
			} else {
				layout.setAttribute("_return", result);
			}

			layout.setAttribute("_helper", helper);
			layout.setAttribute("_contextPath", req.getContextPath());
			layout.setAttribute("_this", lifecycle.getBean());
			layout.setAttribute("_page", page);

			PrintWriter writer = resp.getWriter();
			NoIndentWriter stw = new NoIndentWriter(writer);
			layout.write(stw);
			writer.flush();
			writer.close();
		} catch (Exception e) {
			throw new GlueException(e);
		}
	}
}
