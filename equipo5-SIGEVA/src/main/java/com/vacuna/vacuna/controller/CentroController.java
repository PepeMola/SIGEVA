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
import com.vacuna.vacuna.model.CentroSanitario;
import com.vacuna.vacuna.model.Paciente;

@RestController

@RequestMapping("centro")

@CrossOrigin("*")
public class CentroController {

	@Autowired
	private CentroSanitarioDAO repository;

	@PutMapping("/add")
	public CentroSanitario add(@RequestBody Map<String, Object> info) throws Exception {
		JSONObject jso = new JSONObject(info);
		String nombre = jso.optString("nombre");
		String dosisT = jso.optString("dosisTotales");
		int dosisTotales = Integer.parseInt(dosisT);
		String af = jso.optString("aforo");
		int aforo = Integer.parseInt(af);
		String localidad = jso.optString("localidad");
		String provincia = jso.optString("provincia");

		// if (nombre.isEmpty() || dni.isEmpty()) {
		// throw new ResponseStatusException(HttpStatus.CONFLICT, "Debes rellenar todos
		// los campos del usuario");
		// }

		// validarDNI(dni);

		// if (repository.findByDni(dni) != null) {
		// throw new ResponseStatusException(HttpStatus.CONFLICT, "El usuario ya
		// existe");
		// }

		CentroSanitario c = new CentroSanitario(nombre, dosisTotales, aforo, localidad, provincia);
		return repository.insert(c);

	}

	@GetMapping("/getTodos")
	public List<CentroSanitario> get() {
		try {
			return repository.findAll();
		} catch (Exception e) {
			throw new ResponseStatusException(HttpStatus.NOT_FOUND, e.getMessage());
		}
	}

	@RequestMapping("/modificarCentros")
	public CentroSanitario update(@RequestBody CentroSanitario cs) {
		return repository.save(cs);
	}
	
	@Transactional

	@DeleteMapping("/eliminarCentro/{id}")
	public Paciente eliminarUsuario(@PathVariable String id) {
		repository.deleteById(id);
		return null;

	}

}