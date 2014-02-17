package com.neelo.glue.mustache;

import com.google.inject.Inject;
import com.google.inject.Singleton;

@Singleton
public class Helper {
	private final Link link;
	private final ExecuteFunction execute;

	@Inject
	public Helper(Link link, ExecuteFunction execute) {
		this.link = link;
		this.execute = execute;
	}

	public Link getLink() {
		return link;
	}
	
	public ExecuteFunction getExecute() {
		return execute;
	}
}
