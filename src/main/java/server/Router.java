package server;

import spark.Spark;
import spark.template.handlebars.HandlebarsTemplateEngine;

public class Router {

	public static void configure() {
		HandlebarsTemplateEngine transformer = new HandlebarsTemplateEngine();
		
		Spark.get("/", Controller::login, transformer);		
		Spark.post("/", Controller::validarLogin);
		
		Spark.get("/admin", Controller::adminHome, transformer);
		Spark.get("/cliente", Controller::clienteHome, transformer);
	}

}
