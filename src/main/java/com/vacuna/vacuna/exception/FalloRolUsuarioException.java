package com.vacuna.vacuna.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;
@ResponseStatus(value=HttpStatus.CONFLICT, reason="No se ha podido encontrar el rol del usuario")
/***
 * 
 * @author crist
 *
 */
public class FalloRolUsuarioException extends Exception{
	private static final long serialVersionUID = 3022922104369446824L;

	public FalloRolUsuarioException() {
		//Metodo vacio porque lo hace en el @ResponseStatus
	}
}
