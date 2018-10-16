package server;

import login.DatosDePrueba;
import spark.Spark;
import spark.debug.DebugScreen;

public class Server {
	public static void main(String[] args) {
		//TODO llamada a carga de datos iniciales
		new DatosDePrueba().init();
		Spark.port(9000);
		Spark.init();
		Router.configure();
		DebugScreen.enableDebugScreen();
	}
}
