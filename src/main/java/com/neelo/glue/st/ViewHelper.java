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
package com.neelo.glue.st;

import com.google.inject.Inject;
import com.google.inject.Singleton;

@Singleton
public class ViewHelper {
	private final LinkTool link;
	private final InputTool input;
	private final SelectTool select;
	private final FormTool form;
	private final HelloTool hello;

	@Inject
	public ViewHelper(LinkTool link, FormTool form, InputTool input, SelectTool select, HelloTool hello) {
		this.link = link;
		this.form = form;
		this.input = input;
		this.select = select;
		this.hello = hello;
	}

	public LinkTool getLink() {
		return link;
	}

	public InputTool getInput() {
		return input;
	}

	public SelectTool getSelect() {
		return select;
	}

	public FormTool getForm() {
		return form;
	}

	public HelloTool getHello() {
		return hello;
	}
}
