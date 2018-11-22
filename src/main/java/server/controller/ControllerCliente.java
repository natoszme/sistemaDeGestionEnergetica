package server.controller;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.List;

import cliente.Cliente;
import dispositivo.Dispositivo;
import repositorio.RepoClientes;
import repositorio.RepoConsumoEnFecha;
import server.login.Autenticable;
import simplex.JobOptimizador;
import simplex.OptimizadorUsoDispositivos;
import spark.ModelAndView;
import spark.Request;
import spark.Response;
import tipoDispositivo.ConsumoEnFecha;

import org.apache.commons.math3.util.Pair;

public class ControllerCliente extends ControllerLogin {

	List<Pair<Dispositivo, Double>> horasOptimas;
	
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
		
		viewModel.put("cliente", cliente);
		viewModel.put("consumoActual", cliente.consumoActual());
		viewModel.put("tieneDispositivos", cliente.cantidadDispositivos() > 0);
		viewModel.put("tieneReglas", cliente.getReglas().size() > 0);		
		viewModel.put("horasOptimas", horasOptimas);
		viewModel.put("resultadosOptimizador", new OptimizadorUsoDispositivos(cliente).optimizarUsoDispositivos());
		viewModel.put("esAhorradorAutomatico", cliente.getPermiteAhorroAutomatico());
		
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
	
	public List<ConsumoEnFecha> obtenerMediciones(Request req, Response res) {		
		Cliente cliente = obtenerClienteDe(req);
		
		LocalDateTime desde = formatearFecha(req.queryParams("desde"), LocalTime.of(0, 0, 0, 0));
		LocalDateTime hasta = formatearFecha(req.queryParams("hasta"), LocalTime.of(23, 59, 59, 999));
		
		return RepoConsumoEnFecha.getInstance().filtrarMedicionesXCliente(cliente, desde, hasta);  
	}
	
	private LocalDateTime formatearFecha(String fecha, LocalTime tiempo) {
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
		return (!fecha.isEmpty() ? LocalDateTime.of(LocalDate.parse(fecha, formatter), tiempo) : null);		
	}
}
