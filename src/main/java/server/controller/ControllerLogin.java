package server.controller;

import java.util.HashMap;
import java.util.Optional;

import login.RepoUsuarios;
import spark.ModelAndView;
import spark.Request;
import spark.Response;
import usuario.Usuario;

public class ControllerLogin {
	
	public static ModelAndView login(Request req, Response res) {
		
		if(estaLogueado(req)) {
			res.redirect("/" + tipoUsuario(req));
			return null;
		}
		
		//TODO como hacer para mostrar username en caso de login fallido? Entra a este metodo (get) despues de haber pasado por post
		//pero ya sin los datos del form
		HashMap<String, Object> viewModel = new HashMap<>();
		viewModel.put("username", req.queryParams("username"));
		return new ModelAndView(viewModel, "index.hbs");
	}

	//TODO la pass tiene que llegar aca ya hasheada!
	//que deberia devolver?
	public static String validarLogin(Request req, Response res) {		
		String username = req.queryParams("username");
		String password = req.queryParams("password");
		
		Optional<Usuario> usuario = RepoUsuarios.getInstance().dameUsuario(username, password);
		
		String recurso = usuario.map(user -> tipoUsuarioDe(user))
				.orElse("/?username=" + username);
		
		usuario.ifPresent(user -> {
				req.session(true);
				res.cookie("id", String.valueOf(user.id));
				res.cookie("tipoUsuario", tipoUsuarioDe(user));
			});
		
		res.redirect(recurso);
		
		return "200";
	}
	
	public static void siNoEstaLogueadoEchar(Request req, Response res) {
		if(!estaLogueado(req)) {
			res.redirect("/");
		}
	}

	public static boolean estaLogueado(Request req) {
		return req.cookie("id") != null;
	}
	
	public static String tipoUsuario(Request req) {
		return req.cookie("tipoUsuario");
	}

	private static String tipoUsuarioDe(Usuario user) {
		String recurso = "cliente";
		if(user.esAdmin()) {
			recurso = "admin";
		}
		return recurso;
	}
	
	//TODO mejor forma de manejar la sesion?
	public static String logout(Request req, Response res) {
		res.removeCookie("id");
		res.removeCookie("tipoUsuario");
		
		res.redirect("/");
		
		return "200";
	}
}
