package com.vacuna.vacuna.controller;

import java.security.MessageDigest;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.vacuna.vacuna.dao.UsuarioDAO;
import com.vacuna.vacuna.exception.CambioPasswordException;
import com.vacuna.vacuna.exception.CredencialesInvalidasException;
import com.vacuna.vacuna.exception.FalloRolUsuarioException;
import com.vacuna.vacuna.exception.LogoutException;
import com.vacuna.vacuna.exception.NombreNoEncontradoException;
import com.vacuna.vacuna.exception.PasswordDiferenteException;
import com.vacuna.vacuna.exception.PasswordIncorrectaException;
import com.vacuna.vacuna.exception.PasswordNoCoincideException;
import com.vacuna.vacuna.exception.PrimerInicioException;
import com.vacuna.vacuna.exception.UsuarioNoExisteException;
import com.vacuna.vacuna.model.Paciente;
import com.vacuna.vacuna.model.Sanitario;
import com.vacuna.vacuna.model.Usuario;

@RestController
/***
 * 
 * @author crist
 *
 */
@RequestMapping("login")
public class LoginController { 

	@Autowired 
	private UsuarioDAO userRepository;
	 
	private String userEmail = "userEmail";   
	private String pacient= "Paciente";  
	private String denegado = "denegado";  
	private String aprobado = "aprobado";  
	private String regex = "^.*(?=.{8,})(?=..*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[@#$%^&+=]).*$";
	
	@PostMapping("/login")
	/***
	 * Metodo que controla el inicio de sesion 
	 * @param request
	 * @param info
	 * @return usuario logeado
	 * @throws CredencialesInvalidasException
	 */
	public Usuario login(HttpServletRequest request, @RequestBody Map<String, Object> info) throws CredencialesInvalidasException {
		try { 
			JSONObject jso = new JSONObject(info);
			String email = jso.optString("email");
			if (email.length()==0)
				throw new NombreNoEncontradoException(); 
			String password= jso.optString("password");
			MessageDigest md = MessageDigest.getInstance("SHA-256"); 
			byte[] pwd = md.digest(password.getBytes());
			Usuario user = userRepository.findByEmailAndPassword(email,pwd);
			request.getSession().setAttribute(userEmail, email);
			if (user==null)
				throw new CredencialesInvalidasException(); 
			request.getSession().setAttribute(userEmail, email);
			return user;
		} catch (Exception e) { 
			throw new CredencialesInvalidasException();
		}
	} 

	
	
	@GetMapping("/comprobarRolPaciente")
	/***
	 * Comprobamos que el usuario que inicia sesion es un paciente
	 * @param request
	 * @return rol paciente
	 * @throws FalloRolUsuarioException
	 */
	public @ResponseBody String comprobarRolPaciente(HttpServletRequest request) throws FalloRolUsuarioException {
		try {
			String email = (String) request.getSession().getAttribute(userEmail);
			Usuario u = userRepository.findByEmail(email);
			if(u == null || !u.getTipoUsuario().equalsIgnoreCase(pacient)) {
				return denegado;
			}
		} catch (Exception e) {
			throw new FalloRolUsuarioException();
		}
		return aprobado;
	}

	
	@GetMapping("/comprobarRolSanitario")
	/***
	 * Comprobamos que el usuario que inicia sesion es un sanitario
	 * @param request
	 * @return rol sanitario
	 * @throws FalloRolUsuarioException
	 */
	public @ResponseBody String comprobarRolSanitario(HttpServletRequest request) throws FalloRolUsuarioException {
		try {
			String email = (String) request.getSession().getAttribute(userEmail);
			Usuario u = userRepository.findByEmail(email);
			if(!(u instanceof Sanitario)) {
				return denegado;
			}
		} catch (Exception e) {
			throw new FalloRolUsuarioException();
		}
		return aprobado;
	}

	
	@GetMapping("/comprobarRolAdmin")
	/***
	 * Comprobamos que el usuario que inicia sesion es un administrador
	 * @param request
	 * @return rol administrador
	 * @throws FalloRolUsuarioException
	 */
	public @ResponseBody String comprobarRolAdmin(HttpServletRequest request) throws FalloRolUsuarioException {
		try {
			String email = (String) request.getSession().getAttribute(userEmail);
			Usuario u = userRepository.findByEmail(email);
			if(u == null || !u.getTipoUsuario().equalsIgnoreCase("Administrador")) {
				return denegado;
			}
		} catch (Exception e) {
			throw new FalloRolUsuarioException();
		} 
		return aprobado;
	}
	
	@GetMapping("/getUser")
	/***
	 * 
	 * @param request
	 * @return usuario
	 * @throws UsuarioNoExisteException
	 */
	public List<String> devolverUsuario(HttpServletRequest request) throws UsuarioNoExisteException {
		Usuario usuario = null;
		List<String> user = new ArrayList<>();
		try {
			if (request.getSession().getAttribute(userEmail) != null) {
				usuario = userRepository.findByEmail(request.getSession().getAttribute(userEmail).toString());
				user.add(usuario.getNombre());
				user.add(usuario.getTipoUsuario());
				user.add(usuario.getEmail());
				user.add(usuario.getCentroAsignado());
				user.add(usuario.getDni());
				if(usuario instanceof Paciente)
					user.add(((Paciente)usuario).getDosisAdministradas());
			}
		} catch (Exception e) {
			throw new UsuarioNoExisteException();
		}
		return user;
	}


	
	@PostMapping("/logout")
	/***
	 * Cerrar sesion
	 * @param request
	 * @throws LogoutException
	 */
	public void logout(HttpServletRequest request) throws LogoutException {
		try {
			request.getSession().removeAttribute(userEmail);
		} catch (Exception e) {
			throw new LogoutException();
		}
	}
	
	
	@GetMapping("/primerInicio")
	/***
	 * Comprobamos si el usuario inicia sesion por primera vez
	 * @param request
	 * @return 1
	 * @return 0
	 * @throws PrimerInicioException
	 */
	public String primerInicio(HttpServletRequest request) throws PrimerInicioException {
		try {
			String email = request.getSession().getAttribute(userEmail).toString();
			Usuario u = userRepository.findByEmail(email);
			if(u.getTipoUsuario().equalsIgnoreCase(pacient) ) {
				Paciente p = (Paciente) u;
				if(p.isPrimeraVez()) {	
					return "1";
				}
			}
			if(u.getTipoUsuario().equalsIgnoreCase("Sanitario") ) {
				Sanitario p = (Sanitario) u;
				if(p.isPrimeraVez()) {
					return "1";
				}
			} 
		} catch (Exception e) {
			throw new PrimerInicioException();
		}
		return "0";
	}

	
	
	@PostMapping("/cambiarPassword")
	/***
	 * modificamos password
	 * @param request
	 * @param info
	 * @return password cambiada
	 * @throws CambioPasswordException
	 */
	public String cambiarPassword(HttpServletRequest request,@RequestBody Map<String, Object> info) throws CambioPasswordException {
		try {
			MessageDigest md = MessageDigest.getInstance("SHA-256");
			JSONObject jso = new JSONObject(info);
			String email = request.getSession().getAttribute(userEmail).toString();
			Usuario u = userRepository.findByEmail(email);
			String password = jso.optString("password");
			String password2 = jso.optString("password2");
			if (!password.equals(password2))
				throw new PasswordNoCoincideException();
			byte[] pwd = md.digest(password.getBytes());
			if(Arrays.equals(pwd, u.getPassword()))
				throw new PasswordDiferenteException();
			validarPassword(password);
			if(u.getTipoUsuario().equalsIgnoreCase(pacient) ) {
				Paciente p = (Paciente) u;
				p.setPassword(pwd);
				p.setPrimeraVez(false);
				userRepository.save(p);
			}
			if(u.getTipoUsuario().equalsIgnoreCase("Sanitario") ) {
				Sanitario s = (Sanitario) u;
				s.setPassword(pwd);
				s.setPrimeraVez(false);
				userRepository.save(s);
			} 
		} catch (Exception e) {
			throw new CambioPasswordException();
		}
		return null; 
	}
	
	
	/***
	 * Comprobamos que la composicion de la contraseña es correcta segun nuestra seguridad de contraseñas
	 * @param password
	 * @return true
	 * @throws PasswordIncorrectaException
	 */
	public boolean validarPassword(String password) throws PasswordIncorrectaException {
		if(!password.matches(regex)) {
			throw new PasswordIncorrectaException();
		}
		return true;
	}
	
}