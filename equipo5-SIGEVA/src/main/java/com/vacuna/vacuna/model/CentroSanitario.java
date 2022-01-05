package com.vacuna.vacuna.model;

import java.util.UUID;
import org.springframework.data.annotation.Id;

public class CentroSanitario {

		@Id 
		private String id;
		private String nombre;
		private int dosisTotales;
		private int aforo;
		private String localidad;
		private String provincia;
		
		
		public CentroSanitario(String nombre, int dosisTotales, int aforo, String localidad,
				String provincia) {
			super();
			this.nombre = nombre;
			this.dosisTotales = dosisTotales; //Valor constante de momento
			this.aforo = aforo; //Valor constante de momento
			this.localidad = localidad;
			this.provincia = provincia;
		}

		
		public void restarDosis() {
			dosisTotales -= 2;
		}
		
		public String getNombre() {
			return nombre;
		}


		public void setNombre(String nombre) {
			this.nombre = nombre;
		}


		public int getDosisTotales() {
			return dosisTotales;
		}


		public void setDosisTotales(int dosisTotales) {
			this.dosisTotales = dosisTotales;
		}


		public int getAforo() {
			return aforo;
		}


		public void setAforo(int aforo) {
			this.aforo = aforo;
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


		public CentroSanitario() {
			this.id = UUID.randomUUID().toString();
		}
		

		public String getId() {
			return id;
		}

		public void setId(String id) {
			this.id = id;
		}
		
}
