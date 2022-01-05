package com.vacuna.vacuna.Usuario;

import static org.junit.jupiter.api.Assertions.assertEquals;

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
import com.vacuna.vacuna.dao.UsuarioDAO;
import com.vacuna.vacuna.model.Usuario;
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
class DeleteUserApplicationTest {
	

	@Autowired
	private MockMvc mockMvc;
	@Autowired
	private WebApplicationContext controller;
	@Autowired
	private UsuarioDAO DAO;
	private Usuario usuario;
	
	@BeforeEach
	public void setupMockMvc(){
		   mockMvc = MockMvcBuilders.webAppContextSetup(controller).build();
	}
	@BeforeAll
	public void setUpTest() {
		usuario = new Usuario("Cristina", "Cristina@gmail.com", "Hola1234=".getBytes(), "05724787H", "Sanitario", "Hospital General");
		DAO.save(usuario);
	}

	@Test
	@Order(1)
	/***
	 * 
	 * @throws Exception
	 */
	void deleleUsuarioCorrecto() throws Exception {
		MvcResult result = mockMvc.perform(MockMvcRequestBuilders.delete("/Usuario/eliminarUsuario/"+usuario.getEmail()).contentType(org.springframework.http.MediaType.APPLICATION_JSON_UTF8)).andDo(MockMvcResultHandlers.print()).andReturn();
		assertEquals(200, result.getResponse().getStatus());
	}


	@Test
	@Order(2)
	/***
	 * 
	 * @throws Exception
	 */
	void expectedErrorUsuarioNoExiste() throws Exception {
		MvcResult result1 = mockMvc.perform(MockMvcRequestBuilders.delete("/Usuario/eliminarUsuario/"+usuario.getEmail()).contentType(org.springframework.http.MediaType.APPLICATION_JSON_UTF8)).andDo(MockMvcResultHandlers.print()).andReturn();
		assertEquals(409, result1.getResponse().getStatus());		

	}

	@Test
	@Order(3)
	/***
	 * 
	 * @throws Exception
	 */
	void expectedErrorBorrarAdministrador() throws Exception {
		String jsonStrFallo2 = "{\"tipoUsuario\":\"Administrador\"}";
		MvcResult result2 = mockMvc.perform(MockMvcRequestBuilders.delete("/Usuario/eliminarUsuario/manolo@sigeva.com").contentType(org.springframework.http.MediaType.APPLICATION_JSON_UTF8).content(jsonStrFallo2.getBytes())).andDo(MockMvcResultHandlers.print()).andReturn();
		assertEquals(409, result2.getResponse().getStatus());
	}

	
}
