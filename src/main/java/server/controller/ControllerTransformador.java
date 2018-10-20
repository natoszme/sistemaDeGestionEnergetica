package server.controller;

import java.util.HashMap;
import java.util.List;

import cliente.Cliente;
import dispositivo.Dispositivo;
import repositorio.RepoClientes;
import repositorio.RepoConsumoEnFecha;
import repositorio.RepoTransformadores;
import server.login.Autenticable;
import simplex.OptimizadorUsoDispositivos;
import spark.ModelAndView;
import spark.Request;
import spark.Response;
import tipoDispositivo.ConsumoEnFecha;
import transformador.Transformador;

import org.apache.commons.math3.util.Pair;

public class ControllerTransformador {
	
	public ModelAndView home(Request req, Response res) {
		
		HashMap<String, Object> viewModel = new HashMap<>();
		
		viewModel.put("transformadores", RepoTransformadores.getInstance().obtenerTodas());
		
		return new ModelAndView(viewModel, "transformador/home.hbs");
	}
}
