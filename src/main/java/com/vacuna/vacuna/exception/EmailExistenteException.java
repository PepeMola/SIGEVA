package com.vacuna.vacuna.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;
@ResponseStatus(value=HttpStatus.CONFLICT, reason="El Email ya existe")
/***
 * 
 * @author crist
 *
 */
public class EmailExistenteException extends Exception{

	private static final long serialVersionUID = 3881901131202919674L;

	public EmailExistenteException() {
		//Metodo vacio porque lo hace en el @ResponseStatus
	}
}
