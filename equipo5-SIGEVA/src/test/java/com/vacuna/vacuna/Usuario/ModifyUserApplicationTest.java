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

import com.vacuna.vacuna.VacunaApplication;
import com.vacuna.vacuna.model.Paciente;

import net.minidev.json.JSONObject;
import net.minidev.json.parser.JSONParser;

import com.google.gson.Gson; 

@ExtendWith(SpringExtension.class)

@SpringBootTest(classes = VacunaApplication.class)
@AutoConfigureMockMvc
public class ModifyUserApplicationTest {
	@Autowired
	private MockMvc mockMvc;
	@Autowired
	private WebApplicationContext controller;
		
	@BeforeEach
	public void setupMockMvc(){
		   mockMvc = MockMvcBuilders.webAppContextSetup(controller).build();
	}
	
	@Test
	public void modificarPaciente() throws Exception {
		String jsonStr = "{\"dni\":\"71555585H\"}";
		JSONParser parser = new JSONParser(); 
		JSONObject parsedJson = (JSONObject) parser.parse(jsonStr);
		
		String dni =parsedJson.getAsString("dni");
		assertEquals("71555585H", dni);
		
		MvcResult result = mockMvc.perform(MockMvcRequestBuilders.get("/paciente/buscarDni/71555585H").contentType(org.springframework.http.MediaType.APPLICATION_JSON_UTF8).content(jsonStr.getBytes())).andDo(MockMvcResultHandlers.print()).andReturn();
		
		String content = result.getResponse().getContentAsString();
		JSONObject parsedContent = (JSONObject) parser.parse(content);
		
		String nombre = parsedContent.getAsString("nombre");
		String apellidos = parsedContent.getAsString("apellidos");
		String tipoUsuario = parsedContent.getAsString("tipoUsuario");
		String centroAsignado = parsedContent.getAsString("centroAsignado");
		String dosisAdministradas = parsedContent.getAsString("dosisAdministradas");
		String localidad = parsedContent.getAsString("localidad");
		String provincia =parsedContent.getAsString("provincia");
		
		Paciente p = new Paciente(nombre, apellidos, dni, tipoUsuario, centroAsignado,
				dosisAdministradas, localidad, provincia);
		
		p.setNombre(nombre);
		p.setApellidos("Garcia");
		p.setTipoUsuario(tipoUsuario);
		p.setCentroAsignado("Hola");
		p.setDosisAdministradas(dosisAdministradas);
		p.setLocalidad(localidad);
		p.setProvincia(provincia);
		
		Gson gson = new Gson();
		String json = gson.toJson(p);
		
		MvcResult cambios = mockMvc.perform(MockMvcRequestBuilders.put("/paciente/modificarUsuarios").contentType(org.springframework.http.MediaType.APPLICATION_JSON_UTF8)
				.content(json.getBytes())).andDo(MockMvcResultHandlers.print()).andReturn();
	}

}
