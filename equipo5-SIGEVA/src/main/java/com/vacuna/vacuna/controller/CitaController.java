package com.vacuna.vacuna.controller;


import java.util.Date;
import java.util.Iterator;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import com.vacuna.vacuna.DAO.citaDAO;
import com.vacuna.vacuna.DAO.pacienteDAO;
import com.vacuna.vacuna.model.CentroSanitario;
import com.vacuna.vacuna.model.Cita;
import com.vacuna.vacuna.model.Paciente;

@RestController
@RequestMapping("cita")
public class CitaController {

	@Autowired
	private citaDAO repository;

	@PutMapping("/add")
	public Cita add() throws Exception {

		CentroSanitario centroSanitario = new CentroSanitario("hospitalillo", 2000, 2, "Ciudad Real", "Ciudad Real"); //modificar


		List<Cita> listaCitas = repository.findAll();

		Date today = new Date();
		Date tomorrow = new Date(today.getTime() + (1000 * 60 * 60 * 24));

		long fechaActual = new Date(tomorrow.getYear(),tomorrow.getMonth(), tomorrow.getDate(), 8, 0).getTime();
		int contadorAforo = 0; //Aforo para el unico centro que tenemos
		
		Cita c = new Cita();
		c.setFechaPrimeraDosis(fechaActual);
		c.setFechaSegundaDosis(fechaActual + ((1000 * 60 * 60 * 24 * 21) + 3600000));
		
		if(listaCitas.size()==0) {
			if(centroSanitario.getDosisTotales()>=2) {
				c.setFechaPrimeraDosis(fechaActual);
				c.setFechaSegundaDosis(fechaActual + ((1000 * 60 * 60 * 24 * 21) + 3600000));
				centroSanitario.restarDosis();
			}else{
				throw new Exception("No hay dosis disponibles.");
			}
		}else {
			for (int i = 0; i < listaCitas.size();i++) {
				Cita cita = listaCitas.get(i);
				if ((cita.getFechaPrimeraDosis() == fechaActual) || (cita.getFechaSegundaDosis() == fechaActual)) { // 1 cita - 8 - Lunes --- 2
					contadorAforo++;
					if(contadorAforo == centroSanitario.getAforo()) {
						if(new Date(fechaActual).getHours() == 20 ) {
							fechaActual += (3600000*12); //Proximo dia a las 08.00am las fechas son una mierda
						}else {
							fechaActual += 3600000; //Siguiente rango de horas
						}
						contadorAforo = 0;
						i = 0;
					}
				}else {
					if(contadorAforo < centroSanitario.getAforo()) {
						if(centroSanitario.getDosisTotales()>=2) {
							c = new Cita();
							c.setFechaPrimeraDosis(fechaActual);
							c.setFechaSegundaDosis(fechaActual + ((1000 * 60 * 60 * 24 * 21) + 3600000));
							centroSanitario.restarDosis();
						}
					}else{
						throw new Exception("No hay dosis disponibles.");
					}
				}
			}
		}

		return repository.insert(c);
	}

	@GetMapping("/getTodos")
	public List<Cita> get() {
		try {
			return repository.findAll();
		} catch(Exception e) {
			throw new ResponseStatusException(HttpStatus.NOT_FOUND, e.getMessage());
		}
	}


	@Transactional
	@DeleteMapping("/eliminarCita/{id}")
	public Paciente eliminarCarrito(@PathVariable String id) {
		repository.deleteById(id);
		return null;
	}

	@GetMapping("/")
	public List<Cita> readAll() {
		return repository.findAll();
	}


	@DeleteMapping("/cita/{id}")
	public void delete(@PathVariable String id) {
		repository.deleteById(id);
	}
}
