package repositorio;
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
	
	public double dameRestriccionMaximaDe(Dispositivo dispositivo) {
		Query query = em.createQuery("SELECT res.usoMensualMaximo FROM RestriccionUsoDispositivo res WHERE dispositivo = :dispositivo", Double.class);
		query.setParameter("dispositivo", dispositivo);
		
		Double usoMensualMaximo = (Double) query.getSingleResult();
		return usoMensualMaximo.doubleValue();
	}
	
	public double dameRestriccionMinimaDe(Dispositivo dispositivo) {
		Query query = em.createQuery("SELECT res.usoMensualMinimo FROM RestriccionUsoDispositivo res WHERE dispositivo = :dispositivo", Double.class);
		query.setParameter("dispositivo", dispositivo);
		
		Double usoMensualMinimo = (Double) query.getSingleResult();
		return usoMensualMinimo.doubleValue();
	}

	public Actuador dameAccionDe(Dispositivo dispositivo) {
		Query query = em.createQuery("SELECT res.actuadorAlExcederse FROM RestriccionUsoDispositivo res WHERE dispositivo = :dispositivo", Actuador.class);
		query.setParameter("dispositivo", dispositivo);
		
		return (Actuador) query.getSingleResult();
	}
}
