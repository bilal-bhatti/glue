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

public class Dispatcher {
	// public static final String REDIRECT = "redirect";
	// public static final String FORWARD = "forward";
	// public static final String TEMPLATE = "template";
	// private final ViewHelper helper;

	// @Inject
	// public Dispatcher(ViewHelper helper) {
	// this.helper = helper;
	// }

	// public void redirect(HttpServletRequest request, HttpServletResponse
	// response, Map<Object, Object> params)
	// throws IOException {
	// String url = construct(params).toString();
	// if (url.length() > request.getContextPath().length())
	// url = url.substring(request.getContextPath().length());
	// response.sendRedirect(request.getContextPath() + url.toString());
	// }

	// public void redirect(HttpServletRequest request, HttpServletResponse
	// response, String location) throws IOException {
	// response.sendRedirect(request.getContextPath() + location);
	// }

	// public void redirect(HttpServletRequest request, HttpServletResponse
	// response) throws IOException {
	// response.sendRedirect(request.getContextPath());
	// }

	// public void redirect(HttpServletResponse response, String location)
	// throws IOException {
	// response.sendRedirect(location);
	// }

	// public void forward(HttpServletRequest request, HttpServletResponse
	// response, Map<Object, Object> params)
	// throws ServletException, IOException {
	// String url = construct(params).toString();
	// if (url.length() > request.getContextPath().length())
	// url = url.substring(request.getContextPath().length());
	// RequestDispatcher dispatcher = request.getRequestDispatcher(url);
	// dispatcher.forward(request, response);
	// }

	// private StringBuilder construct(Map<Object, Object> params) throws
	// IOException {
	// if (params.get(REDIRECT) != null)
	// return helper.getLink().link((Map<String, Object>) params.get(REDIRECT));
	// else if (params.get(FORWARD) != null)
	// return helper.getLink().link((Map<String, Object>) params.get(FORWARD));
	// else
	// return new StringBuilder();
	// }
}
