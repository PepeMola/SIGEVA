package com.vacuna.vacuna.model;

import javax.persistence.Lob;

import org.springframework.data.annotation.Id;
/***
 * 
 * @author crist
 *
 */
public class Usuario {

	protected String nombre;
	@Id
	protected String email;
	@Lob
	protected byte[] password;
	protected String dni;
	protected String tipoUsuario;
	protected String centroAsignado;
	
	/***
	 * Constructor usuario
	 * @param nombre
	 * @param email
	 * @param password
	 * @param dni
	 * @param tipoUsuario
	 * @param centroAsignado
	 */
	public Usuario(String nombre, String email, byte[] password, String dni, String tipoUsuario, String centroAsignado) {
		super();
		this.nombre = nombre;
		this.email = email;
		this.password = password;
		this.dni = dni; 
		this.tipoUsuario = tipoUsuario;
		this.centroAsignado = centroAsignado;
	}
	
	/***
	 * getCentroAsignado
	 * @return centroAsignado
	 */
	public String getCentroAsignado() {
		return centroAsignado;
	}
	
	/***
	 * setCentroAsignado
	 * @param centroAsignado
	 */
	public void setCentroAsignado(String centroAsignado) {
		this.centroAsignado = centroAsignado;
	}
	
	/***
	 * getNombre
	 * @return nombre
	 */
	public String getNombre() {
		return nombre;
	}
	
	/***
	 * setNombre
	 * @param nombre
	 */
	public void setNombre(String nombre) {
		this.nombre = nombre;
	}
	
	/***
	 * getEmail
	 * @return email
	 */
	public String getEmail() {
		return email;
	}
	
	/***
	 * getPassword
	 * @return password
	 */
	public byte[] getPassword() {
		return password;
	}
	
	/***
	 * setPassword
	 * @param password
	 */
	public void setPassword(byte[] password) {
		this.password = password;
	}
	
	/***
	 * getDni
	 * @return dni
	 */
	public String getDni() {
		return dni;
	}
	
	/***
	 * getTipoUsuario
	 * @return tipoUsuario
	 */ 
	public String getTipoUsuario() {
		return tipoUsuario;
	}
}
