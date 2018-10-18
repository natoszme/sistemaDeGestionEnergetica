package server;

import spark.Request;
import spark.Response;
import spark.Spark;
import spark.template.handlebars.HandlebarsTemplateEngine;
import spark.utils.HandlebarsTemplateEngineBuilder;

import static spark.Spark.before;
import static spark.Spark.path;

import server.controller.ControllerAdmin;
import server.controller.ControllerCliente;

public class Router {

	public static void configure() {
		HandlebarsTemplateEngine transformer = HandlebarsTemplateEngineBuilder.create().withDefaultHelpers().build();
		
		ControllerAdmin controllerAdmin = new ControllerAdmin();
		ControllerCliente controllerCliente= new ControllerCliente();
		
		path("admin", () -> {
			
			//hay que definirlo en los dos porque controllerLogin no es estatica
			before("/*", (req, res) -> {
				if (!req.uri().equals("/admin/login")) {
					controllerAdmin.siNoEstaLogueadoEchar(req, res);
				}
			});
			
			Spark.get("/login", controllerAdmin::login);
			
			Spark.get("/", ControllerAdmin::adminHome, transformer);
		});
		
		path("cliente", () -> {
			
			before("/*", (req, res) -> {
				if (!req.uri().equals("/cliente/login")) {
					controllerCliente.siNoEstaLogueadoEchar(req, res);
				}
			});
			
			Spark.get("/login", controllerCliente::login);
			
			Spark.get("/", controllerCliente::clienteHome, transformer);
			Spark.get("/cliente/optimizarConsumo", ControllerCliente::optimizarUso, transformer);
		});
		
		Spark.get("/logout", (req, res) -> {
			return logout(req, res);
		});
	}
	
	//TODO habria que cambiar un poco los controllers para que pueda haber un logout generico
	public static String logout(Request req, Response res) {
		res.removeCookie("idUsuario");
		res.removeCookie("idAdmin");
		
		res.redirect("/cliente/login");
		
		return null;
	}
}