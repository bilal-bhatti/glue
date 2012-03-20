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

	public MustacheViewModule() {
	}

	@Override
	protected void configure() {
		bind(ExecuteFunction.class);
	}

	@Inject
	@Provides
	@Singleton
	protected MustacheBuilder get(ServletContext context) {
		String root = context.getRealPath("/");
		log.info("Initializing Mustache with root " + root);
		MustacheBuilder builder = new MustacheBuilder(new File(root + "/WEB-INF/mustache"));
		return builder;
	}

	@Inject
	@Provides
	@RequestScoped
	protected Scope get(Helper helper) {
		Scope scope = new Scope();
		scope.put("link", helper.getLink());
		scope.put("execute", helper.getExecute());

		return populate(scope);
	}

	protected Scope populate(Scope scope) {
		return scope;
	}
}
