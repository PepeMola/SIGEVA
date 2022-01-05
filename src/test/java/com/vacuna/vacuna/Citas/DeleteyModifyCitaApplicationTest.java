package com.vacuna.vacuna.Citas;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.text.SimpleDateFormat;
import java.util.Date;

import org.json.JSONObject;
import org.junit.jupiter.api.AfterEach;
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
class DeleteyModifyCitaApplicationTest {
	@Autowired 
	private MockMvc mockMvc;
	@Autowired
	private WebApplicationContext controller;

	@Autowired
	private CitaDAO citaDAO;
	private Cita c;
	@Autowired
	private UsuarioDAO DAO;
	private Paciente p;
	@Autowired
	private CentroSanitarioDAO centroDAO;
	private CentroSanitario centro;
	
	public void setupMockMvc(){
		mockMvc = MockMvcBuilders.webAppContextSetup(controller).build();
	}
	@BeforeEach
	void setupTest() {
		centro = new CentroSanitario("Centro Cita", 2000, 2, 8, 20, "Ciudad Real", "Ciudad Real");
		centroDAO.save(centro);
		p = new Paciente("Cristina eliminarCita", "eliminarcita@gmail.com","Hola1234=".getBytes(), "05724787H", "Paciente", "Centro Cita", "0", "CR", "CR");
		DAO.save(p);
		c = new Cita(1640471862, 1642286262, "Centro Cita", "05724787H", "Cristina eliminarCita");
		citaDAO.save(c);
	}
	
	@SuppressWarnings("deprecation")
	@Test
	/***
	 * Test borrar cita completa correcto
	 * @throws Exception
	 */
	void deleteCitaCompletoCorrecto() throws Exception {
		final ResultActions resultado = mockMvc.perform(MockMvcRequestBuilders.delete("/cita/eliminarCitaCompleta/"+c.getId())
				.contentType(org.springframework.http.MediaType.APPLICATION_JSON_UTF8))
				.andExpect(status().is(200));
	}
	
	/***
	 * Test obtener citas completo
	 * @throws Exception
	 */
	@Test
	void getCitasCompleto() throws Exception { 
		final ResultActions resultado = mockMvc.perform(MockMvcRequestBuilders.get("/cita/getCitaPaciente/"+p.getDni())
				.contentType(org.springframework.http.MediaType.APPLICATION_JSON_UTF8))
				.andExpect(status().is(200));
	}
	
	/***
	 * Test expected error cambio de fecha pasada
	 * @throws Exception
	 */
	@Test
	void expectedErrorFechaPasada() throws Exception {
		JSONObject requestBody = new JSONObject();
		requestBody.put("dni", p.getDni());
		requestBody.put("centrosSanitarios", c.getNombreCentro());
		SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
		Date primeraDate = new Date(1640471862+86400);
		Date segundaDate = new Date(1642286262+86400);
		
		requestBody.put("fechaPrimeraDosis", formatter.format(primeraDate)); //Lo movemos un día adelante
		requestBody.put("fechaSegundaDosis", formatter.format(segundaDate)); //Lo movemos un día adelante
		
		final ResultActions resultado = mockMvc.perform(MockMvcRequestBuilders.put("/cita/modificarCita/"+c.getId())
				.sessionAttr("userEmail", p.getEmail())
				.content(requestBody.toString())
				.contentType(org.springframework.http.MediaType.APPLICATION_JSON_UTF8)
				).andExpect(status().is(409));
				
	}
	
	
	@AfterEach
	public void deleteAll() {
		Paciente p = (Paciente) DAO.findByEmail("eliminarcita@gmail.com");
		if(p!=null) {
			DAO.delete(p);
		}
		centroDAO.delete(centro);
		Cita c = citaDAO.findByDniPaciente("05724787H");
		if(c!=null) 
			citaDAO.delete(c);
	}
}
