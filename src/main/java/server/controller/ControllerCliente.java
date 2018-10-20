package server.controller;

import java.util.HashMap;
import java.util.List;

import cliente.Cliente;
import dispositivo.Dispositivo;
import repositorio.RepoClientes;
import repositorio.RepoConsumoEnFecha;
import server.login.Autenticable;
import simplex.OptimizadorUsoDispositivos;
import spark.ModelAndView;
import spark.Request;
import spark.Response;
import tipoDispositivo.ConsumoEnFecha;

import org.apache.commons.math3.util.Pair;

public class ControllerCliente extends ControllerLogin {

	List<Pair<Dispositivo, Double>> horasOptimas;
	List<ConsumoEnFecha> mediciones;
	
	public ModelAndView home(Request req, Response res) {
		
		HashMap<String, Object> viewModel = new HashMap<>();
		
		Cliente cliente = obtenerClienteDe(req);
		
		viewModel = obtenerElementosDeCliente(cliente);
		
		return new ModelAndView(viewModel, "cliente/home.hbs");
	}
	
	public ModelAndView optimizarUso(Request req, Response res) {
		Cliente cliente = obtenerClienteDe(req);
		
		OptimizadorUsoDispositivos optimizador = new OptimizadorUsoDispositivos(cliente);		
		horasOptimas = optimizador.optimizarUsoDispositivos();
		
		return this.home(req, res);
	}
	
	public HashMap<String, Object> obtenerElementosDeCliente(Cliente cliente){
		HashMap<String, Object> viewModel = new HashMap<>();
		
		//TODO deberiamos crear un helper para mostrar el consumo actual sin hacer esto ni cambiar consumoActual() por getConsumoActual()
		viewModel.put("cliente", cliente);
		viewModel.put("consumoActual", cliente.consumoActual());
		viewModel.put("tieneDispositivos", cliente.cantidadDispositivos() > 0);
		viewModel.put("tieneReglas", cliente.getReglas().size() > 0);		
		viewModel.put("horasOptimas", horasOptimas);
		viewModel.put("mediciones", mediciones);
		
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
	
	public ModelAndView obtenerMediciones(Request req, Response res) {
		Cliente cliente = obtenerClienteDe(req);
		mediciones = RepoConsumoEnFecha.getInstance().filtrarMedicionesXCliente(cliente, req.queryParams("desde"), req.queryParams("hasta"));		
		
		return this.home(req, res);
	}	
}
