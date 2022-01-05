package com.vacuna.vacuna.exception;

import org.springframework.http.HttpStatus;
/***
 * 
 * @author crist
 *
 */

public class UsuarioExistenteException extends VacunaException{

	private static final long serialVersionUID = 2466689095368788404L;

	public UsuarioExistenteException() {
		super(HttpStatus.CONFLICT, "El usuario ya existe");
	}
}
