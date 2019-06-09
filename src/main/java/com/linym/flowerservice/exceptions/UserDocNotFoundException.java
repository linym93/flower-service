package com.linym.flowerservice.exceptions;

public class UserDocNotFoundException extends Exception{

	/**
	 * 
	 */
	private static final long serialVersionUID = 2407773783923874793L;
	
	public UserDocNotFoundException() {
		super();
	}
	
	public UserDocNotFoundException(final String message) {
		super(message);
	}
	
}
