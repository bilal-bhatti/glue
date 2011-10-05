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

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;

import com.neelo.glue.resolve.request.RequestResolution;
import com.neelo.glue.resolve.response.ResponseResolution;

public class Lifecycle {
	private final HttpServletRequest request;
	private final HttpServletResponse response;
	private RequestResolution requestResolution;
	private ResponseResolution responseResolution;
	private Object bean;
	private Object result;

	public Lifecycle(HttpServletRequest request, HttpServletResponse response) {
		this.request = request;
		this.response = response;
	}

	public HttpServletRequest getRequest() {
		return request;
	}

	public HttpServletResponse getResponse() {
		return response;
	}

	public void setRequestResolution(RequestResolution requestResolution) {
		this.requestResolution = requestResolution;
	}
	
	public RequestResolution getRequestResolution() {
		return requestResolution;
	}

	public void setResponseResolution(ResponseResolution responseResolution) {
		this.responseResolution = responseResolution;
	}

	public ResponseResolution getResponseResolution() {
		return responseResolution;
	}
	
	public void setBean(Object bean) {
		this.bean = bean;
	}

	public Object getBean() {
		return bean;
	}

	public void setResult(Object result) {
		this.result = result;
	}

	public Object getResult() {
		return result;
	}

//	@SuppressWarnings("rawtypes")
//	public boolean redirect() {
//		if (result instanceof Map) {
//			Map map = (Map) result;
//			return map.get(Dispatcher.REDIRECT) != null;
//		}
//		return false;
//	}
//
//	@SuppressWarnings("rawtypes")
//	public boolean forward() {
//		if (result instanceof Map) {
//			Map map = (Map) result;
//			return map.get(Dispatcher.FORWARD) != null;
//		}
//		return false;
//	}

//	public String getTemplate() {
//		Route route = responseResolution.getRoute();
//		String base = route.getKlass().getAnnotation(Resource.class).path();
//		String action = route.getMethod().getName();
//
//		if (result != null && result instanceof String) {
//			action = (String) result;
//		}
//
//		return base.substring(1) + "/" + action;
//	}

	@Override
	public String toString() {
		return ToStringBuilder.reflectionToString(this, ToStringStyle.SHORT_PREFIX_STYLE);
	}
}
