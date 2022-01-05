package com.vacuna.vacuna.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;
@ResponseStatus(value=HttpStatus.CONFLICT, reason="El usuario no existe")
/***
 * 
 * @author crist
 *
 */
public class UsuarioNoExisteException extends Exception{

	private static final long serialVersionUID = 8699579039645084204L;

	public UsuarioNoExisteException() {
		//Metodo vacio porque lo hace en el @ResponseStatus
	}
}
