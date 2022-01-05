package com.vacuna.vacuna.exception;

import org.springframework.http.HttpStatus;
/***
 * 
 * @author crist
 *
 */

public class ControlHorasVacunacionException extends VacunaException{

	private static final long serialVersionUID = -1563873958934203003L;

	public ControlHorasVacunacionException() {
		super(HttpStatus.CONFLICT, "La hora de inicio tiene que ser antes que la hora fin");
	}
}
