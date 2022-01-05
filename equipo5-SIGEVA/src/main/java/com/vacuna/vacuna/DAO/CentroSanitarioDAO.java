package com.vacuna.vacuna.DAO;

import org.springframework.data.mongodb.repository.MongoRepository;
import com.vacuna.vacuna.model.CentroSanitario;

public interface CentroSanitarioDAO extends MongoRepository<CentroSanitario, String>{

}
