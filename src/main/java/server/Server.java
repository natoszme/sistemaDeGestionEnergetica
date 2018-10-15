package server;

import spark.Spark;

public class Server {
	public static void main(String[] args) {
		//TODO llamada a carga de datos iniciales
		Spark.port(9000);
		Spark.init();
		Router.configure();
	}
}
