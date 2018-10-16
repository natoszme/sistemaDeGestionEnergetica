package server;

import java.util.HashMap;
import java.util.Optional;

import login.RepoUsuarios;
import spark.ModelAndView;
import spark.Request;
import spark.Response;
import usuario.Usuario;

public class Controller {
	public static ModelAndView login(Request req, Response res) {
		//TODO revisar el null
		return new ModelAndView(null, "index.hbs");
	}
	
	public static ModelAndView validarLogin(Request req, Response res) {		
		Optional<Usuario> usuario = RepoUsuarios.getInstance().dameUsuario(req.attribute("usuario"), req.attribute("password"));
		
		return usuario.map(user -> modelAndViewSegun(user))
				.orElse(new ModelAndView(null, "index.hbs"));
	}

	private static ModelAndView modelAndViewSegun(Usuario user) {
		HashMap<String, Object> viewModel = new HashMap<>();
		
		return new ModelAndView(viewModel.put("usuario", user.getUser()),
				obtenerViewConPermisoDe(user));
	}

	private static String obtenerViewConPermisoDe(Usuario user) {
		String recurso;
		if(user.esAdmin()) {
			recurso = "admin";
		}
		recurso = "usuario";
		return recurso + "/home.hbs";
	}
}
