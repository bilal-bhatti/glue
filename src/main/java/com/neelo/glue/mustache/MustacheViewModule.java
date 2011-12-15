package com.neelo.glue.mustache;

import java.io.File;

import javax.inject.Inject;
import javax.inject.Singleton;
import javax.servlet.ServletContext;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.inject.AbstractModule;
import com.google.inject.Provides;
import com.google.inject.servlet.RequestScoped;
import com.sampullara.mustache.MustacheBuilder;
import com.sampullara.mustache.Scope;

public class MustacheViewModule extends AbstractModule {
	private final Logger log = LoggerFactory.getLogger(MustacheViewModule.class);

	@Override
	protected void configure() {
		bind(ExecuteFunction.class);
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

	@Inject
	@Provides
	@RequestScoped
	public Scope get(Helper helper, ExecuteFunction execute) {
		Scope scope = new CustomScope();
		scope.put("link", helper.getLink());
		scope.put("execute", execute);

		return scope;
	}
}
