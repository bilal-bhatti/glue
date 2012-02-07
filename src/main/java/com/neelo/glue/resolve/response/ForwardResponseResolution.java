package com.neelo.glue.resolve.response;

import java.io.IOException;
import java.util.Map;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.inject.Inject;
import com.google.inject.Singleton;
import com.neelo.glue.GlueException;
import com.neelo.glue.Lifecycle;
import com.neelo.glue.util.LinkBuilder;

@Singleton
public class ForwardResponseResolution implements ResponseResolution {
	private final LinkBuilder linkBuilder;

	@Inject
	public ForwardResponseResolution(LinkBuilder linkBuilder) {
		this.linkBuilder = linkBuilder;
	}

	@SuppressWarnings("unchecked")
	public void process(Lifecycle lifecycle) throws GlueException {
		try {
			forward(lifecycle.getRequest(), lifecycle.getResponse(), (Map<Object, Object>) lifecycle.getResult());
		} catch (Exception e) {
			throw new GlueException(e);
		}
	}

	@SuppressWarnings("unchecked")
	private void forward(HttpServletRequest request, HttpServletResponse response, Map<Object, Object> params)
			throws ServletException, IOException {
		StringBuilder url = linkBuilder.link((Map<String, Object>) params.get(forward));
		RequestDispatcher dispatcher = request.getRequestDispatcher(url.toString());
		dispatcher.forward(request, response);
	}
}
