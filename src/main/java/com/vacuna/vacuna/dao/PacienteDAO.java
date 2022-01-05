package com.vacuna.vacuna.dao;

import java.util.List;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import com.vacuna.vacuna.model.Paciente;


@Repository
/***
 * 
 * @author crist
 *
 */
public interface PacienteDAO extends MongoRepository<Paciente, String> {
	/***
	 * Buscar un paciente por su dni
	 * @param dni
	 * @return paciente
	 */
	Paciente findByDni(String dni);
	
	/***
	 * Borrar un paciente buscandolo por su dni
	 * @param dni
	 * @return paciente
	 */
	Paciente deleteByDni(String dni);
	
	/***
	 * Buscar todos los pacientes
	 */
	public List<Paciente> findAll();
	
	/***
	 * Buscar un paciente por si email
	 * @param email
	 * @return paciente
	 */
	Paciente findByEmail(String email);
	
	/***
	 * Buscar email y password de un paciente
	 * @param email
	 * @param sha512Hex
	 * @return paciente
	 */
	Paciente findByEmailAndPassword(String email, String sha512Hex);
}
