package emGlobal;
import javax.persistence.EntityManager;

import org.uqbarproject.jpa.java8.extras.WithGlobalEntityManager;



public class EntityManagerGlobal implements WithGlobalEntityManager {
	private static EntityManagerGlobal instancia;
	
	EntityManager em = entityManager();
	
	public EntityManagerGlobal() {
		
	}
	
	public static  EntityManagerGlobal getInstance() {
		
		if (instancia == null) {
			instancia = new EntityManagerGlobal();
		}
		
		return instancia;
	}
	public EntityManager getEntityManager() {
		return em;
	}
}
