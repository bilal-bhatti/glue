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

import java.util.Collection;
import java.util.Map;
import java.util.Set;

import org.apache.commons.lang.NotImplementedException;

public abstract class Tool implements Map<String, String> {
	public abstract String get(Object key);

	public void clear() {
		throw new NotImplementedException();
	}

	public boolean containsKey(Object key) {
		return true;
	}

	public boolean containsValue(Object value) {
		return true;
	}

	public Set<Map.Entry<String, String>> entrySet() {
		throw new NotImplementedException();
	}

	public boolean isEmpty() {
		throw new NotImplementedException();
	}

	public Set<String> keySet() {
		throw new NotImplementedException();
	}

	public String put(String key, String value) {
		throw new NotImplementedException();
	}

	public void putAll(Map<? extends String, ? extends String> m) {
		throw new NotImplementedException();
	}

	public String remove(Object key) {
		throw new NotImplementedException();
	}

	public int size() {
		throw new NotImplementedException();
	}

	public Collection<String> values() {
		throw new NotImplementedException();
	}
}
