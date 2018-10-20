package server.login;

import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.criterion.Restrictions;

import repositorio.RepoEnDB;

public class RepoAutenticables<Entidad extends Autenticable> extends RepoEnDB<Entidad>{
	
	private Class<Entidad> entidad;
	
	public RepoAutenticables(Class<Entidad> entidad) {
		this.entidad = entidad;
	}
	
	public Autenticable dameAutenticable(String usuario, String password) {
		Session session = entityManager().unwrap(Session.class);
		Criteria criteria = session.createCriteria(entidad);
		
		Autenticable autenticable = (Autenticable) criteria.add(Restrictions.eq("username", usuario))
				.add(Restrictions.eq("password", password))
		        .uniqueResult();
		
		return autenticable;
	}

	@Override
	public void limpiarEntidades() {
		// TODO Auto-generated method stub
		
	}
}
