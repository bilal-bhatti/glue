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
package com.neelo.glue.util;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class Literals {
	public static <T> List<T> list(T... items) {
		return new ArrayList<T>(Arrays.asList(items));
	}

	public static <T> Set<T> set(T... items) {
		return new HashSet<T>(Arrays.asList(items));
	}

	public static <S, T> MapBuilder<S, T> map(S key, T value) {
		return new MapBuilder<S, T>().map(key, value);
	}

	public static class MapBuilder<S, T> extends HashMap<S, T> {
		public MapBuilder<S, T> map(S key, T value) {
			put(key, value);
			return this;
		}
	}

	public static List<Object> olist(Object... items) {
		return list(items);
	}

	public static Set<Object> oset(Object... items) {
		return set(items);
	}

	public static OMapBuilder omap(Object key, Object value) {
		return new OMapBuilder().omap(key, value);
	}

	public static class OMapBuilder extends HashMap<Object, Object> {
		public OMapBuilder omap(Object key, Object value) {
			put(key, value);
			return this;
		}
	}
}
