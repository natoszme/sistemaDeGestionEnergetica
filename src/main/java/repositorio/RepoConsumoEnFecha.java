package repositorio;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

import org.hibernate.Session;
import org.hibernate.Filter;

import cliente.Cliente;
import repositorio.RepoEnDB;
import tipoDispositivo.ConsumoEnFecha;

public class RepoConsumoEnFecha extends RepoEnDB<ConsumoEnFecha> {
	
	private static RepoConsumoEnFecha instancia;
	
	public RepoConsumoEnFecha(String tabla) {
		super(tabla);
	}
	
	public static RepoConsumoEnFecha getInstance() {
		if(instancia == null) {
			instancia = new RepoConsumoEnFecha("HistorialConsumos");
		}
		return instancia;
	}
	
	public List<ConsumoEnFecha> filtrarMedicionesXCliente(Cliente cliente, String desde, String hasta) { 
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
			
		Session session = entityManager().unwrap(Session.class);
		Filter filter = session.enableFilter("filtroDeFecha");
		
		filter.setParameter("desde", LocalDateTime.parse(desde, formatter));
		filter.setParameter("hasta", LocalDateTime.parse(hasta, formatter));
		
		String query = "SELECT hc.* FROM Cliente c INNER JOIN  c.dispositivos AS d INNER JOIN  d.tipoDispositivo.consumosHastaElMomento AS hc";
		query = "FROM ConsumoEnFecha";
		List<ConsumoEnFecha> results = (List<ConsumoEnFecha>) session.createQuery(query);
		
		for (ConsumoEnFecha c: results) {
			System.out.println(c.getFecha());
			System.out.println(c.getConsumo());
		}
		
		session.disableFilter("filtroDeFecha");
		
		return results;
	}
}
