package com.vacuna.vacuna.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;
@ResponseStatus(value=HttpStatus.CONFLICT, reason="Paciente con alguna dosis administrada o cita ya asignada")
/***
 * 
 * @author crist
 *
 */
public class ErrorDosisAdministradasException extends Exception{

	private static final long serialVersionUID = -1669044336424992591L;

	public ErrorDosisAdministradasException() {
		//Metodo vacio porque lo hace en el @ResponseStatus
	}
}
