package server;

import spark.Spark;
import spark.template.handlebars.HandlebarsTemplateEngine;
import static spark.Spark.before;

import server.controller.ControllerAdmin;
import server.controller.ControllerCliente;
import server.controller.ControllerLogin;

public class Router {

	public static void configure() {
		HandlebarsTemplateEngine transformer = new HandlebarsTemplateEngine();
		
		Spark.get("/", ControllerLogin::login, transformer);		
		Spark.post("/", ControllerLogin::validarLogin);
		
		//TODO el before admite expresiones regulares. pero con "/*" no funco!
		before("/admin", (req, res) -> {
			ControllerLogin.siNoEstaLogueadoEchar(req,  res);
		});
		
		before("/cliente", (req, res) -> {
			ControllerLogin.siNoEstaLogueadoEchar(req,  res);
		});
		
		Spark.get("/admin", ControllerAdmin::adminHome, transformer);
		Spark.get("/cliente", ControllerCliente::clienteHome, transformer);
		
		Spark.get("/logout", ControllerLogin::logout);
	}

}
