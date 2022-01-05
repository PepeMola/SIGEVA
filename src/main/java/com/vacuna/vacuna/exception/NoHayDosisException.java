package com.vacuna.vacuna.exception;

import org.springframework.http.HttpStatus;
/***
 * 
 * @author crist
 *
 */

public class NoHayDosisException extends VacunaException{

	private static final long serialVersionUID = -2380293493174537509L;

	public NoHayDosisException() {
		super(HttpStatus.CONFLICT, "No hay dosis disponibles.");
	}
}
