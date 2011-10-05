package com.neelo.glue.resolve.response;

import java.io.IOException;
import java.util.Map;

import javax.servlet.http.HttpServletResponse;

import com.google.inject.Inject;
import com.google.inject.Singleton;
import com.neelo.glue.GlueException;
import com.neelo.glue.Lifecycle;
import com.neelo.glue.st.ViewHelper;

@Singleton
public class RedirectResponseResolution implements ResponseResolution {
	private final ViewHelper helper;

	@Inject
	public RedirectResponseResolution(ViewHelper helper) {
		this.helper = helper;
	}

	@SuppressWarnings("unchecked")
	public void process(Lifecycle lifecycle) throws GlueException {
		try {
			redirect(lifecycle.getResponse(), (Map<Object, Object>) lifecycle.getResult());
		} catch (Exception e) {
			throw new GlueException(e);
		}
	}

	@SuppressWarnings("unchecked")
	private void redirect(HttpServletResponse response, Map<Object, Object> params) throws IOException {
		StringBuilder url = helper.getLink().link((Map<String, Object>) params.get(redirect));
		response.sendRedirect(url.toString());
	}
}
