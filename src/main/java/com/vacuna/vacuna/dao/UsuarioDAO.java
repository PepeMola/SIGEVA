package com.vacuna.vacuna.dao;


import java.util.List;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import com.vacuna.vacuna.model.CentroSanitario;
import com.vacuna.vacuna.model.Usuario;

@Repository
public interface UsuarioDAO extends MongoRepository<Usuario, String> {

	/***
	 * Buscar un usuario por su dni
	 * @param dni
	 * @return Usuario
	 */
	Usuario findByDni(String dni);
	
	/***
	 * Buscar a un usuario por su email
	 * @param email
	 * @return usuario
	 */
	Usuario findByEmail(String email);
	
	/***
	 * Eliminar un usuario buscandolo por su dni
	 * @param dni
	 * @return usuario
	 */
	Usuario deleteByDni(String dni);
	
	/***
	 * Eliminar un usuario buscandolo por su email
	 * @param email
	 * @return usuario
	 */
	Usuario deleteByEmail(String email);
	
	/***
	 * Buscar un usuario por su email y contraseña
	 * @param email
	 * @param pwd
	 * @return usuario
	 */
	Usuario findByEmailAndPassword(String email, byte[] pwd);
	
	/***
	 * Listar usuario buscandolos por el tipo
	 * @param tipoUsuario
	 * @return usuario
	 */
	List<Usuario> findAllByTipoUsuario(String tipoUsuario);
	
	/***
	 * Buscar usuarios por su centro sanitario
	 * @param centroAsignado
	 * @return usuario
	 */
	List<Usuario> findAllByCentroAsignado(String centroAsignado);
	
	/***
	 * Buscar centro sanitario del usuario por el email de este
	 * @param email
	 * @return centro sanitario
	 */
	CentroSanitario findByCentroAsignado(String email);

}
