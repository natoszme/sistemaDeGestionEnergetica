package db;

import java.awt.peer.SystemTrayPeer;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.persistence.EntityManager;
import javax.persistence.Query;

import org.uqbarproject.jpa.java8.extras.WithGlobalEntityManager;

import cliente.Cliente;
import dispositivo.Dispositivo;
import repositorio.RepoTransformadores;
import transformador.Transformador;

public class Reportes implements WithGlobalEntityManager{

	EntityManager em = entityManager();
	/*TODO Hay que ver que tipo devolverían los métodos*/
	public Map<Cliente, Double> consumoPorHogarEntre(LocalDateTime desde, LocalDateTime hasta) {
		Map<Cliente, Double> consumoPorHogar  = new HashMap <Cliente, Double>();
		em.createQuery("SELECT c.nombre, SUM(hc.consumo) consumoPorHogar FROM cliente c "
				+ " INNER JOIN  c.dispositivos AS d"
				+ " INNER JOIN  d.consumoHastaElMomento AS td"
				+ " INNER JOIN  td.consumoHastaElMomento AS hc"
				+ " WHERE hc.fecha <= :desde AND hc.fecha >= :hasta"
				+ " GROUP BY d.id, c.id_cliente").setParameter(0,desde).setParameter(1,hasta).getResultList();
		  	 
		return consumoPorHogar;
	}
	
	public Map<Dispositivo, Double> consumoPromedioPorDispositivoDe() {
		Map<Dispositivo, Double> consumoPromedioPorDispositivo  = new HashMap <Dispositivo, Double>();
		List<Object[]> lista = em.createQuery("SELECT c.nombre, d.nombre, AVG(hc.consumo) promedio"
				+ " FROM cliente c "
				+ " INNER JOIN c.dispositivos AS d"
				+ " INNER JOIN d.tipoDispositivo AS td"
				+ " INNER JOIN td.consumosHastaElMomento AS hc"
				+ " GROUP BY d.id, c.id").getResultList();
		for (Object[] object : lista) {
			for(int i = 0 ; i< 3; i++) {
				System.out.println(object[i]);
				
			}
		}
		return consumoPromedioPorDispositivo;
	}
	
	public Map<Transformador, Double> consumoPorTransformadorEntre(LocalDateTime fechaInicial, LocalDateTime fechaFinal) {
		Map<Transformador, Double> consumoPorTransformador  = new HashMap <Transformador, Double>();

		RepoTransformadores.getInstance().obtenerTodas().forEach(
				transformador -> consumoPorTransformador.put(transformador, transformador.consumoEntre(fechaInicial, fechaFinal)));
		return consumoPorTransformador;
	}
	

}





