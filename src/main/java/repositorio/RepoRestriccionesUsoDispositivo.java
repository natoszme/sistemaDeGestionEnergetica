package repositorio;
import javax.persistence.NoResultException;
import javax.persistence.Query;

import dispositivo.Dispositivo;
import dispositivo.gadgets.actuador.Actuador;
import simplex.NoExisteRestriccionPara;
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
	
	private RestriccionUsoDispositivo obtenerRestriccionDe(Dispositivo dispositivo) {
		Query query = em.createQuery("SELECT res FROM RestriccionUsoDispositivo res WHERE dispositivo = :dispositivo", RestriccionUsoDispositivo.class);
		query.setParameter("dispositivo", dispositivo);
		
		RestriccionUsoDispositivo restriccion;
		try {
			restriccion = (RestriccionUsoDispositivo) query.getSingleResult();
		}
		catch (NoResultException e) {
			throw new NoExisteRestriccionPara(dispositivo);
		}
		return restriccion;
	}
	
	public double dameRestriccionMaximaDe(Dispositivo dispositivo) {
		Query query = em.createQuery("SELECT res.usoMensualMaximo FROM RestriccionUsoDispositivo res WHERE dispositivo = :dispositivo", Double.class);
		query.setParameter("dispositivo", dispositivo);
		
		Double usoMensualMaximo = (Double) query.getSingleResult();
		return usoMensualMaximo.doubleValue();
	}
	
	public double dameRestriccionMinimaDe(Dispositivo dispositivo) {
		RestriccionUsoDispositivo restriccion = obtenerRestriccionDe(dispositivo);
		
		return restriccion.getUsoMensualMinimo();
	}

	public Actuador dameAccionDe(Dispositivo dispositivo) {
		return obtenerRestriccionDe(dispositivo).getActuadorAlExcederse();
	}
}
