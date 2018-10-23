package server.controller;

import java.util.HashMap;
import server.login.Autenticable;
import spark.ModelAndView;
import spark.Request;
import spark.Response;

public abstract class ControllerLogin {
	
	public ModelAndView login(Request req, Response res) {
		
		if(estaLogueado(req)) {
			redirigirAHome(res);
			return null;
		}
		
		HashMap<String, Object> viewModel = new HashMap<>();
		viewModel.put("username", req.cookie("username"));
		
		return new ModelAndView(viewModel, "login.hbs");
	}

	protected void redirigirAHome(Response res) {
		res.redirect("/" + home() + "/home");
	}
	
	protected abstract String home();

	//TODO la pass tiene que llegar aca ya hasheada!
	public String validarLogin(Request req, Response res) {		
		String username = req.queryParams("username");
		String password = req.queryParams("password");
		
		Autenticable aut = obtenerAutenticable(username, password);
		
		if(aut == null) {
			res.cookie("username", username);
			res.redirect("/" + home() + "/login");
			return null;
		}
		
		res.removeCookie("username");
		res = setearCookies(res, aut.id());
		
		redirigirAHome(res);		
		return null;
	}

	protected abstract Autenticable obtenerAutenticable(String username, String password);

	protected Response setearCookies(Response res, long id) {
		res.cookie(nombreCookieId(), String.valueOf(id));
		return res;
	}

	public abstract String nombreCookieId();

	public void siNoEstaLogueadoEchar(Request req, Response res) {
		if(!estaLogueado(req)) {
			res.redirect("/" + home() + "/login");
		}
	}

	public boolean estaLogueado(Request req) {
		return req.cookie(nombreCookieId()) != null;
	}
	
	public String logout(Request req, Response res) {
		res.removeCookie(nombreCookieId());
		redirigirAHome(res);
		return null;
	}
}
