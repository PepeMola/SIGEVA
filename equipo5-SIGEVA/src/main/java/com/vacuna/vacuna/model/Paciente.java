package com.vacuna.vacuna.model;

import org.springframework.data.annotation.Id;

public class Paciente {

	private String nombre;
	private String apellidos;
	@Id
	private String dni;
	private String tipoUsuario;
	private String centroAsignado;
	private String dosisAdministradas;
	private String localidad;
	private String provincia;

	public Paciente() {
		super();
	}

	public Paciente(String nombre, String apellidos, String dni, String tipoUsuario, String centroAsignado,
			String dosisAdministradas, String localidad, String provincia) {
		this.nombre = nombre;
		this.apellidos = apellidos;
		this.dni = dni;
		this.tipoUsuario = tipoUsuario;
		this.centroAsignado = centroAsignado;
		this.dosisAdministradas = dosisAdministradas;
		this.localidad = localidad;
		this.provincia = provincia;
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public String getApellidos() {
		return apellidos;
	}

	public void setApellidos(String apellidos) {
		this.apellidos = apellidos;
	}

	public String getDni() {
		return dni;
	}
	
	
	public String getTipoUsuario() {
		return tipoUsuario;
	}

	public void setTipoUsuario(String tipoUsuario) {
		this.tipoUsuario = tipoUsuario;
	}

	public String getCentroAsignado() {
		return centroAsignado;
	}

	public void setCentroAsignado(String centroAsignado) {
		this.centroAsignado = centroAsignado;
	}

	public String getDosisAdministradas() {
		return dosisAdministradas;
	}

	public void setDosisAdministradas(String dosisAdministradas) {
		this.dosisAdministradas = dosisAdministradas;
	}

	public String getLocalidad() {
		return localidad;
	}

	public void setLocalidad(String localidad) {
		this.localidad = localidad;
	}

	public String getProvincia() {
		return provincia;
	}

	public void setProvincia(String provincia) {
		this.provincia = provincia;
	}

}
