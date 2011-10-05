package com.neelo.glue.resolve.response;

import com.neelo.glue.GlueException;
import com.neelo.glue.Lifecycle;

public interface ResponseResolution {
	public final String redirect = "redirect";
	public final String forward = "forward";
	public final String template = "template";

	public void process(Lifecycle lifecycle) throws GlueException;
}
