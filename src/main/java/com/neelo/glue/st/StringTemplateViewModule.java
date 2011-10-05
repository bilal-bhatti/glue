package com.neelo.glue.st;

import java.util.Map;

import javax.inject.Inject;
import javax.inject.Singleton;
import javax.servlet.ServletContext;

import org.antlr.stringtemplate.AttributeRenderer;
import org.antlr.stringtemplate.StringTemplateGroup;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.inject.AbstractModule;
import com.google.inject.Provides;
import com.google.inject.name.Names;

public class StringTemplateViewModule extends AbstractModule {
	private final Logger log = LoggerFactory.getLogger(StringTemplateViewModule.class);

	@Override
	protected void configure() {
		bind(String.class).annotatedWith(Names.named("stringtemplates")).toInstance("");
	}

	@Inject
	@Provides
	@Singleton
	public StringTemplateGroup get(ServletContext context) {
		log.info("Initializing StringTemplate");

		StringTemplateGroup group;

		String root = context.getRealPath("/");
		if (StringUtils.isBlank(root))
			group = new StringTemplateGroup("root");
		else
			group = new StringTemplateGroup("root", root + "/WEB-INF/templates");

		group.setRefreshInterval(0);

		group.registerRenderer(java.util.Date.class, new DateRenderer());
		group.registerRenderer(java.sql.Date.class, new DateRenderer());
		group.registerRenderer(java.sql.Timestamp.class, new DateRenderer());
		group.registerRenderer(java.lang.String.class, new StringRenderer());

		Map<Class<?>, AttributeRenderer> renderers = renderers();
		if (renderers != null)
			for (Map.Entry<Class<?>, AttributeRenderer> entry : renderers.entrySet()) {
				group.registerRenderer(entry.getKey(), entry.getValue());
			}

		return group;
	}

	public Map<Class<?>, AttributeRenderer> renderers() {
		return null;
	}
}
