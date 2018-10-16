package server;

import java.util.HashMap;
import java.util.Optional;

import login.RepoUsuarios;
import spark.ModelAndView;
import spark.Request;
import spark.Response;
import usuario.Usuario;

public class Controller {
	
	public static ModelAndView login(Request req, Response res) {
		//TODO revisar el null
		return new ModelAndView(null, "index.hbs");
	}
	
	//TODO la pass tiene que llegar aca ya hasheada!
	public static ModelAndView validarLogin(Request req, Response res) {
		
		String username = req.queryParams("username");
		String password = req.queryParams("password");
		
		Optional<Usuario> usuario = RepoUsuarios.getInstance().dameUsuario(username, password);
		
		System.out.println(usuario.isPresent());
		
		final HashMap<String, Object> viewModel = usuario.map(user -> viewModelSegun(user))
				.orElse(viewModelConUsername(username));
		
		return usuario.map(user -> modelAndViewSegun(user, viewModel))
				.orElse(new ModelAndView(viewModel, "index.hbs"));
	}

	private static HashMap<String, Object> viewModelConUsername(String username) {
		HashMap<String, Object> viewModel = new HashMap<>();
		viewModel.put("username", username);
		return viewModel;
	}

	private static HashMap<String, Object> viewModelSegun(Usuario user) {
		HashMap<String, Object> viewModel = new HashMap<>();
		
		if(user.esAdmin()) {
			//TODO agregar las cosas del admin
			//viewModel.put();
		}
		else {
			viewModel.put("cliente", user.getCliente());
		}
		
		return viewModel;
	}

	private static ModelAndView modelAndViewSegun(Usuario user, HashMap<String, Object> viewModel) {
		//TODO llenar el viewModel con la data segun corresponda
		return new ModelAndView(viewModel,
				obtenerViewConPermisoDe(user));
	}

	private static String obtenerViewConPermisoDe(Usuario user) {
		String recurso = "usuario";
		if(user.esAdmin()) {
			recurso = "admin";
		}
		return recurso + "/home.hbs";
	}
}
