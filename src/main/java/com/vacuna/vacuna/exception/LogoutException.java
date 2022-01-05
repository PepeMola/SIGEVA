package com.vacuna.vacuna.exception;

import org.springframework.http.HttpStatus;
/***
 * 
 * @author crist
 *
 */

public class LogoutException extends VacunaException{

	private static final long serialVersionUID = -335594039510641286L;

	public LogoutException() {
		super(HttpStatus.CONFLICT, "Algo ha ido mal. No se puede cerrar sesion");
	}
}
