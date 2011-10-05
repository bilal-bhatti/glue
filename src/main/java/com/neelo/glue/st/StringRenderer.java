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

import java.net.URLEncoder;

import org.antlr.stringtemplate.AttributeRenderer;
import org.apache.commons.lang.StringUtils;

public class StringRenderer implements AttributeRenderer {
	public String toString(Object o) {
		return o.toString();
	}

	public String toString(Object o, String format) {
		try {
			String[] formats = format.split("[,]");
			String formatted = o.toString();

			for (String f : formats) {
				f = f.trim();

				if (f.equals("uppercase"))
					formatted = formatted.toUpperCase();
				else if (f.equals("lowercase"))
					formatted = formatted.toLowerCase();
				else if (f.equals("capitalize"))
					formatted = StringUtils.capitalize(formatted);
				else if (f.startsWith("abbreviate")) {
					String[] parts = f.split("[:]");
					if (parts.length == 2) {
						formatted = StringUtils.abbreviate(formatted, Integer.parseInt(parts[1].trim()));
					}
				} else if (f.equals("encode"))
					formatted = URLEncoder.encode(o.toString(), "UTF-8");
				else if (f.equals("csv")) {
					formatted = formatted.replace("\"", "\"\"");
					formatted = "\"" + formatted + "\"";
				}
			}

			return formatted;
		} catch (Exception e) {
			return toString(o);
		}
	}
}
