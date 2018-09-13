package db;

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
				+ "INNER JOIN dispositivo d ON d.id_cliente = c.id_cliente"
				+ "INNER JOIN historial_consumo hc ON hc.id_dispositivo = d.id" 
				+ "WHERE hc.fecha <= :desde AND hc.fecha >= :hasta"
				+ "GROUP BY d.id, c.id_cliente").setParameter(0,desde).setParameter(1,hasta).getResultList();
		  	 
		return consumoPorHogar;
	}
	
	public Map<Dispositivo, Double> consumoPromedioPorDispositivoDe() {
		Map<Dispositivo, Double> consumoPromedioPorDispositivo  = new HashMap <Dispositivo, Double>();
		em.createQuery("SELECT c.nombre, d.nombre, AVG(hc.consumo) promedio"
				+ "FROM cliente c "
				+ "INNER JOIN dispositivo d ON d.id_cliente = c.id inner"
				+ "INNER JOIN historial_consumo hc ON hc.id_dispositivo = d.id"
				+ "GROUP BY d.id, c.id_cliente").getResultList();
	
		return consumoPromedioPorDispositivo;
	}
	
	public Map<Transformador, Double> consumoPorTransformadorEntre(LocalDateTime fechaInicial, LocalDateTime fechaFinal) {
		Map<Transformador, Double> consumoPorTransformador  = new HashMap <Transformador, Double>();
/*		return consumoPorTransformador.entrySet().stream().mapToDouble(transformador -> 
		transformador.getKey().consumoEntre(fechaInicial, fechaFinal));*/
		RepoTransformadores.getInstance().obtenerTodas().forEach(
				transformador -> consumoPorTransformador.put(transformador, transformador.consumoEntre(fechaInicial, fechaFinal)));
		return consumoPorTransformador;
	}
	

}





