package com.neelo.glue.resolve.response;

import java.io.IOException;
import java.util.Map;

import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.inject.Inject;
import com.google.inject.Singleton;
import com.neelo.glue.GlueException;
import com.neelo.glue.Lifecycle;
import com.neelo.glue.util.LinkBuilder;

@Singleton
public class RedirectResponseResolution implements ResponseResolution {
	private final Logger log = LoggerFactory.getLogger(RedirectResponseResolution.class);
	private final LinkBuilder linkBuilder;

	@Inject
	public RedirectResponseResolution(LinkBuilder linkBuilder) {
		this.linkBuilder = linkBuilder;
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
		StringBuilder url = linkBuilder.link((Map<String, Object>) params.get(redirect));
		String finalurl = url.toString();
		log.debug("Redirecting to url: " + finalurl);
		response.sendRedirect(finalurl.toString());
	}
}
