package com.vacuna.vacuna.Usuario;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.regex.Pattern;

import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.TestMethodOrder;
import org.junit.jupiter.api.TestInstance.Lifecycle;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import com.vacuna.vacuna.VacunaApplication;
import com.vacuna.vacuna.exception.PasswordIncorrectaException;
@ExtendWith(SpringExtension.class)

@SpringBootTest(classes = VacunaApplication.class)
@AutoConfigureMockMvc
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@TestInstance(Lifecycle.PER_CLASS)
class ComprobarPasswordApplicationTest {
	public  boolean validarPassword(String password) throws PasswordIncorrectaException {
		boolean valido = true;
		String regex = "^.*(?=.{8,})(?=..*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[@#$%^&+=]).*$";
		Pattern pattern = Pattern.compile(regex);
		if(!pattern.matcher(password).matches()) {
			throw new PasswordIncorrectaException();
		}
		/*if(!password.matches(regex)) {
			throw new PasswordIncorrectaException(); 
			
		}*/
		return valido;
	}
	
	@Test
	/***
	 * 
	 * @throws PasswordIncorrectaException
	 */
	void Comprobar() throws PasswordIncorrectaException {
		try {
			validarPassword("Adios12");
			
		}catch(PasswordIncorrectaException e){
		}
		assertTrue(validarPassword("Hola1234="));
		assertTrue(validarPassword("Hola12389="));
	}
}
