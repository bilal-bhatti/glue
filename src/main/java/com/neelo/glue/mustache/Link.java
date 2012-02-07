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
package com.neelo.glue.mustache;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.common.base.Function;
import com.google.inject.Inject;
import com.neelo.glue.util.LinkBuilder;
import com.neelo.glue.util.ObjectMapper;

public class Link implements Function<String, String> {
	private final Logger log = LoggerFactory.getLogger(Link.class);

	private final ObjectMapper mapper;
	private final LinkBuilder linkBuilder;

	@Inject
	public Link(LinkBuilder linkBuilder, ObjectMapper mapper) {
		this.linkBuilder = linkBuilder;
		this.mapper = mapper;
	}

	public String apply(String input) {
		try {
			return linkBuilder.link(mapper.read(input)).toString();
		} catch (Exception e) {
			log.error("Error encountered while looking up route", e);
		}

		return "";
	}
}
