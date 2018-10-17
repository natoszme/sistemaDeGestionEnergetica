package server.controller;

import java.util.HashMap;

import spark.ModelAndView;
import spark.Request;
import spark.Response;

public class ControllerAdmin {

	public static ModelAndView adminHome(Request req, Response res) {
		
		if(!ControllerLogin.tipoUsuario(req).equals("admin")) {
			res.redirect("/cliente");
			return null;
		}
		
		HashMap<String, Object> viewModel = new HashMap<>();
		
		//viewModel.put("consumos", //lo que devuelva el reporte);
		
		return new ModelAndView(viewModel, "admin/home.hbs");
	}

}
