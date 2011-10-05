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

import java.io.IOException;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.inject.Inject;
import com.google.inject.Injector;
import com.neelo.glue.Core;
import com.neelo.glue.GlueException;
import com.neelo.glue.Lifecycle;
import com.neelo.glue.resolve.ResolutionException;

public class GlueServlet extends HttpServlet {
	private Core core;

	@Inject
	public void setCore(Core core) {
		this.core = core;
	}

	public Core getCore() {
		return core;
	}

	@Override
	public void init(ServletConfig config) throws ServletException {
		super.init(config);
		Injector injector = (Injector) config.getServletContext().getAttribute(Injector.class.getName());
		injector.injectMembers(this);
	}

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		process(req, resp);
	}

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		process(req, resp);
	}

	private void process(HttpServletRequest req, HttpServletResponse resp) throws IOException, ServletException {
		try {
			Lifecycle lifecycle = new Lifecycle(req, resp);
			req.setAttribute(Lifecycle.class.getName(), lifecycle);

			core.process(lifecycle);

		} catch (ResolutionException re) {
			resp.sendError(re.getStatus(), re.getMessage());
		} catch (GlueException e) {
			throw new ServletException(e);
		} catch (Throwable t) {
			throw new ServletException(t);
		}
	}
}
