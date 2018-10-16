package server;

import java.util.HashMap;
import java.util.Optional;

import cliente.Cliente;
import login.RepoUsuarios;
import spark.ModelAndView;
import spark.Request;
import spark.Response;
import usuario.Usuario;

public class Controller {
	
	public static ModelAndView login(Request req, Response res) {
		
		//TODO ver si la cookie esta seteada (logueado) y en ese caso, redirect
		
		//TODO como hacer para mostrar username en caso de login fallido? Entra a este metodo (get) despues de haber pasado por post
		//pero ya sin los datos del form
		HashMap<String, Object> viewModel = new HashMap<>();
		viewModel.put("username", req.queryParams("username"));
		return new ModelAndView(viewModel, "index.hbs");
	}
	
	//TODO la pass tiene que llegar aca ya hasheada!
	public static String validarLogin(Request req, Response res) {		
		String username = req.queryParams("username");
		String password = req.queryParams("password");
		
		Optional<Usuario> usuario = RepoUsuarios.getInstance().dameUsuario(username, password);
		
		String recurso = usuario.map(user -> obtenerHomeDe(user))
				.orElse("/");
		
		//TODO estaria bueno setear si es admin o user para hacer el redirect mas facil?
		usuario.ifPresent(user -> {
				req.session(true);
				res.cookie("id", String.valueOf(user.id));
			});
		
		res.redirect(recurso);
		
		return "200";
	}
	
	public static ModelAndView adminHome(Request req, Response res) {
		HashMap<String, Object> viewModel = new HashMap<>();
		
		//viewModel.put("consumos", //lo que devuelva el reporte);
		
		return new ModelAndView(viewModel, "admin/home.hbs");
	}
	
	public static ModelAndView clienteHome(Request req, Response res) {
		HashMap<String, Object> viewModel = new HashMap<>();
		
		Cliente cliente = RepoUsuarios.getInstance().dameClienteDe(Long.parseLong(req.cookie("id")));
		
		viewModel.put("cliente", cliente);
		
		return new ModelAndView(viewModel, "cliente/home.hbs");
	}

	private static String obtenerHomeDe(Usuario user) {
		String recurso = "cliente";
		if(user.esAdmin()) {
			recurso = "admin";
		}
		return recurso;
	}
}
