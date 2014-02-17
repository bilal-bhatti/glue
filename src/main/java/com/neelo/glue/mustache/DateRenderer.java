package com.neelo.glue.mustache;

import java.text.SimpleDateFormat;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class DateRenderer implements Renderer {
	private final Logger log = LoggerFactory.getLogger(DateRenderer.class);

	public String stringify(Object o, String format) {
		try {
			SimpleDateFormat sdf = new SimpleDateFormat(format);
			return sdf.format(o);
		} catch (Exception e) {
			log.error("Unable to format provided date {} using expression {}", new Object[] { o.toString(), format }, e);
		}
		return o.toString();
	}
}
