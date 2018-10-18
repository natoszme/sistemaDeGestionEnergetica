package repositorio;

import java.util.List;

public interface Repo<Entidad> {
	public void agregarEntidad(Entidad entidad);

	public void agregarEntidades(List<Entidad> entidades);

	public List<Entidad> obtenerTodas();
}
