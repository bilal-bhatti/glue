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
package com.neelo.glue;

import java.lang.reflect.Method;

public class ResourceBuilder {
	private final Class<?> resourceClass;
	private final String resourcePath;
	private final String resourceLayout;

	public ResourceBuilder(Class<?> resourceClass, String resourcePath, String resourceLayout) {
		this.resourceClass = resourceClass;
		this.resourcePath = resourcePath;
		this.resourceLayout = resourceLayout;
	}

	public Class<?> getResourceClass() {
		return resourceClass;
	}

	public String getResourcePath() {
		return resourcePath;
	}

	public String getResourceLayout() {
		return resourceLayout;
	}

	// public RouteBuilder route(String pattern, String name, Class<?>[] types,
	// HttpMethod httpMethod) {
	// try {
	// Method method = klass.getMethod(name, types);
	// return new RouteBuilder(pattern, method, httpMethod, klass, path,
	// layout);
	// } catch (Exception e) {
	// throw new RuntimeException("Unable to bind route.", e);
	// }
	// }

	public RouteBuilder get(Method method, String path, String content) {
		return new RouteBuilder(this, method, HttpMethod.GET, path, content);
	}

	public RouteBuilder post(Method method, String path, String content) {
		return new RouteBuilder(this, method, HttpMethod.POST, path, content);
	}
}
