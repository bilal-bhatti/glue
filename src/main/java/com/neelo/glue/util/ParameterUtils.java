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

import java.lang.reflect.InvocationTargetException;
import java.util.Map;

import org.apache.commons.beanutils.BeanUtils;

public class ParameterUtils {
	public static void populate(Object target, final Map<String, String[]> params) throws IllegalAccessException,
			InvocationTargetException {

		// Map<String, Object[]> copy = new HashMap<String, Object[]>(params);
		//
		// Iterator<Map.Entry<String, Object[]>> i = copy.entrySet().iterator();

		// while (i.hasNext()) {
		// Map.Entry<String, Object[]> entry = i.next();
		// PropertyDescriptor pd = PropertyUtils.getPropertyDescriptor(target,
		// entry.getKey());
		//
		// if (pd == null)
		// continue;
		//
		// Method writer = pd.getWriteMethod();
		// if (writer.isAnnotationPresent(Convert.class)) {
		// Convert ann = writer.getAnnotation(Convert.class);
		// Class<? extends Converter> converterClass = ann.converter();
		// Converter converter = GuiceFactory.getInstance(converterClass);
		// Object[] values = new Object[entry.getValue().length];
		//
		// for (int j = 0; j < entry.getValue().length; j++) {
		// values[j] = converter.convert((String)entry.getValue()[j],
		// pd.getPropertyType(), ann.format());
		// }
		//
		// if (pd.getPropertyType().isArray()) {
		// PropertyUtils.setProperty(target, entry.getKey(), values);
		// } else {
		// PropertyUtils.setProperty(target, entry.getKey(), values[0]);
		// }
		// i.remove();
		// }
		// }

		BeanUtils.populate(target, params);
	}
}
