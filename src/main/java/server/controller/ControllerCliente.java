package server.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Optional;

import cliente.Cliente;
import dispositivo.Dispositivo;
import server.login.RepoUsuarios;
import simplex.OptimizadorUsoDispositivos;
import spark.ModelAndView;
import spark.Request;
import spark.Response;
import usuario.Usuario;

import org.apache.commons.math3.util.Pair;

public class ControllerCliente extends ControllerLogin{

	public ModelAndView clienteHome(Request req, Response res) {		
		
		if(!estaLogueado(req)) {
			res.redirect("/admin");
			return null;
		}
		
		HashMap<String, Object> viewModel = new HashMap<>();
		
		Cliente cliente = obtenerClienteDe(req);
		
		// TODO: fijarse que funcione eleven y odd en la home del cliente
		viewModel = obtenerElementosDeCliente(cliente);		
		
		return new ModelAndView(viewModel, "cliente/home.hbs");
	}
	
	public static ModelAndView optimizarUso(Request req, Response res) {
		Cliente cliente = obtenerClienteDe(req);
		OptimizadorUsoDispositivos optimizador = new OptimizadorUsoDispositivos(cliente);
		
		List<Pair<Dispositivo, Double>> horasOptimas = optimizador.optimizarUsoDispositivos();
		
		HashMap<String, Object> viewModel = obtenerElementosDeCliente(cliente);
		
		viewModel.put("horasOptimas", horasOptimas);
		
		return new ModelAndView(viewModel, "cliente/home.hbs");
	}
	
	public static HashMap<String, Object> obtenerElementosDeCliente(Cliente cliente){
		HashMap<String, Object> viewModel = new HashMap<>();
		
		//TODO deberiamos crear un helper para mostrar el consumo actual sin hacer esto ni cambiar consumoActual() por getConsumoActual()
		viewModel.put("cliente", cliente);
		viewModel.put("consumoActual", cliente.consumoActual());
		viewModel.put("tieneDispositivos", cliente.cantidadDispositivos() > 0);
		
		return viewModel;
	}
	
	private static Cliente obtenerClienteDe(Request req) {
		return RepoUsuarios.getInstance().dameClienteDe(Long.parseLong(req.cookie("id")));
	}

	@Override
	protected String home() {
		return "cliente";
	}

	protected String nombreCookieId() {
		return "idCliente";
	}

}
