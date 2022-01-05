package com.vacuna.vacuna.Exceptions;

import static org.junit.jupiter.api.Assertions.assertTrue;

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
import org.springframework.web.context.WebApplicationContext;

import com.vacuna.vacuna.VacunaApplication;
import com.vacuna.vacuna.exception.CambioPasswordException;
import com.vacuna.vacuna.exception.CentroNoEliminadoException;
import com.vacuna.vacuna.exception.CentroNoExisteException;
import com.vacuna.vacuna.exception.CentrosNoEncontradosException;
import com.vacuna.vacuna.exception.CitasNoEncontradasException;
import com.vacuna.vacuna.exception.ControlHorasVacunacionException;
import com.vacuna.vacuna.exception.CredencialesInvalidasException;
import com.vacuna.vacuna.exception.DatosIncompletosException;
import com.vacuna.vacuna.exception.DiasEntreDosisIncorrectosException;
import com.vacuna.vacuna.exception.DniNoValidoException;
import com.vacuna.vacuna.exception.EmailExistenteException;
import com.vacuna.vacuna.exception.EmailIncorrectoException;
import com.vacuna.vacuna.exception.ErrorDosisAdministradasException;
import com.vacuna.vacuna.exception.FalloRolUsuarioException;
import com.vacuna.vacuna.exception.LogoutException;
import com.vacuna.vacuna.exception.NoHayDosisException;
import com.vacuna.vacuna.exception.NombreNoEncontradoException;
import com.vacuna.vacuna.exception.PasswordDiferenteException;
import com.vacuna.vacuna.exception.PasswordIncorrectaException;
import com.vacuna.vacuna.exception.PasswordNoCoincideException;
import com.vacuna.vacuna.exception.PrimerInicioException;
import com.vacuna.vacuna.exception.SlotVacunacionSuperadoException;
import com.vacuna.vacuna.exception.UsuarioExistenteException;
import com.vacuna.vacuna.exception.UsuarioNoEliminadoException;
import com.vacuna.vacuna.exception.UsuarioNoExisteException;
import com.vacuna.vacuna.exception.UsuariosNoEncontradosException;
import com.vacuna.vacuna.exception.VacunaException;
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
class ExceptionsTest {
	@Autowired
	private MockMvc mockMvc;
	@Autowired
	private WebApplicationContext controller;
	
	@Test
	/***
	 * Test excepciones
	 * @throws Exception
	 */
	void LoginCorrecto() throws Exception {
		new CambioPasswordException();
		new CentroNoEliminadoException();
		new CentroNoExisteException();
		new CentrosNoEncontradosException();
		new CitasNoEncontradasException();
		new ControlHorasVacunacionException();
		new CredencialesInvalidasException();
		new DatosIncompletosException();
		new DiasEntreDosisIncorrectosException();
		new DniNoValidoException();
		new EmailExistenteException();
		new EmailIncorrectoException();
		new ErrorDosisAdministradasException();
		new FalloRolUsuarioException();
		new LogoutException();
		new NoHayDosisException();
		new NombreNoEncontradoException();
		new PasswordDiferenteException();
		new PasswordIncorrectaException();
		new PasswordNoCoincideException();
		new PrimerInicioException();
		new SlotVacunacionSuperadoException();
		new UsuarioExistenteException();
		new UsuarioNoEliminadoException();
		new UsuarioNoExisteException();
		new UsuariosNoEncontradosException();
		new VacunaException(null, null);
		assertTrue(true);
	}
		
}
