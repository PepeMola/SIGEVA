package com.vacuna.vacuna.Centros;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
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
/***
 * 
 * @author crist
 *
 */

@ExtendWith(SpringExtension.class)

@SpringBootTest(classes = VacunaApplication.class)
@AutoConfigureMockMvc
class ReadCentrosApplicationTest {
	@Autowired
	private MockMvc mockMvc;
	
	@Autowired
	private WebApplicationContext controller;
	@BeforeEach
	public void setupMockMvc(){
	    mockMvc = MockMvcBuilders.webAppContextSetup(controller).build();
	}

	
	@Test
	/***
	 * Test leer todos los centros correctamente
	 * @throws Exception
	 */
	void readAll() throws Exception {
		MvcResult result = mockMvc.perform(MockMvcRequestBuilders.get("/centro/getTodos").contentType(org.springframework.http.MediaType.APPLICATION_JSON_UTF8)).andDo(MockMvcResultHandlers.print()).andReturn();
		assertEquals(200, result.getResponse().getStatus());
	}

}
