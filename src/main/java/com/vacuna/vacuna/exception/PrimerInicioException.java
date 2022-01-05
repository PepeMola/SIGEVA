package com.vacuna.vacuna.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;
@ResponseStatus(value=HttpStatus.CONFLICT, reason="No se ha podido iniciar sesion")
/***
 * 
 * @author crist
 *
 */

public class PrimerInicioException extends Exception{

	private static final long serialVersionUID = -6761168478704676696L;

	public PrimerInicioException() {
		//Metodo vacio porque lo hace en el @ResponseStatus
	}
}
