package repositorio;

import java.util.ArrayList;
import java.util.List;

public abstract class RepoEnMemoria<Entidad> implements Repo<Entidad> {
	protected List<Entidad> entidades = new ArrayList<>();

	public void agregarEntidad(Entidad entidad) {
		entidades.add(entidad);
	}

	public void agregarEntidades(List<Entidad> entidades) {
		this.entidades.addAll(entidades);
	}

	public List<Entidad> obtenerTodas() {
		return entidades;
	}
	
	public void limpiarEntidades() {
		entidades.clear();
	}
}
