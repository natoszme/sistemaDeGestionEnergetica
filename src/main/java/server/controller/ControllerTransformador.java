package server.controller;

import java.util.HashMap;
import repositorio.RepoTransformadores;
import spark.ModelAndView;
import spark.Request;
import spark.Response;

public class ControllerTransformador {
	
	public ModelAndView home(Request req, Response res) {
		
		HashMap<String, Object> viewModel = new HashMap<>();
		
		viewModel.put("transformadores", RepoTransformadores.getInstance().obtenerTodas());
		
		return new ModelAndView(viewModel, "transformador/home.hbs");
	}
}
