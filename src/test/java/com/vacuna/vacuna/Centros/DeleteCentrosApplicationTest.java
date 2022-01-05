package com.vacuna.vacuna.Centros;

import static org.junit.jupiter.api.Assertions.assertEquals;

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
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import com.vacuna.vacuna.VacunaApplication;
import com.vacuna.vacuna.dao.CentroSanitarioDAO;
import com.vacuna.vacuna.dao.UsuarioDAO;
import com.vacuna.vacuna.model.CentroSanitario;
import com.vacuna.vacuna.model.Usuario;
/***
 * 
 * @author crist
 *
 */

@ExtendWith(SpringExtension.class)

@SpringBootTest(classes = VacunaApplication.class)
@AutoConfigureMockMvc()
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@TestInstance(Lifecycle.PER_CLASS)
class DeleteCentrosApplicationTest {
	@Autowired
	private MockMvc mockMvc;
	@Autowired
	private CentroSanitarioDAO DAO;
	private CentroSanitario centro;
	private CentroSanitario centro1;
	@Autowired
	private UsuarioDAO DAOUser;
	private Usuario usuario;
	
	@Autowired
	private WebApplicationContext controller;
	@BeforeEach
	public void setupMockMvc(){
	    mockMvc = MockMvcBuilders.webAppContextSetup(controller).build();
	}
	
	
	 
	@BeforeAll
	void setUpTest() {
		centro = new CentroSanitario("Centro Prueba 1", 2, 300, 8, 20, "Ciudad Real", "Ciudad Real");
		DAO.save(centro);
		
		centro1 = new CentroSanitario("Centro Prueba 2", 2, 300, 8, 20, "Ciudad Real", "Ciudad Real");
		DAO.save(centro1);
		
		usuario = new  Usuario("usuarioPrueba","usuarioPruebaCentros@gmail.com","Hola1236=".getBytes(),"05724787H","Sanitario",centro1.getNombre());
		DAOUser.save(usuario);
	}

	
	@Test
	@Order(1)
	/***
	 * Test borrar un centro correctamente
	 * @throws Exception
	 */
	void deleteCentroCorrecto() throws Exception {
		MvcResult result = mockMvc.perform(MockMvcRequestBuilders.delete("/centro/eliminarCentro/"+centro.getId())
				.contentType(org.springframework.http.MediaType.APPLICATION_JSON_UTF8))
				.andDo(MockMvcResultHandlers.print()).andReturn();
		assertEquals(200, result.getResponse().getStatus());
	}

	@Test
	@Order(2)
	/***
	 * Test Expected error al borrar un centro que no existe
	 * @throws Exception
	 */
	void expectedErrorDeleteCentroInexistente() throws Exception {
		MvcResult result1 = mockMvc.perform(MockMvcRequestBuilders.delete("/centro/eliminarCentro/"+centro.getId())
				.contentType(org.springframework.http.MediaType.APPLICATION_JSON_UTF8))
				.andDo(MockMvcResultHandlers.print()).andReturn();
		assertEquals(409, result1.getResponse().getStatus());
	}

	@Test
	@Order(3)
	/***
	 * Test Expected error al borrar un centro que esta asignado a usuarios
	 * @throws Exception
	 */
	void expectedErrorDeleteCentroConUsuarios() throws Exception {
		MvcResult result1 = mockMvc.perform(MockMvcRequestBuilders.delete("/centro/eliminarCentro/"+centro1.getId())
				.contentType(org.springframework.http.MediaType.APPLICATION_JSON_UTF8))
				.andDo(MockMvcResultHandlers.print()).andReturn();
		assertEquals(409, result1.getResponse().getStatus());
	}
	
	
	@AfterAll
	void finishTest() {
		DAO.delete(centro);
		DAO.delete(centro1);
		DAOUser.delete(usuario);
	} 

}
