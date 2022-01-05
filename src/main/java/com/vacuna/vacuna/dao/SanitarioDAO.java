package com.vacuna.vacuna.dao;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import com.vacuna.vacuna.model.Sanitario;

@Repository
public interface SanitarioDAO extends MongoRepository<Sanitario, String> {

	/***
	 * Buscar a un sanitario por su email
	 * @param email
	 * @return sanitario
	 */
	Sanitario findByEmail(String email);
	
	/***
	 * Buscar un sanitario por su dni
	 * @param dni
	 * @return sanitario
	 */
	Sanitario findByDni(String dni);

}
