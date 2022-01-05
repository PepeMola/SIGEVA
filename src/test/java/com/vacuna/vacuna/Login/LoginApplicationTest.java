package com.vacuna.vacuna.Login;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.MethodOrderer;
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
import com.vacuna.vacuna.dao.UsuarioDAO;
import com.vacuna.vacuna.model.Administrador;
import com.vacuna.vacuna.model.Paciente;
import com.vacuna.vacuna.model.Sanitario;
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
class LoginApplicationTest {
	@Autowired
	private MockMvc mockMvc;
	@Autowired
	private WebApplicationContext controller;

	@Autowired
	private UsuarioDAO userDAO;
	private Sanitario a;
	private Administrador u;
	private Paciente p;
	
	private String TEST_NOMBRE = "Cristina Sanitario";
	private String TEST_EMAIL = "pruebaSanitario@gmail.com";
	private String TEST_PASSWORD = "Hola1236=";
	private String TEST_PASSWORD2 = "Hola1236=";
	private String TEST_DNI = "05724787H";
	private String TEST_TIPOUSUARIO = "Sanitario";
	private String TEST_CENTROASIGNADO = "Centro Prueba"; 
	
	private String TEST_NOMBRE1 = "Cristina Administrador";
	private String TEST_EMAIL1 = "pruebaAdministrador@gmail.com";
	private String TEST_DNI1 = "06724787H";
	private String TEST_TIPOUSUARIO1 = "Administrador";
	
	
	private String TEST_NOMBRE2 = "Cristina Paciente";
	private String TEST_EMAIL2 = "pruebaPaciente@gmail.com";
	private String TEST_DNI2 = "07724787H";
	private String TEST_TIPOUSUARIO2 = "Paciente";
	private String TEST_DOSIS = "0";
	private String TEST_LOCALIDAD = "Ciudad Real";
	private String TEST_PROVINCIA = "Ciudad Real";
	
	@BeforeEach
	/***
	 * 
	 * @throws NoSuchAlgorithmException
	 */
	public void setupMockMvc() throws NoSuchAlgorithmException{
		MessageDigest md = MessageDigest.getInstance("SHA-256");
		mockMvc = MockMvcBuilders.webAppContextSetup(controller).build();
		a = new Sanitario(TEST_NOMBRE, TEST_EMAIL,md.digest(TEST_PASSWORD.getBytes()), TEST_DNI, TEST_TIPOUSUARIO, TEST_CENTROASIGNADO);
		u = new Administrador(TEST_NOMBRE1, TEST_EMAIL1,md.digest(TEST_PASSWORD.getBytes()), TEST_DNI1, TEST_TIPOUSUARIO1, TEST_CENTROASIGNADO);
		p = new Paciente(TEST_NOMBRE2, TEST_EMAIL2,md.digest(TEST_PASSWORD.getBytes()), TEST_DNI2, TEST_TIPOUSUARIO2, TEST_CENTROASIGNADO, TEST_DOSIS, TEST_LOCALIDAD, TEST_PROVINCIA);
		userDAO.save(a);
		userDAO.save(u);
		userDAO.save(p);
	}
	
	@Test 
	/***
	 * 
	 * @throws Exception
	 */
	void LoginCorrecto() throws Exception {
		JSONObject json = new JSONObject();
		json.put("email", TEST_EMAIL);
		json.put("password", TEST_PASSWORD);
		System.out.println(json.toJSONString());		
		final ResultActions resultado = mockMvc.perform(MockMvcRequestBuilders.post("/login/login")
				.content(json.toString())
				.sessionAttr("userEmail", TEST_EMAIL)
				.contentType(org.springframework.http.MediaType.APPLICATION_JSON_UTF8))
				.andExpect(status().is(200));
	} 

	
	@Test
	/***
	 * 
	 * @throws Exception
	 */
	void expectedErrorCredencialesInvalidas() throws Exception {
		JSONObject json = new JSONObject();
		json.put("email", TEST_EMAIL);
		json.put("password", "Hola");
		System.out.println(json.toJSONString());		
		final ResultActions resultado = mockMvc.perform(MockMvcRequestBuilders.post("/login/login")
				.content(json.toString())
				.sessionAttr("userEmail", TEST_EMAIL)
				.contentType(org.springframework.http.MediaType.APPLICATION_JSON_UTF8))
				.andExpect(status().is(409));
	}

	@Test
	/***
	 * 
	 * @throws Exception
	 */
	void comprobarRolSanitarioCorrecto() throws Exception {
		JSONObject json = new JSONObject();
		json.put("email", TEST_EMAIL);
		System.out.println(json.toJSONString());		
		final ResultActions resultado = mockMvc.perform(MockMvcRequestBuilders.get("/login/comprobarRolSanitario")
				.content(json.toString())
				.sessionAttr("userEmail", TEST_EMAIL)
				.contentType(org.springframework.http.MediaType.APPLICATION_JSON_UTF8))
				.andExpect(content().string(("aprobado")));
	}
	
	@Test
	/***
	 * 
	 * @throws Exception
	 */
	void comprobarRolAdminCorrecto() throws Exception {
		JSONObject json = new JSONObject();
		json.put("email", TEST_EMAIL1);
		json.put("tipoUsuario", TEST_TIPOUSUARIO1);
		System.out.println(json.toJSONString());		
		final ResultActions resultado = mockMvc.perform(MockMvcRequestBuilders.get("/login/comprobarRolAdmin")
				.content(json.toString())
				.sessionAttr("userEmail", TEST_EMAIL1)
				.contentType(org.springframework.http.MediaType.APPLICATION_JSON_UTF8))
				.andExpect(content().string(("aprobado")));
	}

	
	@Test
	/***
	 * 
	 * @throws Exception
	 */
	void comprobarRolPacienteCorrecto() throws Exception {
		JSONObject json = new JSONObject();
		json.put("email", TEST_EMAIL2);
		System.out.println(json.toJSONString());		
		final ResultActions resultado = mockMvc.perform(MockMvcRequestBuilders.get("/login/comprobarRolPaciente")
				.content(json.toString())
				.sessionAttr("userEmail", TEST_EMAIL2)
				.contentType(org.springframework.http.MediaType.APPLICATION_JSON_UTF8))
				.andExpect(content().string(("aprobado")));
	}
	
	@Test
	/***
	 * 
	 * @throws Exception
	 */
	void comprobarRolSanitarioIncorrecto() throws Exception {
		JSONObject json = new JSONObject();
		json.put("email", "pruebFallo@gmail.com");
		System.out.println(json.toJSONString());		
		final ResultActions resultado = mockMvc.perform(MockMvcRequestBuilders.get("/login/comprobarRolSanitario")
				.content(json.toString())
				.sessionAttr("userEmail", "pruebFallo@gmail.com")
				.contentType(org.springframework.http.MediaType.APPLICATION_JSON_UTF8))
				.andExpect(content().string(("denegado")));
	}

	
	@Test
	/***
	 * 
	 * @throws Exception
	 */
	void comprobarRolAdministradorIncorrecto() throws Exception {
		JSONObject json = new JSONObject();
		json.put("email", "pruebFallo@gmail.com");
		System.out.println(json.toJSONString());		
		final ResultActions resultado = mockMvc.perform(MockMvcRequestBuilders.get("/login/comprobarRolAdmin")
				.content(json.toString())
				.sessionAttr("userEmail", TEST_EMAIL)
				.contentType(org.springframework.http.MediaType.APPLICATION_JSON_UTF8))
				.andExpect(content().string(("denegado")));
	}

	 
	@Test
	/***
	 * 
	 * @throws Exception
	 */
	void comprobarRolPacienteIncorrecto() throws Exception {
		JSONObject json = new JSONObject();
		json.put("email", "pruebFallo@gmail.com");
		System.out.println(json.toJSONString());		
		final ResultActions resultado = mockMvc.perform(MockMvcRequestBuilders.get("/login/comprobarRolPaciente")
				.content(json.toString())
				.sessionAttr("userEmail", TEST_EMAIL)
				.contentType(org.springframework.http.MediaType.APPLICATION_JSON_UTF8))
				.andExpect(content().string(("denegado")));
	}

	
	@Test
	/***
	 * 
	 * @throws Exception
	 */
	void expectedErrorNombreNoEncontrado() throws Exception {
		JSONObject json = new JSONObject();
		json.put("email", "");
		json.put("password", TEST_PASSWORD);
		System.out.println(json.toJSONString());		
		final ResultActions resultado = mockMvc.perform(MockMvcRequestBuilders.post("/login/login")
				.content(json.toString())
				.sessionAttr("userEmail", "")
				.contentType(org.springframework.http.MediaType.APPLICATION_JSON_UTF8))
				.andExpect(status().is(409));
	}

	
	@Test
	/***
	 * 
	 * @throws Exception
	 */
	void cambioPasswordCorrecto() throws Exception {
		JSONObject json = new JSONObject();
		json.put("email", TEST_EMAIL);
		json.put("password", "Hola1234567=");
		json.put("password2", "Hola1234567=");
		System.out.println(json.toJSONString());		
		final ResultActions resultado = mockMvc.perform(MockMvcRequestBuilders.post("/login/cambiarPassword")
				.content(json.toString())
				.sessionAttr("userEmail", TEST_EMAIL)
				.contentType(org.springframework.http.MediaType.APPLICATION_JSON_UTF8))
				.andExpect(status().is(200));
	}

	
	@Test
	/***
	 * 
	 * @throws Exception
	 */
	void cambioPasswordIncorrecto() throws Exception {
		JSONObject json = new JSONObject();
		json.put("email", TEST_EMAIL);
		json.put("password", TEST_PASSWORD);
		json.put("password2", TEST_PASSWORD2);
		System.out.println(json.toJSONString());		
		final ResultActions resultado = mockMvc.perform(MockMvcRequestBuilders.post("/login/cambiarPassword")
				.content(json.toString())
				.contentType(org.springframework.http.MediaType.APPLICATION_JSON_UTF8))
				.andExpect(status().is(409));
	}

	
	@Test
	/***
	 * 
	 * @throws Exception
	 */
	void expectedErrorCambiarPasword() throws Exception {
		JSONObject json = new JSONObject();
		json.put("email", TEST_EMAIL);
		json.put("password", TEST_PASSWORD);
		json.put("password2", "Hola12378=");
		System.out.println(json.toJSONString());		
		final ResultActions resultado = mockMvc.perform(MockMvcRequestBuilders.post("/login/cambiarPassword")
				.content(json.toString())
				.contentType(org.springframework.http.MediaType.APPLICATION_JSON_UTF8))
				.andExpect(status().is(409));
	}

	
	@Test
	/***
	 * 
	 * @throws Exception
	 */
	void primerInicioIncorrecto() throws Exception {
		JSONObject json = new JSONObject();
		json.put("email", TEST_EMAIL);
		System.out.println(json.toJSONString());		
		final ResultActions resultado = mockMvc.perform(MockMvcRequestBuilders.get("/login/primerInicio")
				.content(json.toString())
				.contentType(org.springframework.http.MediaType.APPLICATION_JSON_UTF8))
				.andExpect(status().is(409));
	}

	
	@Test
	/***
	 * 
	 * @throws Exception
	 */
	void getUsuarios() throws Exception {
	
		final ResultActions resultado = mockMvc.perform(MockMvcRequestBuilders.get("/login/getUser")
				.sessionAttr("userEmail", TEST_EMAIL)
				.contentType(org.springframework.http.MediaType.APPLICATION_JSON_UTF8)).andExpect(status().isOk());
		
	}

	
	@AfterAll
	public void deleteAll() {
		Usuario u = userDAO.findByEmail(TEST_EMAIL);
		if(u!=null)
			userDAO.delete(u);
			userDAO.delete(a);
			userDAO.delete(p);
	}
}
