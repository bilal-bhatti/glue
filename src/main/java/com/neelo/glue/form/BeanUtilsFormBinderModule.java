package com.neelo.glue.form;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.beanutils.BeanUtilsBean;
import org.apache.commons.beanutils.ConvertUtilsBean;
import org.apache.commons.beanutils.Converter;
import org.apache.commons.beanutils.converters.BooleanConverter;
import org.apache.commons.beanutils.converters.ByteConverter;
import org.apache.commons.beanutils.converters.CharacterConverter;
import org.apache.commons.beanutils.converters.DoubleConverter;
import org.apache.commons.beanutils.converters.FloatConverter;
import org.apache.commons.beanutils.converters.IntegerConverter;
import org.apache.commons.beanutils.converters.LongConverter;
import org.apache.commons.beanutils.converters.ShortConverter;

import com.google.inject.AbstractModule;

public final class BeanUtilsFormBinderModule extends AbstractModule implements FormBinder {
	private BeanUtilsBean bub;

	public BeanUtilsFormBinderModule() {
		bub = new BeanUtilsBean();

		ConvertUtilsBean cub = bub.getConvertUtils();
		cub.register(new IntegerConverter(null), Integer.class);
		cub.register(new FloatConverter(null), Float.class);
		cub.register(new DoubleConverter(null), Double.class);
		cub.register(new ShortConverter(null), Short.class);
		cub.register(new ByteConverter(null), Byte.class);
		cub.register(new LongConverter(null), Long.class);
		cub.register(new BooleanConverter(null), Boolean.class);
		cub.register(new CharacterConverter(null), Character.class);
	}

	public void register(Converter converter, Class<?> clazz) {
		bub.getConvertUtils().register(converter, clazz);
	}

	public void bind(Object form, HttpServletRequest request) throws RuntimeException {
		try {
			bub.populate(form, request.getParameterMap());
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	@Override
	protected void configure() {
		bind(FormBinder.class).toInstance(this);
	}
}
