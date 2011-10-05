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
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.code.regex.NamedMatcher;
import com.google.code.regex.NamedPattern;
import com.google.inject.Binder;
import com.google.inject.Module;
import com.neelo.glue.annotation.Get;
import com.neelo.glue.annotation.Post;
import com.neelo.glue.annotation.Resource;
import com.neelo.glue.resolve.Parameter;
import com.neelo.glue.resolve.ResolutionException;
import com.neelo.glue.resolve.Route;
import com.neelo.glue.resolve.request.RequestResolution;
import com.neelo.glue.resolve.request.RequestResolver;
import com.neelo.glue.resolve.response.DefaultResponseResolver;
import com.neelo.glue.resolve.response.ResponseResolver;

public abstract class ApplicationModule implements Module, RequestResolver {
	private static final List<Parameter> EMPTY_PARAMETERS = Collections.unmodifiableList(new ArrayList<Parameter>());
	private static final String FORWARD_SLASH = "/";

	private final Logger log = LoggerFactory.getLogger(ApplicationModule.class);

	protected List<Route> routes;
	protected Map<String, Route> ids;

	public final void configure(Binder binder) {
		this.routes = new ArrayList<Route>();
		this.ids = new HashMap<String, Route>();

		binder.bind(RequestResolver.class).toInstance(this);
		binder.bind(ResponseResolver.class).to(DefaultResponseResolver.class);
		routes();

		this.routes = Collections.unmodifiableList(routes);
		for (Route route : routes) {
			if (route.getName() != null) {
				ids.put(route.getName(), route);
			}
		}
		this.ids = Collections.unmodifiableMap(ids);

		Module[] additional = install();
		for (Module module : additional) {
			binder.install(module);
		}
	}

	protected abstract void routes();

	protected Module[] install() {
		return new Module[] {};
	}

	public void register(Class<?> resourceClass) {
		log.info("Registering: " + resourceClass.getName());

		Resource resource = null;

		if (resourceClass.isAnnotationPresent(Resource.class)) {
			resource = resourceClass.getAnnotation(Resource.class);
		} else {
			log.warn("Class not registerd. Missing " + Resource.class.getSimpleName() + " annotation");
			return;
		}

		ResourceBuilder builder = new ResourceBuilder(resourceClass, resource.path(), resource.layout());

		Method[] methods = resourceClass.getDeclaredMethods();
		for (Method method : methods) {
			if (method.isAnnotationPresent(Get.class)) {
				Get get = method.getAnnotation(Get.class);

				RouteBuilder route = builder.get(method, get.path(), get.content());

				route.setLayout(StringUtils.isNotBlank(get.layout()) ? get.layout() : resource.layout());
				if (StringUtils.isNotBlank(get.name()))
					route.setName(get.name());

				routes.add(route);
			} else if (method.isAnnotationPresent(Post.class)) {
				Post post = method.getAnnotation(Post.class);

				RouteBuilder route = builder.post(method, post.path(), post.content());

				route.setLayout(StringUtils.isNotBlank(post.layout()) ? post.layout() : resource.layout());
				if (StringUtils.isNotBlank(post.name()))
					route.setName(post.name());

				routes.add(route);
			}
		}
	}

	public RequestResolution resolve(Lifecycle lifecycle) throws ResolutionException {
		HttpServletRequest request = lifecycle.getRequest();
		HttpMethod method = HttpMethod.valueOf(request.getMethod());

		// TODO: These two lines depend on the servlet mapping (/app/*, /)
		// String path =
		// request.getRequestURI().substring(request.getContextPath().length());
		String path = request.getPathInfo();
		path = StringUtils.removeEnd(path, FORWARD_SLASH);

		for (Route route : routes) {
			if (!method.equals(route.getHttpMethod()))
				continue;

			if (StringUtils.isBlank(path) && StringUtils.isBlank(route.getFullPath()))
				return new RequestResolution(route, EMPTY_PARAMETERS);

			NamedPattern np = NamedPattern.compile(route.getFullPath());
			NamedMatcher nm = np.matcher(path);

			List<String> names = np.groupNames();

			if (nm.matches()) {
				if (names.size() > 0) {
					List<Parameter> params = new ArrayList<Parameter>();
					for (String name : names) {
						params.add(new Parameter(name, nm.group(name)));
					}
					return new RequestResolution(route, params);
				}
				return new RequestResolution(route, EMPTY_PARAMETERS);
			}
		}

		throw new ResolutionException(404, "Resource not found @ " + path);
	}

	public Route lookup(String id) {
		return ids.get(id);
	}
}
