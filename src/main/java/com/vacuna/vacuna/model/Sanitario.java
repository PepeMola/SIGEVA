package com.vacuna.vacuna.model;
/***
 * 
 * @author crist
 *
 */
public class Sanitario extends Usuario {

	private boolean primeraVez = true;
	/***
	 * Constructor sanitario
	 * @param nombre
	 * @param email
	 * @param password
	 * @param dni
	 * @param tipoUsuario
	 * @param centroAsignado
	 */
	public Sanitario(String nombre, String email, byte[] password, String dni, String tipoUsuario, String centroAsignado) {
		super(nombre, email, password, dni, tipoUsuario, centroAsignado);
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
	
}