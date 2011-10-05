package com.neelo.glue.phase;

import com.google.inject.Inject;
import com.neelo.glue.GlueException;
import com.neelo.glue.Lifecycle;

abstract class BasePhase implements Phase {
	private PhaseFactory phases;

	@Inject
	public void setPhases(PhaseFactory phases) {
		this.phases = phases;
	}

	public PhaseFactory getPhases() {
		return phases;
	}

	public abstract Phase execute(Lifecycle lifecycle) throws GlueException;
}