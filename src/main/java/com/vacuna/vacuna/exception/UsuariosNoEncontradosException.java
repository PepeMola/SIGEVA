package com.vacuna.vacuna.exception;

import org.springframework.http.HttpStatus;
/***
 * 
 * @author crist
 *
 */

public class UsuariosNoEncontradosException extends VacunaException{

	private static final long serialVersionUID = -3508230068519989639L;

	public UsuariosNoEncontradosException() {
		super(HttpStatus.CONFLICT, "Fallo al obtener la lista de usuarios");
	}
}
