package com.neelo.glue.phase;

import com.google.inject.Inject;
import com.google.inject.Injector;
import com.neelo.glue.GlueException;
import com.neelo.glue.Lifecycle;
import com.neelo.glue.form.FormBinder;

class FormBindingPhase extends BasePhase {
	private final Injector injector;
	private final FormBinder binder;

	@Inject
	public FormBindingPhase(Injector injector, FormBinder binder) {
		this.injector = injector;
		this.binder = binder;
	}

	public Phase execute(Lifecycle lifecycle) throws GlueException {
		try {
			Object bean = injector.getInstance(lifecycle.getRequestResolution().getRoute().getResourceClass());

			lifecycle.setBean(bean);
			// ParameterUtils.populate(bean,
			// lifecycle.getRequest().getParameterMap());
			binder.bind(bean, lifecycle.getRequest());

			return getPhases().getInvoke();
		} catch (Exception e) {
			throw new GlueException(e);
		}
	}
}