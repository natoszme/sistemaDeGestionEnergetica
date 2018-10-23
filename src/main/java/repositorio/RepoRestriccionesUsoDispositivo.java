package repositorio;
import javax.persistence.NoResultException;
import javax.persistence.Query;

import dispositivo.Dispositivo;
import dispositivo.gadgets.actuador.Actuador;
import simplex.RestriccionUsoDispositivo;

public class RepoRestriccionesUsoDispositivo extends RepoEnDB<RestriccionUsoDispositivo> {
	
	public RepoRestriccionesUsoDispositivo(String tabla) {
		super(tabla);
	}

	private static RepoRestriccionesUsoDispositivo instancia;	
	
	public static RepoRestriccionesUsoDispositivo getInstance(){
		if (instancia == null) {
			instancia = new RepoRestriccionesUsoDispositivo("RestriccionUsoDispositivo");
		}
		return instancia;
	}
	
	// TODO: evitar repeticion de logica de los metodos que obtienen la restriccion (max y min)
	// TODO: ver que hacer con el try catch
	public double dameRestriccionMaximaDe(Dispositivo dispositivo) {
		Query query = em.createQuery("SELECT res.usoMensualMaximo FROM RestriccionUsoDispositivo res WHERE dispositivo = :dispositivo", Double.class);
		query.setParameter("dispositivo", dispositivo);
		
		Double usoMensualMaximo;
		
		try {
			usoMensualMaximo = (Double) query.getSingleResult();			
		} catch (NoResultException e) {
			usoMensualMaximo = 0.0;
		}
		
		return usoMensualMaximo.doubleValue();
	}
	
	public double dameRestriccionMinimaDe(Dispositivo dispositivo) {
		Query query = em.createQuery("SELECT res.usoMensualMinimo FROM RestriccionUsoDispositivo res WHERE dispositivo = :dispositivo", Double.class);
		query.setParameter("dispositivo", dispositivo);		
		
		Double usoMensualMinimo;
		
		try {
			usoMensualMinimo = (Double) query.getSingleResult();			
		} catch (NoResultException e) {
			usoMensualMinimo = 0.0;
		}
		
		return usoMensualMinimo.doubleValue();
	}

	public Actuador dameAccionDe(Dispositivo dispositivo) {
		Query query = em.createQuery("SELECT res.actuadorAlExcederse FROM RestriccionUsoDispositivo res WHERE dispositivo = :dispositivo", Actuador.class);
		query.setParameter("dispositivo", dispositivo);
		
		return (Actuador) query.getSingleResult();
	}
}
