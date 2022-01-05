package com.vacuna.vacuna.model;

import java.util.UUID;
import org.springframework.data.annotation.Id;

public class Cita {
	
	@Id 
	private String id ;
	private long fechaPrimeraDosis;
	private long fechaSegundaDosis;
	
	public Cita(long fechaPrimeraDosis, long fechaSegundaDosis) {
		super();
		this.fechaPrimeraDosis = fechaPrimeraDosis;
		this.fechaSegundaDosis = fechaSegundaDosis;
	}

	public Cita() {
		this.id = UUID.randomUUID().toString();
	}
	
	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public long getFechaPrimeraDosis() {
		return fechaPrimeraDosis;
	}

	public void setFechaPrimeraDosis(long fechaPrimeraDosis) {
		this.fechaPrimeraDosis = fechaPrimeraDosis;
	}

	public long getFechaSegundaDosis() {
		return fechaSegundaDosis;
	}

	public void setFechaSegundaDosis(long fechaSegundaDosis) {
		this.fechaSegundaDosis = fechaSegundaDosis;
	}
	
	
	
}
