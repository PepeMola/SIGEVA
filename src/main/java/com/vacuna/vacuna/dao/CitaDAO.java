package com.vacuna.vacuna.dao;

import java.util.List;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import com.vacuna.vacuna.model.Cita;

@Repository
/***
 * 
 * @author crist
 *
 */
public interface CitaDAO extends MongoRepository<Cita, String>{

	/***
	 * Buscar paciente por si dni
	 * @param dniPaciente
	 * @return cita
	 */
	Cita findByDniPaciente(String dniPaciente);

	/***
	 * Eliminar una cita buscandola por su id
	 * @param id
	 * @return cita
	 */
	void deleteById(String id);
	
	/***
	 * Bustar una cita del paciente por el nombre del centro
	 * @param nombre
	 * @return cita
	 */
	List<Cita> findAllByNombreCentro(String nombre);

	
}
