package importacion;

import json.JSONParser;
import repositorio.RepoEnMemoria;

abstract public class Importador<Entidad> {
	private String rutaArchivo;
	protected RepoEnMemoria<Entidad> repo;
	private Class<Entidad> entidad;
	
	public void importarJSON() {
		JSONParser<Entidad> cargadorDeDatos = new JSONParser<Entidad>();
		repo.agregarEntidades(cargadorDeDatos.importar(rutaArchivo, entidad));
	}
	
	Importador(String rutaArchivo, RepoEnMemoria<Entidad> repo, Class<Entidad> entidad){
		this.rutaArchivo = rutaArchivo;
		this.repo = repo;
		this.entidad = entidad;
	}
}
