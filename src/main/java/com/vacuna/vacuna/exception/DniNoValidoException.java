package com.vacuna.vacuna.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;
@ResponseStatus(value=HttpStatus.CONFLICT, reason="DNI no valido, intentelo de nuevo")
/***
 * 
 * @author crist
 *
 */
public class DniNoValidoException extends Exception{
	private static final long serialVersionUID = -3669195206707577711L;

	public DniNoValidoException() {
		//Metodo vacio porque lo hace en el @ResponseStatus
	}
}
