package com.neelo.glue.phase;

import java.lang.reflect.Method;

import org.apache.commons.beanutils.ConvertUtils;

import com.neelo.glue.GlueException;
import com.neelo.glue.Lifecycle;
import com.neelo.glue.resolve.request.RequestResolution;

class InvokeApplicationPhase extends BasePhase {
	public Phase execute(Lifecycle lifecycle) throws GlueException {
		try {
			RequestResolution resolution = lifecycle.getRequestResolution();
			Method method = resolution.getRoute().getMethod();
			Class<?>[] parameterTypes = method.getParameterTypes();
			Object[] params = new Object[method.getParameterTypes().length];
			for (int i = 0; i < parameterTypes.length; i++) {
				Class<?> parameterType = parameterTypes[i];
				String value = resolution.getParameters().get(i).getValue();
				Object convert = ConvertUtils.convert(value, parameterType);
				params[i] = convert;
			}
			Object result = method.invoke(lifecycle.getBean(), params);
			resolution.setTypedParameters(params);
			lifecycle.setResult(result);

			return getPhases().getSend();
		} catch (Exception e) {
			throw new GlueException(e);
		}
	}
}