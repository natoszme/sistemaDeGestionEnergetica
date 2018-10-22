package server;

import spark.Spark;
import spark.template.handlebars.HandlebarsTemplateEngine;
import spark.utils.HandlebarsTemplateEngineBuilder;

import static spark.Spark.before;
import static spark.Spark.path;
import static spark.Spark.staticFiles;

import javax.persistence.EntityManager;

import org.uqbarproject.jpa.java8.extras.WithGlobalEntityManager;
import org.uqbarproject.jpa.java8.extras.transaction.TransactionalOps;

import server.controller.ControllerAdmin;
import server.controller.ControllerCliente;
import server.controller.ControllerTransformador;

public class Router implements TransactionalOps, WithGlobalEntityManager{

	private static ControllerAdmin controllerAdmin = new ControllerAdmin();
	private static ControllerCliente controllerCliente= new ControllerCliente();
	private static ControllerTransformador controllerTransformador = new ControllerTransformador();
	
	EntityManager em = entityManager();
	
	public void configure() {
		HandlebarsTemplateEngine transformer = HandlebarsTemplateEngineBuilder.create().withDefaultHelpers().build();

		staticFiles.location("/public");
		
		Spark.before("/*", (req, res) -> {
			if(req.requestMethod() != "GET") {
				em.getTransaction().begin();
			}
		});
		
		path("/admin", () -> {
			
			//hay que definirlo en los dos porque controllerLogin no es estatica
			before("/*", (req, res) -> {
				if (!req.uri().equals("/admin/login")) {
					controllerAdmin.siNoEstaLogueadoEchar(req, res);
				}
			});
			
			Spark.get("/login", controllerAdmin::login, transformer);
			Spark.post("/login", controllerAdmin::validarLogin);
			Spark.get("/logout", controllerAdmin::logout);
			
			Spark.get("/home", controllerAdmin::home, transformer);
		});
		
		Spark.get("/dispositivos/nuevo", controllerAdmin::crearDispositivoView, transformer);
		Spark.post("/dispositivos", controllerAdmin::crearDispositivo);
		
		path("/cliente", () -> {
			
			before("/*", (req, res) -> {
				if (!req.uri().equals("/cliente/login")) {
					controllerCliente.siNoEstaLogueadoEchar(req, res);
				}
			});
			
			Spark.get("/login", controllerCliente::login, transformer);
			Spark.post("/login", controllerCliente::validarLogin);
			Spark.get("/logout", controllerCliente::logout);
			
			Spark.get("/home", controllerCliente::home, transformer);
			Spark.get("/optimizarConsumo", controllerCliente::optimizarUso, transformer);
			
			Spark.get("/mediciones", controllerCliente::obtenerMediciones, transformer);
			
		});
		
		Spark.get("/transformadores", controllerTransformador::home, transformer);
		
		Spark.before("/*", (req, res) -> {
			if(req.requestMethod() != "GET") {
				em.getTransaction().commit();
			}
		});
	}
}