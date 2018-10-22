package server.controller;

import java.util.HashMap;

import dispositivo.Dispositivo;
import server.login.Autenticable;
import server.login.RepoAdmins;
import spark.ModelAndView;
import spark.Request;
import spark.Response;
import tipoDispositivo.DispositivoEstandar;
import tipoDispositivo.DispositivoInteligente;

public class ControllerAdmin extends ControllerLogin{

	public ModelAndView home(Request req, Response res) {
		HashMap<String, Object> viewModel = new HashMap<>();
		
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
