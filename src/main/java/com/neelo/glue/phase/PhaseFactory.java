package com.neelo.glue.phase;

import com.google.inject.Inject;
import com.google.inject.Singleton;

@Singleton
public class PhaseFactory {
	private Phase resolve;
	private Phase bind;
	private Phase invoke;
	private Phase send;

	public Phase getResolve() {
		return resolve;
	}

	@Inject
	public void setResolve(ResolutionPhase resolve) {
		this.resolve = resolve;
	}

	public Phase getBind() {
		return bind;
	}

	@Inject
	public void setBind(FormBindingPhase bind) {
		this.bind = bind;
	}

	public Phase getInvoke() {
		return invoke;
	}

	@Inject
	public void setInvoke(InvokeApplicationPhase invoke) {
		this.invoke = invoke;
	}

	public Phase getSend() {
		return send;
	}

	@Inject
	public void setSend(SendResponsePhase send) {
		this.send = send;
	}
}
