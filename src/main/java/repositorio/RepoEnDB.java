package repositorio;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;

import org.hibernate.metamodel.source.binder.ToOneAttributeSource;
import org.uqbarproject.jpa.java8.extras.WithGlobalEntityManager;
import org.uqbarproject.jpa.java8.extras.test.AbstractPersistenceTest;
import org.uqbarproject.jpa.java8.extras.transaction.TransactionalOps;

public abstract class RepoEnDB<Entidad>  implements TransactionalOps, Repo<Entidad>,  WithGlobalEntityManager{
	//protected List<Entidad> entidades = new ArrayList<>();
	EntityManager em = entityManager();
	String tabla;

	public void agregarEntidad(Entidad entidad) {
		withTransaction(() -> {em.persist(entidad);});
	}

	public void agregarEntidades(List<Entidad> entidades) {
		entidades.forEach(entidad->this.agregarEntidad(entidad));
	}

	@SuppressWarnings("unchecked")
	public List<Entidad> obtenerTodas() {
		List<Entidad> entidades = null;
		
		entidades = (List<Entidad>) em.createQuery("FROM " + tabla).getResultList();
		  
		return entidades;
	}
	
	public void limpiarEntidades() {
		System.out.print(tabla + " por borrarse");
		em.createQuery("DELETE FROM " + tabla);
		obtenerTodas().stream().forEach(entidad->em.detach(entidad));
		System.out.print(tabla + " borrada");
		
	}
}

