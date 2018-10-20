package server.controller;

import java.util.HashMap;
import java.util.List;

import cliente.Cliente;
import dispositivo.Dispositivo;
import repositorio.RepoClientes;
import server.login.Autenticable;
import simplex.OptimizadorUsoDispositivos;
import spark.ModelAndView;
import spark.Request;
import spark.Response;

import org.apache.commons.math3.util.Pair;

public class ControllerCliente extends ControllerLogin{

	public ModelAndView home(Request req, Response res) {
		
		HashMap<String, Object> viewModel = new HashMap<>();
		
		Cliente cliente = obtenerClienteDe(req);
		
		// TODO: fijarse que funcione eleven y odd en la home del cliente
		viewModel = obtenerElementosDeCliente(cliente);		
		
		return new ModelAndView(viewModel, "cliente/home.hbs");
	}
	
	public ModelAndView optimizarUso(Request req, Response res) {
		Cliente cliente = obtenerClienteDe(req);
		OptimizadorUsoDispositivos optimizador = new OptimizadorUsoDispositivos(cliente);
		
		List<Pair<Dispositivo, Double>> horasOptimas = optimizador.optimizarUsoDispositivos();
		
		HashMap<String, Object> viewModel = obtenerElementosDeCliente(cliente);
		
		viewModel.put("horasOptimas", horasOptimas);
		
		return new ModelAndView(viewModel, "cliente/home.hbs");
	}
	
	public HashMap<String, Object> obtenerElementosDeCliente(Cliente cliente){
		HashMap<String, Object> viewModel = new HashMap<>();
		
		//TODO deberiamos crear un helper para mostrar el consumo actual sin hacer esto ni cambiar consumoActual() por getConsumoActual()
		viewModel.put("cliente", cliente);
		viewModel.put("consumoActual", cliente.consumoActual());
		viewModel.put("tieneDispositivos", cliente.cantidadDispositivos() > 0);
		
		return viewModel;
	}

	@Override
	protected String home() {
		return "cliente";
	}

	public String nombreCookieId() {
		return "idCliente";
	}

	protected Autenticable obtenerAutenticable(String username, String password) {
		return RepoClientes.getInstance().dameAutenticable(username, password);
	}
	
	private Cliente obtenerClienteDe(Request req) {
		return RepoClientes.getInstance().dameCliente(Long.parseLong(req.cookie(nombreCookieId())));
	}
	
}
