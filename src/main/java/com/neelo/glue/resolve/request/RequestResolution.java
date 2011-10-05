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
package com.neelo.glue.resolve.request;

import java.util.List;

import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;

import com.neelo.glue.resolve.Parameter;
import com.neelo.glue.resolve.Route;

public class RequestResolution {
	private final Route route;
	private final List<Parameter> parameters;
	private Object[] typedParameters;

	public RequestResolution(Route route, List<Parameter> parameters) {
		this.route = route;
		this.parameters = parameters;
	}

	public Route getRoute() {
		return route;
	}

	public void addParameter(Parameter param) {
		parameters.add(param);
	}

	public List<Parameter> getParameters() {
		return parameters;
	}

	public void setTypedParameters(Object[] typedParameters) {
		this.typedParameters = typedParameters;
	}

	public Object[] getTypedParameters() {
		return typedParameters;
	}

	@Override
	public String toString() {
		return ToStringBuilder.reflectionToString(this, ToStringStyle.SHORT_PREFIX_STYLE);
	}
}
