package server;

import spark.Spark;
import spark.template.handlebars.HandlebarsTemplateEngine;
import static spark.Spark.before;

public class Router {

	public static void configure() {
		HandlebarsTemplateEngine transformer = new HandlebarsTemplateEngine();
		
		Spark.get("/", Controller::login, transformer);		
		Spark.post("/", Controller::validarLogin);
		
		//TODO el before admite expresiones regulares. pero con "/*" no funco!
		before("/admin", (req, res) -> {
			Controller.siNoEstaLogueadoEchar(req,  res);
		});
		
		before("/cliente", (req, res) -> {
			Controller.siNoEstaLogueadoEchar(req,  res);
		});
		
		Spark.get("/admin", Controller::adminHome, transformer);
		Spark.get("/cliente", Controller::clienteHome, transformer);
		
		Spark.get("/logout", Controller::logout);
	}

}
