package com.vacuna.vacuna.Citas;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.json.JSONObject;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.TestInstance.Lifecycle;
import org.junit.jupiter.api.TestMethodOrder;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import com.vacuna.vacuna.VacunaApplication;
import com.vacuna.vacuna.dao.CentroSanitarioDAO;
import com.vacuna.vacuna.dao.CitaDAO;
import com.vacuna.vacuna.dao.UsuarioDAO;
import com.vacuna.vacuna.model.CentroSanitario;
import com.vacuna.vacuna.model.Cita;
import com.vacuna.vacuna.model.Paciente;
/***
 * 
 * @author crist
 *
 */

@ExtendWith(SpringExtension.class)

@SpringBootTest(classes = VacunaApplication.class)
@AutoConfigureMockMvc
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@TestInstance(Lifecycle.PER_CLASS)
class AddCitaApplicationTest {
	@Autowired 
	private MockMvc mockMvc;
	@Autowired
	private WebApplicationContext controller;
	@Autowired
	private CentroSanitarioDAO DAO;
	@Autowired
	private UsuarioDAO userDAO;
	private Paciente p;
	private Paciente pError;
	@Autowired
	private CitaDAO citaDAO;
	
	
	private CentroSanitario centro;
	private String TEST_NOMBRE = "Cristina Paciente";
	private String TEST_EMAIL = "pruebaCita@gmail.com";
	private String TEST_EMAIL1 = "pruebaCitaError@gmail.com";
	private String TEST_PASSWORD = "Hola1236=";
	private String TEST_DNI = "05724787H";
	private String TEST_DNI1 = "08723787H";
	private String TEST_TIPOUSUARIO = "Paciente";
	private String TEST_DOSIS = "0";
	private String TEST_DOSIS1 = "2";
	private String TEST_CENTROASIGNADO = "Centro Prueba Cita";
	private String TEST_LOCALIDAD = "Ciudad Real";
	private String TEST_PROVINCIA = "Ciudad Real";
	
	
	@BeforeEach
	public void setupMockMvc(){
		mockMvc = MockMvcBuilders.webAppContextSetup(controller).build();
	}
	@BeforeAll

	public void setupTest() {
		centro = new CentroSanitario(TEST_CENTROASIGNADO, 2000, 2, 8, 20, "Ciudad Real", "Ciudad Real");
		DAO.save(centro);
		p = new Paciente(TEST_NOMBRE, TEST_EMAIL,TEST_PASSWORD.getBytes(), TEST_DNI, TEST_TIPOUSUARIO, TEST_CENTROASIGNADO, TEST_DOSIS, TEST_LOCALIDAD, TEST_PROVINCIA);
		pError = new Paciente(TEST_NOMBRE, TEST_EMAIL1,TEST_PASSWORD.getBytes(), TEST_DNI1, TEST_TIPOUSUARIO, TEST_CENTROASIGNADO, TEST_DOSIS1, TEST_LOCALIDAD, TEST_PROVINCIA);
		userDAO.save(p);
		userDAO.save(pError);
	}

	@Test 
	@Order(1)
	/***
	 * Test añadir cita correctamente
	 * @throws Exception
	 */
	void addCitaCorrecto() throws Exception {
		JSONObject json = new JSONObject();
		json.put("dni", TEST_DNI);
		json.put("nombre", TEST_NOMBRE);
		json.put("centroAsignado", TEST_CENTROASIGNADO);
		System.out.println(json.toString());
		
		final ResultActions resultado = mockMvc.perform(MockMvcRequestBuilders.put("/cita/add")
				.content(json.toString())
				.contentType(org.springframework.http.MediaType.APPLICATION_JSON_UTF8))
				.andExpect(status().is(200));
	}

	
	@Test
	@Order(2)
	/***
	 * Expected error Test añadir cita incorrecto
	 * @throws Exception
	 */
	void addCitaIncorrecto() throws Exception {
		JSONObject json = new JSONObject();
		json.put("dni", TEST_DNI1);
		json.put("nombre", TEST_NOMBRE);
		json.put("centroAsignado", TEST_CENTROASIGNADO);
		System.out.println(json.toString());
		
		final ResultActions resultado = mockMvc.perform(MockMvcRequestBuilders.put("/cita/add")
				.content(json.toString())
				.contentType(org.springframework.http.MediaType.APPLICATION_JSON_UTF8))
				.andExpect(status().is(409));
	}

	
	@AfterAll
	void deleteAll() {
		Paciente p = (Paciente) userDAO.findByEmail(TEST_EMAIL);
		if(p!=null) {
			userDAO.delete(p);
			userDAO.delete(pError);
		}
		DAO.delete(centro);
		Cita c = citaDAO.findByDniPaciente(TEST_DNI);
		if(c!=null) 
			citaDAO.delete(c);
	}

}
