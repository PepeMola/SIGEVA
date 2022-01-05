package com.vacuna.vacuna.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;
@ResponseStatus(value=HttpStatus.CONFLICT, reason="La contraseña no coincide con su confirmación")
/***
 * 
 * @author crist
 *
 */
public class PasswordNoCoincideException extends Exception{

	private static final long serialVersionUID = -8268713239614498891L;

	public PasswordNoCoincideException() {
		//Metodo vacio porque lo hace en el @ResponseStatus
	}
}
