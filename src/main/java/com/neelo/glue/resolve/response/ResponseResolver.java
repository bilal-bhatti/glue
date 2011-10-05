package com.neelo.glue.resolve.response;

import com.neelo.glue.Lifecycle;

public interface ResponseResolver {
	public ResponseResolution resolve(Lifecycle lifecycle);
}
