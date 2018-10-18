package server;

import spark.Spark;
import spark.template.handlebars.HandlebarsTemplateEngine;
import spark.utils.HandlebarsTemplateEngineBuilder;

import static spark.Spark.before;

import server.controller.ControllerAdmin;
import server.controller.ControllerCliente;
import server.controller.ControllerLogin;

public class Router {

	public static void configure() {
		HandlebarsTemplateEngine transformer;
		HandlebarsTemplateEngineBuilder template = HandlebarsTemplateEngineBuilder.create();
		
		//template.withHelper(isEven, EvenHelper.evenHelper())
		
		transformer = template.build();
		
		Spark.get("/", ControllerLogin::login, transformer);		
		Spark.post("/", ControllerLogin::validarLogin);
		Spark.get("/logout", ControllerLogin::logout);
		
		//TODO el before admite expresiones regulares. pero con "/*" no funco!
		before("/admin", (req, res) -> {
			ControllerLogin.siNoEstaLogueadoEchar(req,  res);
		});
		
		before("/cliente", (req, res) -> {
			ControllerLogin.siNoEstaLogueadoEchar(req,  res);
		});
		
		Spark.get("/admin", ControllerAdmin::adminHome, transformer);
		
		Spark.get("/cliente", ControllerCliente::clienteHome, transformer);
		//TODO ver grupo de rutas
	}

}
