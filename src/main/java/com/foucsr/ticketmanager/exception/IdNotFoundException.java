package com.foucsr.ticketmanager.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.BAD_REQUEST)
public class IdNotFoundException extends RuntimeException
{
	public IdNotFoundException(String message) 
	{
		super(message);
	}
	
	public IdNotFoundException(String message, Throwable cause)
	{
		super(message,cause);
	}
	
}
