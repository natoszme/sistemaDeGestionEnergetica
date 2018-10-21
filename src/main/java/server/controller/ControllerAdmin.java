package server.controller;

import java.util.HashMap;

import server.login.Autenticable;
import server.login.RepoAdmins;
import spark.ModelAndView;
import spark.Request;
import spark.Response;

public class ControllerAdmin extends ControllerLogin{

	public ModelAndView home(Request req, Response res) {
		
		HashMap<String, Object> viewModel = new HashMap<>();
		
		//viewModel.put("consumos", //lo que devuelva el reporte);
		
		return new ModelAndView(viewModel, "admin/home.hbs");
	}

	protected String home() {
		return "admin";
	}

	public String nombreCookieId() {
		return "idAdmin";
	}
	
	protected Autenticable obtenerAutenticable(String username, String password) {
		return RepoAdmins.getInstance().dameAutenticable(username, password);
	}
}
