package com.vacuna.vacuna.Usuario;

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

import com.jayway.jsonpath.JsonPath;
import com.vacuna.vacuna.VacunaApplication;

import net.minidev.json.JSONObject;
import net.minidev.json.parser.JSONParser;




@ExtendWith(SpringExtension.class)

@SpringBootTest(classes = VacunaApplication.class)
@AutoConfigureMockMvc
public class AddUserApplicationTest {
	@Autowired
	private MockMvc mockMvc;
	
	@Autowired
	private WebApplicationContext controller;
	@BeforeEach
	public void setupMockMvc(){
	    mockMvc = MockMvcBuilders.webAppContextSetup(controller).build();
	}
	
	@Test
	public void savePaciente() throws Exception {
			String jsonStr = "{\"nombre\":\"Lucas\",\"apellidos\":\"Carretero\",\"dni\":\"05724787H\",\"tipoUsuario\":\"paciente\",\"centroAsignado\":\"Hospitalillo\",\"dosisAdministradas\":\"2\",\"localidad\":\"CR\",\"provincia\":\"CR\"}";
			JSONParser parser = new JSONParser(); 
			JSONObject parsedJson = (JSONObject) parser.parse(jsonStr);

			String nombre = parsedJson.getAsString("nombre");
			assertEquals("Lucas", nombre);
			
			String apellidos = parsedJson.getAsString("apellidos");
			assertEquals("Carretero", apellidos);
			
			String dni = parsedJson.getAsString("dni");
			assertEquals("05724787H", dni);
			
			String tipoUsuario = parsedJson.getAsString("tipoUsuario");
			assertEquals("paciente", tipoUsuario);
			
			String centroAsignado = parsedJson.getAsString("centroAsignado");
			assertEquals("Hospitalillo", centroAsignado);
			
			String dosisAdministradas = parsedJson.getAsString("dosisAdministradas");
			assertEquals("2", dosisAdministradas);
			
			String localidad = parsedJson.getAsString("localidad");
			assertEquals("CR", localidad);
			
			String provincia =parsedJson.getAsString("provincia");
			assertEquals("CR", provincia);
			
			MvcResult result = mockMvc.perform(MockMvcRequestBuilders.put("/paciente/add").contentType(org.springframework.http.MediaType.APPLICATION_JSON_UTF8).content(jsonStr.getBytes())).andDo(MockMvcResultHandlers.print()).andReturn();
	}

	@Test
	public void readAll() throws Exception {
		MvcResult result = mockMvc.perform(MockMvcRequestBuilders.get("/paciente/getTodos").contentType(org.springframework.http.MediaType.APPLICATION_JSON_UTF8)).andDo(MockMvcResultHandlers.print()).andReturn();
		
	}
	
	
	@Test
	public void findPaciente() throws Exception {
		String jsonStr = "{\"dni\":\"01724987H\"}";
		JSONParser parser = new JSONParser(); 
		JSONObject parsedJson = (JSONObject) parser.parse(jsonStr);
		
		String dni =parsedJson.getAsString("dni");
		assertEquals("01724987H", dni);
		
		MvcResult result = mockMvc.perform(MockMvcRequestBuilders.get("/paciente/buscarDni/01724987H").contentType(org.springframework.http.MediaType.APPLICATION_JSON_UTF8).content(jsonStr.getBytes())).andDo(MockMvcResultHandlers.print()).andReturn();
		
		
	}
}
