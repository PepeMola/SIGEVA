package com.vacuna.vacuna.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;
@ResponseStatus(value=HttpStatus.CONFLICT, reason="Fallo al obtener la lista de centros")
/***
 * 
 * @author crist
 *
 */
public class CentrosNoEncontradosException extends Exception{

	private static final long serialVersionUID = 1420679248958971029L;

	public CentrosNoEncontradosException() {
		//Metodo vacio porque lo hace en el @ResponseStatus
	}
}
