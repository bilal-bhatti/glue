package com.neelo.glue.mustache;

import java.io.File;

import javax.inject.Inject;
import javax.inject.Singleton;
import javax.servlet.ServletContext;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.inject.AbstractModule;
import com.google.inject.Provides;
import com.sampullara.mustache.MustacheBuilder;

public class MustacheViewModule extends AbstractModule {
	private final Logger log = LoggerFactory.getLogger(MustacheViewModule.class);

	@Override
	protected void configure() {
	}

	@Inject
	@Provides
	@Singleton
	public MustacheBuilder get(ServletContext context) {
		String root = context.getRealPath("/");
		log.info("Initializing Mustache with root " + root);
		MustacheBuilder builder = new MustacheBuilder(new File(root + "/WEB-INF/mustache"));
		return builder;
	}
}
