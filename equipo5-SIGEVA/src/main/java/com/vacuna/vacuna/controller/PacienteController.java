package com.vacuna.vacuna.controller;

import java.util.List;
import java.util.Map;

import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import com.vacuna.vacuna.DAO.CentroSanitarioDAO;
import com.vacuna.vacuna.DAO.pacienteDAO;
import com.vacuna.vacuna.model.CentroSanitario;
import com.vacuna.vacuna.model.Paciente;

@RestController

@RequestMapping("paciente")

@CrossOrigin("*")
public class PacienteController {

	@Autowired
	private pacienteDAO repository;
	
	@Autowired
	private CentroSanitarioDAO csrepository;


	@PutMapping("/add")
	public Paciente add(@RequestBody Map<String, Object> info) throws Exception {
		JSONObject jso = new JSONObject(info);
		String nombre = jso.optString("nombre");
		String apellidos = jso.optString("apellidos");
		String dni = jso.optString("dni");
		String tipoUsuario = jso.optString("tipoUsuario");
		String centroAsignado = jso.optString("centroAsignado");
		//String dosisAdministradas = jso.optString("dosisAdinistradas");
		String localidad = jso.optString("localidad");
		String provincia = jso.optString("provincia");

		if (nombre.isEmpty() || dni.isEmpty()) {
			throw new ResponseStatusException(HttpStatus.CONFLICT, "Debes rellenar todos los campos del usuario");
		}

		validarDNI(dni);

		if (repository.findByDni(dni) != null) {
			throw new ResponseStatusException(HttpStatus.CONFLICT, "El usuario ya existe");
		}

		Paciente p = new Paciente(nombre, apellidos, dni, tipoUsuario, centroAsignado, "0", localidad, provincia);
		return repository.insert(p);

	}

	@GetMapping("/getTodos")
	public List<Paciente> get() {
		try {
			return repository.findAll();
		} catch (Exception e) {
			throw new ResponseStatusException(HttpStatus.NOT_FOUND, e.getMessage());
		}
	}
	
	@GetMapping("/getCentros")
	public List<CentroSanitario> devolverCentro() {
		try {
			return csrepository.findAll();

		} catch (Exception e) {
			throw new ResponseStatusException(HttpStatus.NOT_FOUND, e.getMessage());
		}

	}

	@Transactional
	@DeleteMapping("/eliminarUsuario/{dni}")
	public Paciente eliminarUsuario(@PathVariable String dni) {
		validarDNI(dni);
		if (repository.findByDni(dni) == null) {
			throw new ResponseStatusException(HttpStatus.CONFLICT, "El usuario no existe");
		}
		return repository.deleteByDni(dni);

	}

	@GetMapping("/buscarDni/{dni}")
	public Paciente readAll(@PathVariable String dni) {
		validarDNI(dni);
		if (repository.findByDni(dni) == null) {
			throw new ResponseStatusException(HttpStatus.CONFLICT, "El usuario no existe");
		}
		return repository.findByDni(dni);
	}

	@RequestMapping("/modificarUsuarios")
	public Paciente update(@RequestBody Paciente p) {
		return repository.save(p);
	}

	public boolean validarDNI(String dni) {
		boolean valido = true;
		if (dni.length() != 9) {
			valido = false;
			throw new ResponseStatusException(HttpStatus.CONFLICT, "DNI no valido, cantidad de digitos incorrecta");

		}
		String entero = dni.substring(0, 8);
		System.out.println(entero);

		if (!entero.matches("[0-9]+")) {
			System.out.println(entero);
			throw new ResponseStatusException(HttpStatus.CONFLICT, "DNI no valido, intentelo de nuevo");
		}
		String letra = dni.substring(8, 9);
		if (letra.matches("[0-9]+")) {
			System.out.println(letra);
			throw new ResponseStatusException(HttpStatus.CONFLICT, "DNI no valido, intentelo de nuevo");
		}
		return valido;
	}
}