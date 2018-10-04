package repositorio;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;

import org.uqbarproject.jpa.java8.extras.WithGlobalEntityManager;

public abstract class RepoEnDB<Entidad> implements Repo<Entidad>,  WithGlobalEntityManager{
	//protected List<Entidad> entidades = new ArrayList<>();
	EntityManager em = entityManager();
	String table;
	public void agregarEntidad(Entidad entidad) {
		em.persist(entidad);
	}

	public void agregarEntidades(List<Entidad> entidades) {
		entidades.forEach(entidad->em.persist(entidad));
	}

	@SuppressWarnings("unchecked")
	public List<Entidad> obtenerTodas() {
		return (List<Entidad>) em.createQuery("select * from " + table);
	}
	
	public void limpiarEntidades() {
		
	}
}
