package com.vacuna.vacuna.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;
@ResponseStatus(value=HttpStatus.CONFLICT, reason="Algo ha ido mal. Password no modificada")
/***
 * 
 * @author crist
 *
 */

public class CambioPasswordException extends Exception{

	private static final long serialVersionUID = -3944299722578489097L;
	public CambioPasswordException() {
		//Metodo vacio porque lo hace en el @ResponseStatus
	}

}
