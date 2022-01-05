package com.vacuna.vacuna.Centros;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.TestInstance.Lifecycle;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import com.vacuna.vacuna.VacunaApplication;
import com.vacuna.vacuna.dao.CentroSanitarioDAO;
import com.vacuna.vacuna.model.CentroSanitario;

import net.minidev.json.JSONObject;
/***
 * 
 * @author crist
 *
 */
@SpringBootTest(classes = VacunaApplication.class)
@AutoConfigureMockMvc()
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@TestInstance(Lifecycle.PER_CLASS)

class AddCentrosApplicationTest {
	@Autowired
	private MockMvc mockMvc;
	
	@Autowired
	private WebApplicationContext controller;

	@Autowired
	private CentroSanitarioDAO DAO;
	
	private String TEST_NOMBRE = "Centro Prueba";
	private int TEST_DOSIS = 2000;
	private int TEST_AFORO = 2;
	private int TEST_HINICIO = 8;
	private int TEST_HFIN = 20;
	private String TEST_LOCALIDAD = "Ciudad Real";
	private String TEST_PROVINCIA = "Ciudad Real";
	@BeforeEach
	
	public void setupMockMvc(){
	    mockMvc = MockMvcBuilders.webAppContextSetup(controller).build();
	}
	
	
	
	@Test 
	@Order(1)
	/***
	 * Test guardar un centro correctamente
	 * @throws Exception
	 */
	void saveCentroCorrecto() throws Exception {
		JSONObject json = new JSONObject();
		json.put("nombre", TEST_NOMBRE);
		json.put("dosisTotales", TEST_DOSIS);
		json.put("aforo", TEST_AFORO);
		json.put("horaInicio", TEST_HINICIO);
		json.put("horaFin", TEST_HFIN);
		json.put("localidad", TEST_LOCALIDAD);
		json.put("provincia", TEST_PROVINCIA);
		System.out.println(json.toJSONString());
		final ResultActions resultado = mockMvc.perform(MockMvcRequestBuilders.put("/centro/add")
				.content(json.toString())
				.contentType(org.springframework.http.MediaType.APPLICATION_JSON_UTF8))
				.andExpect(status().is(200));
	}
	

	
	@Test
	/***
	 * Test excepted error de datos incompletos
	 * @throws Exception
	 */
	void expectedErrorDatosIncompletos() throws Exception {
		JSONObject json = new JSONObject();
		json.put("nombre", TEST_NOMBRE);
		json.put("dosisTotales", TEST_DOSIS);
		json.put("aforo", TEST_AFORO);
		json.put("horaInicio", TEST_HINICIO);
		json.put("horaFin", TEST_HFIN);
		json.put("localidad", "");
		json.put("provincia", "");
		System.out.println(json.toJSONString());
		final ResultActions resultado = mockMvc.perform(MockMvcRequestBuilders.put("/centro/add")
				.content(json.toString())
				.contentType(org.springframework.http.MediaType.APPLICATION_JSON_UTF8))
				.andExpect(status().is(409));
	}

	
	@AfterEach
	/***
	 * Borrar centro creado
	 */
	void deleteAll() {
		CentroSanitario centro = DAO.findByNombre(TEST_NOMBRE);
		if(centro!=null)
			DAO.delete(centro);
	}

}
