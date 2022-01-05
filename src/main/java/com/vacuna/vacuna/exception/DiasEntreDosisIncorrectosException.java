package com.vacuna.vacuna.exception;

import org.springframework.http.HttpStatus;
/***
 * 
 * @author crist
 *
 */

public class DiasEntreDosisIncorrectosException extends VacunaException{

	private static final long serialVersionUID = -5441711562607467455L;

	public DiasEntreDosisIncorrectosException() {
		super(HttpStatus.CONFLICT, "Debe haber 21 días mínimo entre ambas dosis.");
	}
}
