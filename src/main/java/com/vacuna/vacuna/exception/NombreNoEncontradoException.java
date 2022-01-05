package com.vacuna.vacuna.exception;

import org.springframework.http.HttpStatus;
/***
 * 
 * @author crist
 *
 */

public class NombreNoEncontradoException extends VacunaException{

	private static final long serialVersionUID = -785197052799565191L;

	public NombreNoEncontradoException() {
		super(HttpStatus.CONFLICT, "Debes indicar tu nombre de usuario");
	}
}
