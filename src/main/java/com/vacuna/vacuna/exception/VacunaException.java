package com.vacuna.vacuna.exception;

import org.springframework.http.HttpStatus;
/***
 * 
 * @author crist
 *
 */

public class VacunaException extends Exception{

private static final long serialVersionUID = -7418484215113578717L;
	
	private final HttpStatus status;
	private final String message;
	/***
	 * 
	 * @param status
	 * @param message
	 */
	
	public VacunaException(HttpStatus status, String message) {
		this.status = status;
		this.message = message;
	}

	@Override
	public String getMessage() {
		return this.message;
	}
	/***
	 * 
	 * @return
	 */

	public HttpStatus getStatus() {
		return status;
	}
}
