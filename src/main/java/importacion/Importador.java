package importacion;

import json.JSONParser;
import repositorio.Repo;

abstract public class Importador<Entidad> {
	private String rutaArchivo;
	protected Repo<Entidad> repo;
	private Class<Entidad> entidad;
	
	public void importarJSON() {
		JSONParser<Entidad> cargadorDeDatos = new JSONParser<Entidad>();
		repo.agregarEntidades(cargadorDeDatos.importar(rutaArchivo, entidad));
	}
	
	Importador(String rutaArchivo, Repo<Entidad> repo, Class<Entidad> entidad){
		this.rutaArchivo = rutaArchivo;
		this.repo = repo;
		this.entidad = entidad;
	}
}
