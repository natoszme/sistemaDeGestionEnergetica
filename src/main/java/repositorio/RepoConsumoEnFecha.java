package repositorio;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.Query;

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
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss");
		
		Query query = entityManager()
						.createQuery(
							"SELECT hc.fecha AS fecha, hc.consumo AS consumo "
							+ "FROM Cliente c "
							+ "INNER JOIN  c.dispositivos AS d "
							+ "INNER JOIN d.tipoDispositivo td "
							+ "INNER JOIN  td.consumosHastaElMomento AS hc "
							+ "WHERE c = :cliente "
							+ (!desde.isEmpty() ? "AND hc.fecha >= :desde " : "")
							+ (!hasta.isEmpty() ? "AND hc.fecha <= :hasta" : "")
						);
		
		query.setParameter("cliente", cliente);
		
		if (!desde.isEmpty()) {
			query.setParameter("desde", LocalDateTime.parse(desde + " 00:00:00", formatter));
		}
		
		if (!hasta.isEmpty()) {
			query.setParameter("hasta", LocalDateTime.parse(hasta + " 23:59:59", formatter)).getResultList();
		}
		
		List<Object[]> results = query.getResultList();
		List<ConsumoEnFecha> mediciones = new ArrayList<ConsumoEnFecha>();
		
		results.stream().forEach(result -> {
			mediciones.add(new ConsumoEnFecha((LocalDateTime) result[0], (Double) result[1]));
			
		});
		
		return mediciones;
	}
}
