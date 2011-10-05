/*******************************************************************************
 * Copyright 2010 Bilal Bhatti, Neelo Consulting Inc.
 * 
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * 
 *   http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 ******************************************************************************/
package com.neelo.glue.servlet;

import javax.servlet.ServletContextEvent;

import org.apache.bval.guice.ValidationModule;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.inject.Guice;
import com.google.inject.Injector;
import com.google.inject.Module;
import com.google.inject.servlet.GuiceServletContextListener;
import com.google.inject.servlet.ServletModule;
import com.neelo.glue.ApplicationModule;
import com.neelo.glue.form.BeanUtilsFormBinderModule;
import com.neelo.glue.st.StringTemplateViewModule;

public abstract class GlueContextListener extends GuiceServletContextListener {
	private final Logger log = LoggerFactory.getLogger(GlueContextListener.class);

	private Injector injector;

	@Override
	protected Injector getInjector() {
		Module[] m = new Module[] { getServletModule(), getApplicationModule(), getViewModule(), getValidationModule(),
				getFormBinderModule() };
		injector = Guice.createInjector(m);
		return injector;
	}

	@Override
	public void contextInitialized(ServletContextEvent event) {
		log.info("Initializing application");
		super.contextInitialized(event);
	}

	@Override
	public void contextDestroyed(ServletContextEvent event) {
		log.info("Destroying application");
		super.contextDestroyed(event);
	}

	protected abstract ApplicationModule getApplicationModule();

	protected Module getViewModule() {
		return new StringTemplateViewModule();
	}

	protected Module getFormBinderModule() {
		return new BeanUtilsFormBinderModule();
	}

	/**
	 * Not using Guice Servlet module for serving servlets due to a bug in
	 * RequestDispatcher.forward(). Using it for the additional scopes and
	 * bindings
	 * 
	 * @return Module
	 */
	protected Module getServletModule() {
		return new ServletModule();
	}

	protected Module getValidationModule() {
		return new ValidationModule();
	}
}
