package server.controller;

import java.awt.Point;
import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import com.github.jknack.handlebars.Handlebars;
import com.github.jknack.handlebars.Helper;
import com.github.jknack.handlebars.HumanizeHelper;
import com.github.jknack.handlebars.Options;

import cliente.Cliente;
import cliente.TipoDocumento;
import dispositivo.Dispositivo;
import repositorio.RepoCategorias;
import repositorio.RepoClientes;
import repositorio.RepoConsumoEnFecha;
import server.login.Autenticable;
import server.login.RepoAdmins;
import spark.ModelAndView;
import spark.Request;
import spark.Response;
import tipoDispositivo.DispositivoEstandar;
import tipoDispositivo.DispositivoInteligente;

public class ControllerAdmin extends ControllerLogin{

	
	
	public ModelAndView home(Request req, Response res) {
		
		LocalDateTime desde = null;
		LocalDateTime hasta = null;
		//LocalDateTime desde = formatearFecha(req.queryParams("desde"), LocalTime.of(0, 0, 0, 0));
		//LocalDateTime hasta = formatearFecha(req.queryParams("hasta"), LocalTime.of(23, 59, 59, 999));
		
		HashMap<String, Object> viewModel = new HashMap<>();
	
		List<Cliente> clientes = RepoClientes.getInstance().obtenerTodas();
		
		viewModel.put("clientes", clientes);
		viewModel.put("fechaDesde", desde);
		viewModel.put("fechaHasta", hasta);
		
		return new ModelAndView(viewModel, "admin/home.hbs");
		
		
		
		/*List<Cliente> clientes= new ArrayList<>();
		List<Dispositivo> dispositivos = new ArrayList<>();
		Cliente clienteEjemplo = new Cliente("Bebe","1","Bebe", "Perro", TipoDocumento.DNI,(long)11111111, (long)1123894928, "Casa de nato", RepoCategorias.getInstance().obtenerCategoriaPorNombre("R1"), dispositivos, new org.uqbar.geodds.Point(0,1));
		clientes.add(clienteEjemplo);
		List<Double> consumoPorCliente= new ArrayList<>();
		consumoPorCliente.add(100.2);
		HashMap<Cliente, Double> clienteConConsumo;*/
		//clienteConConsumo.put(key, value)
		//clientes.forEach(cliente->clientesConConsumo.pu);
		//clientes.forEach(cliente->consumoPorCliente.add(cliente.consumoRealizadoEntre(fechaInicial, fechaFinal)));
		//viewModel.put("consumos", //lo que devuelva el reporte);
		
		
	}
	
	public double consumoDeClienteEnFecha(Cliente cliente, LocalDateTime desde, LocalDateTime hasta) {
		return RepoConsumoEnFecha.getInstance().obtenerConsumoDeClienteEnFecha(cliente, desde, hasta);
	}
	
	//TODO pasar esto arriba y que admin y cliente lo usen
	private LocalDateTime formatearFecha(String fecha, LocalTime tiempo) {
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
		return (!fecha.isEmpty() ? LocalDateTime.of(LocalDate.parse(fecha, formatter), tiempo) : null);		
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
