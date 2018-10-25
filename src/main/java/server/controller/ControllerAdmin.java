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
import java.util.Objects;

import com.github.jknack.handlebars.Handlebars;
import com.github.jknack.handlebars.Helper;
import com.github.jknack.handlebars.HumanizeHelper;
import com.github.jknack.handlebars.Options;

import cliente.Cliente;
import cliente.TipoDocumento;
import dispositivo.Dispositivo;
import json.JSONParser;
import repositorio.RepoCategorias;
import repositorio.RepoClientes;
import repositorio.RepoConsumoEnFecha;
import server.login.Autenticable;
import server.login.RepoAdmins;
import spark.ModelAndView;
import spark.Request;
import spark.Response;
import tipoDispositivo.ConsumoEnFecha;
import tipoDispositivo.DispositivoEstandar;
import tipoDispositivo.DispositivoInteligente;

public class ControllerAdmin extends ControllerLogin{

	
	
	public ModelAndView home(Request req, Response res) {
		
		/*String fechaDesde = req.queryParams("desde");
		String fechaHasta = req.queryParams("hasta");*/
		/*if(fechaDesde == null) {
			fechaDesde = "";
		}
		if(fechaHasta == null) {
			fechaHasta = "";
		}*/
		
		/*LocalDateTime desde = formatearFecha(fechaDesde, LocalTime.of(0, 0, 0, 0));
		LocalDateTime hasta = formatearFecha(fechaHasta, LocalTime.of(23, 59, 59, 999));*/
		
		HashMap<String, Object> viewModel = new HashMap<>();
	
	//	List<Cliente> clientes = RepoClientes.getInstance().obtenerTodas();
	
		return new ModelAndView(viewModel, "admin/home.hbs");
		
	}
	
	public String obtenerConsumos(Request req, Response res) {
		JSONParser<Object> parser = new JSONParser<Object>();		 
		
	
		
		LocalDateTime desde = formatearFecha(req.queryParams("desde"), LocalTime.of(0, 0, 0, 0));
		LocalDateTime hasta = formatearFecha(req.queryParams("hasta"), LocalTime.of(23, 59, 59, 999));
		
		List<Object> clientesConConsumo = RepoConsumoEnFecha.getInstance().obtenerConsumoDeClientesEnFecha(desde, hasta);
		
		return parser.listToJson(clientesConConsumo);  
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
