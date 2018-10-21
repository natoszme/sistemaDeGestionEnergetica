package repositorio;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
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
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
		
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
			LocalDateTime fechaDesde = LocalDateTime.of(LocalDate.parse(desde, formatter), LocalTime.of(0, 0, 0));
			query.setParameter("desde", fechaDesde);
		}		
		
		if (!hasta.isEmpty()) {
			LocalDateTime fechaHasta = LocalDateTime.of(LocalDate.parse(hasta, formatter), LocalTime.of(23, 59, 59));
			query.setParameter("hasta", fechaHasta);
		}
		
		List<Object[]> results = query.getResultList();
		
		// TODO: esto lo deberia hacer directamente la query (dice que no lo sabe castear)
		// Ni siquiera si en el select se hace new ConsumoDeFecha(..., ...)
		List<ConsumoEnFecha> mediciones = new ArrayList<ConsumoEnFecha>();
		
		results.stream().forEach(result -> {
			mediciones.add(new ConsumoEnFecha((LocalDateTime) result[0], (Double) result[1]));			
		});
		
		return mediciones;
	}
}
