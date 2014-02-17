package com.neelo.glue.mustache;

import java.net.URLEncoder;

import org.apache.commons.lang.StringUtils;

public class StringRenderer implements Renderer {

	public String stringify(Object o, String format) {
		try {
			String formatted = o.toString();

			String f = format.trim();

			if (f.equals("uc"))
				formatted = formatted.toUpperCase();
			else if (f.equals("lc"))
				formatted = formatted.toLowerCase();
			else if (f.equals("cap"))
				formatted = StringUtils.capitalize(formatted);
			else if (f.startsWith("abbr")) {
				String[] parts = f.split("[:]", 2);
				if (parts.length == 2) {
					formatted = StringUtils.abbreviate(formatted, Integer.parseInt(parts[1].trim()));
				}
			} else if (f.equals("encode"))
				formatted = URLEncoder.encode(o.toString(), "UTF-8");
			else if (f.equals("csv")) {
				formatted = formatted.replace("\"", "\"\"");
				formatted = "\"" + formatted + "\"";
			}

			return formatted;
		} catch (Exception e) {
			return o.toString();
		}
	}
}
