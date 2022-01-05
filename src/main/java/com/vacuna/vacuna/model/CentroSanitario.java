package com.vacuna.vacuna.model;

import java.util.UUID;
import org.springframework.data.annotation.Id;
/***
 * 
 * @author crist
 *
 */
public class CentroSanitario {

		@Id 
		private String id;
		private String nombre;
		private int dosisTotales;
		private int aforo;
		private int horaInicio;
		private int horaFin;
		private String localidad;
		private String provincia;
		
		/***
		 *  Constructor del centro sanitario
		 * @param nombre
		 * @param dosisTotales
		 * @param aforo
		 * @param horaInicio
		 * @param horaFin
		 * @param localidad
		 * @param provincia
		 */
		public CentroSanitario(String nombre, int dosisTotales, int aforo,int horaInicio,int horaFin, String localidad,
				String provincia) {
			super();
			this.nombre = nombre;
			this.dosisTotales = dosisTotales; //Valor constante de momento
			this.aforo = aforo; //Valor constante de momento
			this.horaInicio = horaInicio;
			this.horaFin = horaFin;
			this.localidad = localidad;
			this.provincia = provincia;
		}


		/***
		 * getHoraInicio
		 * @return horaInicio
		 */
		public int getHoraInicio() {
			return horaInicio;
		}
		
		/***
		 * setHoraInicio
		 * @param horaInicio
		 */
		public void setHoraInicio(int horaInicio) {
			this.horaInicio = horaInicio;
		}

		/***
		 * getHoraFin
		 * @return horaFin
		 */
		public int getHoraFin() {
			return horaFin;
		}
		
		/***
		 * setHoraFin
		 * @param horaFin
		 */
		public void setHoraFin(int horaFin) {
			this.horaFin = horaFin;
		}


		public void restarDosis() {
			dosisTotales -= 2;
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
		 * getDosisTotales
		 * @return dosisTotales
		 */
		public int getDosisTotales() {
			return dosisTotales;
		}
		
		/***
		 * setDosisTotales
		 * @param dosisTotales
		 */
		public void setDosisTotales(int dosisTotales) {
			this.dosisTotales = dosisTotales;
		}
		
		/***
		 * getAforo
		 * @return aforo
		 */
		public int getAforo() {
			return aforo;
		}
		
		/***
		 * setAforo
		 * @param aforo
		 */
		public void setAforo(int aforo) {
			this.aforo = aforo;
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

		
		public CentroSanitario() {
			this.id = UUID.randomUUID().toString();
		}
		
		/***
		 * getId
		 * @return id
		 */
		public String getId() {
			return id;
		}
}
