package repositorio;

import java.util.List;

import javax.persistence.EntityManager;

import org.uqbarproject.jpa.java8.extras.WithGlobalEntityManager;
import org.uqbarproject.jpa.java8.extras.transaction.TransactionalOps;

public abstract class RepoEnDB<Entidad>  implements TransactionalOps, Repo<Entidad>,  WithGlobalEntityManager{
	EntityManager em = entityManager();

	protected String tabla;
  
	public RepoEnDB(String tabla) {
		this.tabla = tabla;
	}


	public void agregarEntidad(Entidad entidad) {
		em.persist(entidad);
	}

	public void agregarEntidades(List<Entidad> entidades) {
		entidades.forEach(entidad->this.agregarEntidad(entidad));
	}

	@SuppressWarnings("unchecked")
	public List<Entidad> obtenerTodas() {
		return (List<Entidad>) em.createQuery("FROM " + tabla).getResultList();
	}
}

