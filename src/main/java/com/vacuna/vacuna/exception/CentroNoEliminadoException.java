package com.vacuna.vacuna.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;
@ResponseStatus(value=HttpStatus.CONFLICT, reason="El centro no se puede eliminar, existen usuarios asignados al centro")
/***
 * 
 * @author crist
 *
 */
public class CentroNoEliminadoException extends Exception{

	private static final long serialVersionUID = -9113618212136099710L;

	public CentroNoEliminadoException() {
		//Metodo vacio porque lo hace en el @ResponseStatus
	}
}
