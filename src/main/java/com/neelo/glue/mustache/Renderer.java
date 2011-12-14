package com.neelo.glue.mustache;

public interface Renderer<E> {
	public String stringify(E o, String format);
}