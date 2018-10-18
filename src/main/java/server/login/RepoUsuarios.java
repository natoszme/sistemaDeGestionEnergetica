package server.login;

import java.util.Optional;

import javax.persistence.EntityManager;

import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.criterion.Restrictions;
import org.uqbarproject.jpa.java8.extras.WithGlobalEntityManager;

import cliente.Cliente;
import repositorio.RepoEnDB;
import usuario.Usuario;

public class RepoUsuarios extends RepoEnDB<Usuario> implements WithGlobalEntityManager{
	private static RepoUsuarios instancia;
	EntityManager em = entityManager();
	
	public static RepoUsuarios getInstance() {
		if(instancia == null) {
			instancia = new RepoUsuarios();
		}
		return instancia;
	}
	
	public Optional<Usuario> dameUsuario(String usuario, String password) {
		Session session = em.unwrap(Session.class);
		Criteria criteria = session.createCriteria(Usuario.class);
		
		Usuario user = (Usuario) criteria.add(Restrictions.eq("username", usuario))
				.add(Restrictions.eq("password", password))
		        .uniqueResult();
		
		return Optional.ofNullable(user);
	}

	public Cliente dameClienteDe(long id) {
		return em.find(Usuario.class, id).getCliente();
	}
}
