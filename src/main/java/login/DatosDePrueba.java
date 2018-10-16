package login;

import javax.persistence.EntityManager;

import org.uqbarproject.jpa.java8.extras.WithGlobalEntityManager;
import org.uqbarproject.jpa.java8.extras.test.AbstractPersistenceTest;

import usuario.Usuario;

public class DatosDePrueba extends AbstractPersistenceTest implements WithGlobalEntityManager{
	public void init() {
		EntityManager em = entityManager();
		
		Usuario unUser = new Usuario("admin", "123", true, null);
		withTransaction(() -> {em.persist(unUser); });
	}
}
