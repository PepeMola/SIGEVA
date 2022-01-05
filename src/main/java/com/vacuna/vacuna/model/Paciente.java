package com.vacuna.vacuna.model;
/***
 * 
 * @author crist
 *
 */
public class Paciente extends Usuario {

	private String dosisAdministradas;
	private String localidad;
	private String provincia;
	private boolean primeraVez = true;
	/***
	 * Contrustor paciente
	 * @param nombre
	 * @param email
	 * @param password
	 * @param dni
	 * @param tipoUsuario
	 * @param centroAsignado
	 * @param dosisAdministradas
	 * @param localidad
	 * @param provincia
	 */
	public Paciente(String nombre, String email, byte[] password, String dni, String tipoUsuario, String centroAsignado,
			String dosisAdministradas, String localidad, String provincia) {
		super(nombre, email, password, dni, tipoUsuario, centroAsignado);
		this.dosisAdministradas = dosisAdministradas;
		this.localidad = localidad;
		this.provincia = provincia;
	} 
	
	/***
	 * isPrimeraVez
	 * @return primeraVez
	 */
	public boolean isPrimeraVez() {
		return primeraVez;
	}
	
	/***
	 * setPrimeraVez
	 * @param primeraVez
	 */
	public void setPrimeraVez(boolean primeraVez) {
		this.primeraVez = primeraVez;
	}
	
	/***
	 * getDosisAdministradas
	 * @return dosisAdministradas
	 */
	public String getDosisAdministradas() {
		return dosisAdministradas;
	}
	
	/***
	 * setDosisAdministradas
	 * @param dosisAdministradas
	 */
	public void setDosisAdministradas(String dosisAdministradas) {
		this.dosisAdministradas = dosisAdministradas;
	}
	
	/***
	 * getLocalidad
	 * @return localidad
	 */
	public String getLocalidad() {
		return localidad;
	}
	
	/***
	 * setLocalidad
	 * @param localidad
	 */
	public void setLocalidad(String localidad) {
		this.localidad = localidad;
	}
	
	/***
	 * getProvincia
	 * @return provincia
	 */
	public String getProvincia() {
		return provincia;
	}
	
	/***
	 * setProvincia
	 * @param provincia
	 */
	public void setProvincia(String provincia) {
		this.provincia = provincia;
	}
	

}
