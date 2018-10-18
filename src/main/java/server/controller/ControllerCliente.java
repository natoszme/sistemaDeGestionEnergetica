package server.controller;

import java.util.HashMap;

import cliente.Cliente;
import dispositivo.Dispositivo;
import login.RepoUsuarios;
import spark.ModelAndView;
import spark.Request;
import spark.Response;
import tipoDispositivo.DispositivoEstandar;

public class ControllerCliente {

	public static ModelAndView clienteHome(Request req, Response res) {		
		
		if(!ControllerLogin.tipoUsuario(req).equals("cliente")) {
			res.redirect("/admin");
			return null;
		}
		
		HashMap<String, Object> viewModel = new HashMap<>();
		
		Cliente cliente = RepoUsuarios.getInstance().dameClienteDe(Long.parseLong(req.cookie("id")));
		
		// TODO: sacar los dispositivos harcodeados (fijarse que funcione eleven y odd en la home del cliente)		
		cliente.agregarDispositivo(new Dispositivo("Play 4", new DispositivoEstandar(), 45.987));
		cliente.agregarDispositivo(new Dispositivo("Play 3", new DispositivoEstandar(), 455.987));
		cliente.agregarDispositivo(new Dispositivo("Play 2", new DispositivoEstandar(), 87.987));
		cliente.agregarDispositivo(new Dispositivo("Play 1", new DispositivoEstandar(), 990.987));
		cliente.agregarDispositivo(new Dispositivo("Play 0", new DispositivoEstandar(), 7.987));
		cliente.agregarDispositivo(new Dispositivo("Play -1", new DispositivoEstandar(), 45.987));
		cliente.agregarDispositivo(new Dispositivo("Play -2", new DispositivoEstandar(), 35.987));
		
		//TODO deberiamos crear un helper para mostrar el consumo actual sin hacer esto ni cambiar consumoActual() por getConsumoActual()
		viewModel.put("cliente", cliente);
		viewModel.put("consumoActual", cliente.consumoActual());
		viewModel.put("tieneDispositivos", cliente.cantidadDispositivos() > 0);
		
		return new ModelAndView(viewModel, "cliente/home.hbs");
	}

}
