package com.vacuna.vacuna.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;
@ResponseStatus(value=HttpStatus.CONFLICT, reason="La contraseña debe tener al menos 8 caracteres mayusculas, minusculas, numeros y caracteres especiales")
/***
 * 
 * @author crist
 *
 */
public class PasswordIncorrectaException extends Exception{

	private static final long serialVersionUID = -7235793782607894386L;

	public PasswordIncorrectaException() {
		//Metodo vacio porque lo hace en el @ResponseStatus
	}
}
