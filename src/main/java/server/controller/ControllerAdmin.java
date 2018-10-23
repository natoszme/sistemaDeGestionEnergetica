package server.controller;

import java.awt.Point;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import cliente.Cliente;
import cliente.TipoDocumento;
import dispositivo.Dispositivo;
import repositorio.RepoCategorias;
import repositorio.RepoClientes;
import server.login.Autenticable;
import server.login.RepoAdmins;
import spark.ModelAndView;
import spark.Request;
import spark.Response;
import tipoDispositivo.DispositivoEstandar;
import tipoDispositivo.DispositivoInteligente;

public class ControllerAdmin extends ControllerLogin{

	public ModelAndView home(Request req, Response res) {
		
		String fechaDesde = req.queryParams("desde"); 
		String fechaHasta = req.queryParams("hasta"); 
		
		HashMap<String, Object> viewModel = new HashMap<>();
		
		//List<Cliente> clientes = RepoClientes.getInstance().obtenerTodas();
		List<Cliente> clientes= new ArrayList<>();
		List<Dispositivo> dispositivos = new ArrayList<>();
		Cliente clienteEjemplo = new Cliente("Bebe","1","Bebe", "Perro", TipoDocumento.DNI,(long)11111111, (long)1123894928, "Casa de nato", RepoCategorias.getInstance().obtenerCategoriaPorNombre("R1"), dispositivos, new org.uqbar.geodds.Point(0,1));
		clientes.add(clienteEjemplo);
		
		
		List<Double> consumoPorCliente= new ArrayList<>();
		consumoPorCliente.add(100.2);
		
		HashMap<Cliente, Double> clienteConConsumo;
		//clienteConConsumo.put(key, value)
		//clientes.forEach(cliente->clientesConConsumo.pu);
		
		//clientes.forEach(cliente->consumoPorCliente.add(cliente.consumoRealizadoEntre(fechaInicial, fechaFinal)));
		
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
	
	public ModelAndView crearDispositivoView(Request req, Response res) {
		HashMap<String, Object> viewModel = new HashMap<>();
		
		return new ModelAndView(viewModel, "admin/nuevo-dispositivo-generico.hbs");
	}
	
	public String crearDispositivo(Request req, Response res) {
		
		String nombre = req.queryParams("nombre");
		double kwPorHora = Double.parseDouble(req.queryParams("consumo"));
		String tipoDispositivo = req.queryParams("tipoDispositivo");
		
		Dispositivo dispositivo;
		
		if(tipoDispositivo.equals("estandar")) {
			dispositivo = new Dispositivo(nombre, new DispositivoEstandar(), kwPorHora);
		}
		else {
			dispositivo = new Dispositivo(nombre, new DispositivoInteligente(null), kwPorHora);
		}
		
		RepoDispositivosBase.getInstance().agregarEntidad(dispositivo);
		
		redirigirAHome(res);
		return null;
	}
}
