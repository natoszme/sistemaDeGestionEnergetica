package importacion;

import categoria.Categoria;
import repositorio.Repo;
import repositorio.RepoCategorias;
import repositorio.RepoEnMemoria;

public class ImportadorCategorias extends Importador<Categoria>{
	
	private static ImportadorCategorias instancia;
	
	public static ImportadorCategorias getInstance() {
		if(instancia == null) {
			instancia = new ImportadorCategorias("./resources/jsonData/categorias.json", RepoCategorias.getInstance(), Categoria.class);
		}
		return instancia;
	}
	
	private ImportadorCategorias(String rutaArchivo, Repo<Categoria> repo, Class<Categoria> entidad) {
		super(rutaArchivo, repo, entidad);
	}
}
