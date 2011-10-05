package com.neelo.glue.phase;

import com.neelo.glue.GlueException;
import com.neelo.glue.Lifecycle;

public interface Phase {
	public Phase execute(Lifecycle lifecycle) throws GlueException;
}
