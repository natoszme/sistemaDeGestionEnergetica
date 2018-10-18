package server;

import server.login.DatosDePrueba;
import spark.Spark;
import spark.debug.DebugScreen;

public class Server {
	public static void main(String[] args) {
		new DatosDePrueba().init();
		Spark.port(9000);
		Spark.init();
		Router.configure();
		DebugScreen.enableDebugScreen();
	}
}
