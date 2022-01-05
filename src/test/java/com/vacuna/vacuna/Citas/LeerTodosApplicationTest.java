package com.vacuna.vacuna.Citas;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
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
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
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
class LeerTodosApplicationTest {
	@Autowired
	private MockMvc mockMvc;
	@Autowired
	private CitaDAO citaDAO;
	private Cita c;
	@Autowired
	private UsuarioDAO DAO;
	private Paciente p;
	@Autowired
	private CentroSanitarioDAO centroDAO;
	private CentroSanitario centro;
	@Autowired
	private WebApplicationContext controller;
	@BeforeEach
	public void setupMockMvc(){
	    mockMvc = MockMvcBuilders.webAppContextSetup(controller).build();
	}
	
	@BeforeAll
	public void setupTest() {
		centro = new CentroSanitario("Centro Cita 1", 2000, 2, 8, 20, "Ciudad Real", "Ciudad Real");
		centroDAO.save(centro);
		p = new Paciente("Cristina eliminarCita", "eliminarcita@gmail.com","Hola1234=".getBytes(), "05724787H", "Paciente", "Centro Cita", "0", "CR", "CR");
		DAO.save(p);
		c = new Cita(1640471862, 1642286262, "Centro Cita", "05724787H", "Cristina eliminarCita");
		citaDAO.save(c);
	}
	
	/***
	 * Test leer citas correcto
	 * @throws Exception
	 */
	@Test
	void readCitas() throws Exception {
		MvcResult result = mockMvc.perform(MockMvcRequestBuilders.get("/cita/getTodos").contentType(org.springframework.http.MediaType.APPLICATION_JSON_UTF8)).andDo(MockMvcResultHandlers.print()).andReturn();
		assertEquals(200, result.getResponse().getStatus());
	}
	
	/***
	 * Expected error test leer centros incorrecto
	 * @throws Exception
	 */
	@Test
	void readCentrosIncorrecto() throws Exception {
		Paciente p = (Paciente) DAO.findByEmail("eliminarcita@gmail.com");
		CentroSanitario cs = (CentroSanitario) centroDAO.findByNombre("Centro Prueba 1");
		
		MvcResult result = mockMvc.perform(MockMvcRequestBuilders.get("/cita/getCentroSanitario/eliminarcita@gmail.com").contentType(org.springframework.http.MediaType.APPLICATION_JSON_UTF8)).andDo(MockMvcResultHandlers.print()).andReturn();
		assertEquals(409, result.getResponse().getStatus());
	}

	
	@AfterAll
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
