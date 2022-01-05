package com.vacuna.vacuna.exception;

import org.springframework.http.HttpStatus;
/***
 * 
 * @author crist
 *
 */

public class CitasNoEncontradasException extends VacunaException{

	private static final long serialVersionUID = -1327014029215866800L;

	public CitasNoEncontradasException() {
		super(HttpStatus.CONFLICT, "No se han encontrado las citas");
		
	}
}
