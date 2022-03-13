package com.example.utils.rest;

public class SpringRestException extends RuntimeException {

	private static final long serialVersionUID = -2051463933404604301L;

	public SpringRestException(String errorMessage) {
		super(errorMessage);
	}
	
}