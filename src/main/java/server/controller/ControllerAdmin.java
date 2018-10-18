package server.controller;

import java.util.HashMap;
import java.util.Optional;

import spark.ModelAndView;
import spark.Request;
import spark.Response;
import usuario.Usuario;

public class ControllerAdmin extends ControllerLogin{

	public static ModelAndView adminHome(Request req, Response res) {
		
		if(!estaLogueado()) {
			res.redirect("/cliente");
			return null;
		}
		
		HashMap<String, Object> viewModel = new HashMap<>();
		
		//viewModel.put("consumos", //lo que devuelva el reporte);
		
		return new ModelAndView(viewModel, "admin/home.hbs");
	}

	protected String home() {
		return "admin";
	}

	@Override
	protected Usuario autenticar(String username, String password) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	protected String nombreCookieId() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public boolean estaLogueado(Request req) {
		// TODO Auto-generated method stub
		return false;
	}

}
