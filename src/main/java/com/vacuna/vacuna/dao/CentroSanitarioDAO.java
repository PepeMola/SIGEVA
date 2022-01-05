package com.vacuna.vacuna.dao;

import org.springframework.data.mongodb.repository.MongoRepository;
import com.vacuna.vacuna.model.CentroSanitario;
/***
 * 
 * @author crist
 *
 */
public interface CentroSanitarioDAO extends MongoRepository<CentroSanitario, String> {

	/***
	 * Buscar un centro sanitario por el nombre
	 * @param nombre
	 * @return centro sanitario
	 */
	CentroSanitario findByNombre(String nombre);
	
	/***
	 * Borrar centro sanitario buscandolo por el nombre
	 * @param nombre
	 * @return centro sanitario
	 */
	public CentroSanitario deleteByNombre(String nombre);

	
}
