package server.controller;

import dispositivo.Dispositivo;
import repositorio.RepoEnDB;

public class RepoDispositivosBase extends RepoEnDB<Dispositivo>{
	
	private static RepoDispositivosBase instancia;

	public RepoDispositivosBase(String tabla) {
		super(tabla);
	}
	
	public static RepoDispositivosBase getInstance() {
		if(instancia == null) {
			instancia = new RepoDispositivosBase("Dispositivo");
		}
		return instancia;
	}
}
