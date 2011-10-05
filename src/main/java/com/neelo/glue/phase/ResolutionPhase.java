package com.neelo.glue.phase;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.inject.Inject;
import com.neelo.glue.GlueException;
import com.neelo.glue.Lifecycle;
import com.neelo.glue.resolve.request.RequestResolver;
import com.neelo.glue.resolve.request.RequestResolution;

class ResolutionPhase extends BasePhase {
	private final Logger log = LoggerFactory.getLogger(ResolutionPhase.class);

	private final RequestResolver resolver;

	@Inject
	public ResolutionPhase(RequestResolver resolver) {
		this.resolver = resolver;
	}

	public Phase execute(Lifecycle lifecycle) throws GlueException {
		RequestResolution resolution = resolver.resolve(lifecycle);
		lifecycle.setRequestResolution(resolution);
		log.debug(resolution.getRoute().toString());

		return getPhases().getBind();
	}
}