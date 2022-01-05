package com.vacuna.vacuna.Usuario;

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


@ExtendWith(SpringExtension.class)

@SpringBootTest(classes = VacunaApplication.class)
@AutoConfigureMockMvc

public class DeleteUserApplicationTest {
	

	@Autowired
	private MockMvc mockMvc;
	@Autowired
	private WebApplicationContext controller;
		
	@BeforeEach
	public void setupMockMvc(){
		   mockMvc = MockMvcBuilders.webAppContextSetup(controller).build();
	}
		
	@Test
	public void delelePaciente() throws Exception {
			String jsonStr = "{\"dni\":\"41555585H\"}";
			MvcResult result = mockMvc.perform(MockMvcRequestBuilders.delete("/paciente/eliminarUsuario/41555585H").contentType(org.springframework.http.MediaType.APPLICATION_JSON_UTF8).content(jsonStr.getBytes())).andDo(MockMvcResultHandlers.print()).andReturn();
	}
	
}
