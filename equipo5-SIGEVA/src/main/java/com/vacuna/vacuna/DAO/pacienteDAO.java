package com.vacuna.vacuna.DAO;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import com.vacuna.vacuna.model.Paciente;

@Repository
public interface pacienteDAO extends MongoRepository<Paciente, String> {

	public Paciente findByDni(String dni);

	public Paciente deleteByDni(String dni);
	
}
