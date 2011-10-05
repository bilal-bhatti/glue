package com.neelo.glue.phase;

import com.google.inject.Inject;
import com.neelo.glue.GlueException;
import com.neelo.glue.Lifecycle;
import com.neelo.glue.resolve.response.ResponseResolution;
import com.neelo.glue.resolve.response.ResponseResolver;

class SendResponsePhase extends BasePhase {
	private final ResponseResolver resolver;

	@Inject
	public SendResponsePhase(ResponseResolver resolver) {
		this.resolver = resolver;
	}

	@Override
	public Phase execute(Lifecycle lifecycle) throws GlueException {
		try {
			ResponseResolution resolution = resolver.resolve(lifecycle);
			resolution.process(lifecycle);

			return null;
		} catch (Throwable e) {
			throw new GlueException(e);
		}
	}
}