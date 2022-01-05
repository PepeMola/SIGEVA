package com.vacuna.vacuna.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;
@ResponseStatus(value=HttpStatus.CONFLICT, reason="El centro no existe")
/***
 * 
 * @author crist
 *
 */
public class CentroNoExisteException extends Exception{

	private static final long serialVersionUID = -7319675128204494426L;

	public CentroNoExisteException() {
		//Metodo vacio porque lo hace en el @ResponseStatus
	}
}
