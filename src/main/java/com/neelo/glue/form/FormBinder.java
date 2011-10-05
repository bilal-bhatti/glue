package com.neelo.glue.form;

import javax.servlet.http.HttpServletRequest;

public interface FormBinder {
	public void bind(Object form, HttpServletRequest request);
}
