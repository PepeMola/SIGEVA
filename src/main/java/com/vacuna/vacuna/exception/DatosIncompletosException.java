package com.vacuna.vacuna.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value=HttpStatus.CONFLICT, reason="Rellena todos los campos correctamente")
/***
 * 
 * @author crist
 *
 */
public class DatosIncompletosException extends Exception {
	private static final long serialVersionUID = 8586768844718668989L;

	public DatosIncompletosException() {
		//Metodo vacio porque lo hace en el @ResponseStatus
	}
	
}
