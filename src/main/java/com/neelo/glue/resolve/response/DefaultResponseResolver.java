package com.neelo.glue.resolve.response;

import java.util.Map;

import com.google.inject.Inject;
import com.google.inject.Singleton;
import com.neelo.glue.Lifecycle;

@Singleton
public class DefaultResponseResolver implements ResponseResolver {
	private RenderSTResponseResolution renderTemplateResponse;
	private RedirectResponseResolution redirectResponse;
	private ForwardResponseResolution forwardResponse;

	@Inject
	public void setRenderTemplateResponse(RenderSTResponseResolution renderTemplateResponse) {
		this.renderTemplateResponse = renderTemplateResponse;
	}

	public RenderSTResponseResolution getRenderTemplateResponse() {
		return renderTemplateResponse;
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

		if (object == null || !(object instanceof Map))
			return getRenderTemplateResponse();

		Map<Object, Object> map = (Map<Object, Object>) object;

		if (map.get(ResponseResolution.redirect) != null) {
			return getRedirectResponse();
		} else if (map.get(ResponseResolution.forward) != null) {
			return getForwardResponse();
		} else {
			return getRenderTemplateResponse();
		}
	}
}
