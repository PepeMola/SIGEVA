package com.vacuna.vacuna.DAO;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.vacuna.vacuna.model.Cita;

public interface citaDAO extends MongoRepository<Cita, String>{
	
}
