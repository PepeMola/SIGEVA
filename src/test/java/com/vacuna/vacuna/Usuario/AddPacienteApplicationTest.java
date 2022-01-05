package com.vacuna.vacuna.Usuario;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

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
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import com.vacuna.vacuna.VacunaApplication;
import com.vacuna.vacuna.dao.CentroSanitarioDAO;
import com.vacuna.vacuna.dao.UsuarioDAO;
import com.vacuna.vacuna.model.CentroSanitario;
import com.vacuna.vacuna.model.Usuario;

import net.minidev.json.JSONObject;
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
class AddPacienteApplicationTest {
	@Autowired
	private MockMvc mockMvc;

	@Autowired
	private WebApplicationContext controller;
	@Autowired
	private CentroSanitarioDAO DAO;
	@Autowired
	private UsuarioDAO userDAO;
	
	private CentroSanitario centro;
	private String TEST_NOMBRE = "Cristina Paciente";
	private String TEST_EMAIL = "pruebaPAciente@gmail.com";
	private String TEST_PASSWORD = "Hola1236=";
	private String TEST_PASSWORD2 = "Hola1236=";
	private String TEST_DNI = "05724787H";
	private String TEST_TIPOUSUARIO = "Paciente";
	private String TEST_CENTROASIGNADO = "Centro Prueba add paciente";
	private String TEST_LOCALIDAD = "Ciudad Real";
	private String TEST_PROVINCIA = "Ciudad Real";
	
	@BeforeEach
	public void setupMockMvc() {
		mockMvc = MockMvcBuilders.webAppContextSetup(controller).build();
		
	}
	
	@BeforeAll
	public void setupTest() {
		centro = new CentroSanitario(TEST_CENTROASIGNADO, 2000, 2, 8, 20, "Ciudad Real", "Ciudad Real");
		DAO.save(centro);
	}

	
	@Test 
	@Order(1)
	/***
	 * 
	 * @throws Exception
	 */
	void savePacienteCorrecto() throws Exception {
		JSONObject json = new JSONObject();
		json.put("nombre", TEST_NOMBRE);
		json.put("email", TEST_EMAIL);
		json.put("password", TEST_PASSWORD);
		json.put("password2", TEST_PASSWORD2);
		json.put("dni", TEST_DNI);
		json.put("tipoUsuario", TEST_TIPOUSUARIO);
		json.put("centroAsignado", TEST_CENTROASIGNADO); 
		json.put("localidad", TEST_LOCALIDAD);
		json.put("provincia", TEST_PROVINCIA);
		json.put("dosisAdministradas", "0");
		System.out.println(json.toJSONString());
		
		final ResultActions resultado = mockMvc.perform(put("/Usuario/add")
				.content(json.toString())
				.contentType(org.springframework.http.MediaType.APPLICATION_JSON_UTF8))
				.andExpect(status().is(200));
	}


	@Test
	/***
	 * 
	 * @throws Exception
	 */
	void expectedErrorDniNombreVacios() throws Exception {
		JSONObject json = new JSONObject();
		json.put("nombre", "");
		json.put("email", TEST_EMAIL);
		json.put("password", TEST_PASSWORD);
		json.put("password2", TEST_PASSWORD2);
		json.put("dni", "");
		json.put("tipoUsuario", TEST_TIPOUSUARIO);
		json.put("centroAsignado", TEST_CENTROASIGNADO);
		json.put("localidad", TEST_LOCALIDAD);
		json.put("provincia", TEST_PROVINCIA);
		json.put("dosisAdministradas", "0");
		final ResultActions resultado = mockMvc.perform(put("/Usuario/add",json.toString())
				.content(json.toString())
				.contentType(org.springframework.http.MediaType.APPLICATION_JSON_UTF8))
				.andExpect(status().is(409));
				
	}


	@Test
	/***
	 * 
	 * @throws Exception
	 */
	void expectedErrorPacienteExistente() throws Exception {
		JSONObject json = new JSONObject();
		json.put("nombre", TEST_NOMBRE);
		json.put("email", TEST_EMAIL);
		json.put("password", TEST_PASSWORD);
		json.put("password2", TEST_PASSWORD2);
		json.put("dni", TEST_DNI);
		json.put("tipoUsuario", TEST_TIPOUSUARIO);
		json.put("centroAsignado", TEST_CENTROASIGNADO);
		json.put("localidad", TEST_LOCALIDAD);
		json.put("provincia", TEST_PROVINCIA);
		json.put("dosisAdministradas", "0");
		final ResultActions resultado = mockMvc.perform(put("/Usuario/add",json.toString())
				.content(json.toString())
				.contentType(org.springframework.http.MediaType.APPLICATION_JSON_UTF8))
				.andExpect(status().is(409));
	}

	
	@Test
	/***
	 * 
	 * @throws Exception
	 */
	void expectedErrorDniIncorrecto() throws Exception {
		JSONObject json = new JSONObject();
		json.put("nombre", TEST_NOMBRE);
		json.put("email", TEST_EMAIL);
		json.put("password", TEST_PASSWORD);
		json.put("password2", TEST_PASSWORD2);
		json.put("dni", "55");
		json.put("tipoUsuario", TEST_TIPOUSUARIO);
		json.put("centroAsignado", TEST_CENTROASIGNADO);
		json.put("localidad", TEST_LOCALIDAD);
		json.put("provincia", TEST_PROVINCIA);
		json.put("dosisAdministradas", "0");
		final ResultActions resultado = mockMvc.perform(put("/Usuario/add",json.toString())
				.content(json.toString())
				.contentType(org.springframework.http.MediaType.APPLICATION_JSON_UTF8))
				.andExpect(status().is(409));
	}

	 
	@Test
	/***
	 * 
	 * @throws Exception
	 */
	void expectedErrorEmailIncorrecto() throws Exception {
		JSONObject json = new JSONObject();
		json.put("nombre", TEST_NOMBRE);
		json.put("email", "emailError");
		json.put("password", TEST_PASSWORD);
		json.put("password2", TEST_PASSWORD2);
		json.put("dni", TEST_DNI);
		json.put("tipoUsuario", TEST_TIPOUSUARIO);
		json.put("centroAsignado", TEST_CENTROASIGNADO);
		json.put("localidad", TEST_LOCALIDAD);
		json.put("provincia", TEST_PROVINCIA);
		json.put("dosisAdministradas", "0");
		final ResultActions resultado = mockMvc.perform(put("/Usuario/add",json.toString())
				.content(json.toString())
				.contentType(org.springframework.http.MediaType.APPLICATION_JSON_UTF8))
				.andExpect(status().is(409));
	}
	
	
	@Test
	/***
	 * 
	 * @throws Exception
	 */
	void expectedErrorPasswordIncorrecta() throws Exception {
			JSONObject json = new JSONObject();
			json.put("nombre", TEST_NOMBRE);
			json.put("email", TEST_EMAIL);
			json.put("password", "hola");
			json.put("password2", "hola");
			json.put("dni", TEST_DNI);
			json.put("tipoUsuario", TEST_TIPOUSUARIO);
			json.put("centroAsignado", TEST_CENTROASIGNADO);
			json.put("localidad", "");
			json.put("provincia", "");
			json.put("dosisAdministradas", "");
			final ResultActions resultado = mockMvc.perform(put("/Usuario/add",json.toString())
					.content(json.toString())
					.contentType(org.springframework.http.MediaType.APPLICATION_JSON_UTF8))
					.andExpect(status().is(409));
	}

	
	@Test
	/***
	 * 
	 * @throws Exception
	 */
	void expectedErrorPasswordNoCoincide() throws Exception {
			JSONObject json = new JSONObject();
			json.put("nombre", TEST_NOMBRE);
			json.put("email", TEST_EMAIL);
			json.put("password", "hola");
			json.put("password2", "hola12");
			json.put("dni", TEST_DNI);
			json.put("tipoUsuario", TEST_TIPOUSUARIO);
			json.put("centroAsignado", TEST_CENTROASIGNADO);
			json.put("localidad", "");
			json.put("provincia", "");
			json.put("dosisAdministradas", "");
			final ResultActions resultado = mockMvc.perform(put("/Usuario/add",json.toString())
					.content(json.toString())
					.contentType(org.springframework.http.MediaType.APPLICATION_JSON_UTF8))
					.andExpect(status().is(409));
	}

	
	@AfterAll
	public void deleteAll() {
		Usuario u = userDAO.findByEmail(TEST_EMAIL);
		if(u!=null)
			userDAO.delete(u);
		DAO.delete(centro);
	}

}
