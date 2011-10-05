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
package com.neelo.glue.resolve;

import java.lang.reflect.Method;

import com.neelo.glue.HttpMethod;
import com.neelo.glue.ResourceBuilder;

public class Route {
	protected ResourceBuilder resource;
	protected Method method;
	protected HttpMethod httpMethod;
	protected String path;
	protected String layout;
	protected String name;
	protected String content;

	public Route() {
	}

	public Class<?> getResourceClass() {
		return resource.getResourceClass();
	}

	public String getResourcePath() {
		return resource.getResourcePath();
	}

	public String getLayout() {
		return layout;
	}

	public HttpMethod getHttpMethod() {
		return httpMethod;
	}

	public String getPath() {
		return path;
	}

	public Method getMethod() {
		return method;
	}

	public String getContent() {
		return content;
	}

	public String getFullPath() {
		return resource.getResourcePath() + path;
	}

	public String getName() {
		return name;
	}

	public boolean isHttpGet() {
		return HttpMethod.GET.equals(getHttpMethod());
	}

	public boolean isHttpPost() {
		return HttpMethod.POST.equals(getHttpMethod());
	}

	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		sb.append(getClass().getSuperclass().getSimpleName()).append("[");
		sb.append("resource: ").append(resource.getResourceClass().getSimpleName()).append(", ");
		sb.append("path: ").append(resource.getResourcePath()).append(", ");
		sb.append("pattern: ").append(path).append(", ");
		sb.append("full path: ").append(getFullPath()).append(", ");
		sb.append("method: ").append(method.getName()).append(", ");
		sb.append("layout: ").append(getLayout()).append(", ");
		sb.append("http method: ").append(httpMethod);
		sb.append("]");

		return sb.toString();
	}
}
