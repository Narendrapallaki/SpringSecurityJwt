package com.springsecurity.customException;

public class UserIdNotFound extends RuntimeException{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 7406476627455950048L;
	/**
	 * 
	 */
	
	/**
	 * 
	 */
	public UserIdNotFound(String message)
	{
		super(message);
		
	}

}
