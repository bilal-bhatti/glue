package com.neelo.glue.resolve.response;

import java.util.Map;

import com.google.inject.Inject;
import com.google.inject.Singleton;
import com.neelo.glue.Lifecycle;
import com.neelo.glue.annotation.Mustache;
import com.neelo.glue.mustache.RenderMustacheResponseResolution;
import com.neelo.glue.st.RenderSTResponseResolution;

@Singleton
public class DefaultResponseResolver implements ResponseResolver {
	private RenderSTResponseResolution renderStringTemplateResponse;
	private RenderMustacheResponseResolution renderMustacheResponse;
	private RedirectResponseResolution redirectResponse;
	private ForwardResponseResolution forwardResponse;

	@Inject
	public void setRenderMustacheResponse(RenderMustacheResponseResolution renderMustacheResponse) {
		this.renderMustacheResponse = renderMustacheResponse;
	}

	public RenderMustacheResponseResolution getRenderMustacheResponse() {
		return renderMustacheResponse;
	}

	@Inject
	public void setRenderStringTemplateResponse(RenderSTResponseResolution renderStringTemplateResponse) {
		this.renderStringTemplateResponse = renderStringTemplateResponse;
	}

	public RenderSTResponseResolution getRenderStringTemplateResponse() {
		return renderStringTemplateResponse;
	}

	@Inject
	public void setRedirectResponse(RedirectResponseResolution redirectResponse) {
		this.redirectResponse = redirectResponse;
	}

	public RedirectResponseResolution getRedirectResponse() {
		return redirectResponse;
	}

	@Inject
	public void setForwardResponse(ForwardResponseResolution forwardResponse) {
		this.forwardResponse = forwardResponse;
	}

	public ForwardResponseResolution getForwardResponse() {
		return forwardResponse;
	}

	@SuppressWarnings("unchecked")
	public ResponseResolution resolve(Lifecycle lifecycle) {
		Object object = lifecycle.getResult();

		if (object == null || !(object instanceof Map)) {
			if (lifecycle.getBean().getClass().isAnnotationPresent(Mustache.class))
				return getRenderMustacheResponse();
			return getRenderStringTemplateResponse();
		}

		Map<Object, Object> map = (Map<Object, Object>) object;

		if (map.get(ResponseResolution.redirect) != null) {
			return getRedirectResponse();
		} else if (map.get(ResponseResolution.forward) != null) {
			return getForwardResponse();
		} else {
			if (lifecycle.getBean().getClass().isAnnotationPresent(Mustache.class))
				return getRenderMustacheResponse();
			return getRenderStringTemplateResponse();
		}
	}
}
