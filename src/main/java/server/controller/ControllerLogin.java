package server.controller;

import java.util.HashMap;
import java.util.Optional;

import server.login.RepoUsuarios;
import spark.ModelAndView;
import spark.Request;
import spark.Response;
import usuario.Usuario;

public abstract class ControllerLogin {
	
	public ModelAndView login(Request req, Response res) {
		
		if(estaLogueado(req)) {
			redirigirAHome(res);
			return null;
		}
		
		HashMap<String, Object> viewModel = new HashMap<>();
		viewModel.put("username", req.queryParams("username"));
		return new ModelAndView(viewModel, "login.hbs");
	}

	protected void redirigirAHome(Response res) {
		res.redirect("/" + home());
	}
	
	protected abstract String home();

	//TODO la pass tiene que llegar aca ya hasheada!
	public String validarLogin(Request req, Response res) {		
		String username = req.queryParams("username");
		String password = req.queryParams("password");
		
		Optional<Usuario> usuario = RepoUsuarios.getInstance().dameUsuario(username, password);
		
		if(!usuario.isPresent()) {
			res.redirect("/" + home() + "/login/?username=" + username);
			return null;
		}
		
		res = setearCookies(res, usuario.get().id);
		
		redirigirAHome(res);		
		return null;
	}
	
	protected abstract Usuario autenticar(String username, String password);

	protected Response setearCookies(Response res, long id) {
		res.cookie(nombreCookieId(), String.valueOf(id));
		return res;
	}

	protected abstract String nombreCookieId();

	public void siNoEstaLogueadoEchar(Request req, Response res) {
		if(!estaLogueado(req)) {
			res.redirect("/" + home() + "/login");
		}
	}

	public boolean estaLogueado(Request req) {
		return req.cookie(nombreCookieId()) != null;
	}	
}
