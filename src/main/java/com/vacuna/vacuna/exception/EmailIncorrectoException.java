package com.vacuna.vacuna.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;
@ResponseStatus(value=HttpStatus.CONFLICT, reason="Email incorrecto")
/***
 * 
 * @author crist
 *
 */
public class EmailIncorrectoException extends Exception{

	private static final long serialVersionUID = 8319572958302591099L;

	public EmailIncorrectoException() {
		//Metodo vacio porque lo hace en el @ResponseStatus
	}
}
