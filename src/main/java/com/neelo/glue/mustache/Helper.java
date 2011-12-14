package com.neelo.glue.mustache;

import com.google.inject.Inject;
import com.google.inject.Singleton;

@Singleton
public class Helper {
	private final Link link;

	@Inject
	public Helper(Link link) {
		this.link = link;
	}

	public Link getLink() {
		return link;
	}
}
