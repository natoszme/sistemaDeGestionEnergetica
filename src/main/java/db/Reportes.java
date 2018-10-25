package db;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;

import org.uqbarproject.jpa.java8.extras.WithGlobalEntityManager;
import org.uqbarproject.jpa.java8.extras.transaction.TransactionalOps;

import cliente.Cliente;
import dispositivo.Dispositivo;
import repositorio.RepoTransformadores;
import transformador.Transformador;


public class Reportes implements TransactionalOps, WithGlobalEntityManager{

	EntityManager em = entityManager();
	
//	public void main(String[] args) {
//			
//		EntityManager em = entityManager();
//		EntityTransaction transaction = em.getTransaction();
//		
//		transaction.begin();
//		Reportes instancia = new Reportes();
//		
//		instancia.consumoPorHogarEntre(LocalDateTime.of(2018, 9, 10,0, 0), LocalDateTime.now());
//		
//		transaction.commit();
//			
//	}
	@SuppressWarnings("unchecked")
	public List<Object[]> consumoPorHogarEntre(LocalDateTime desde, LocalDateTime hasta) {
//		Map<Cliente, Double> consumoPorHogar  = new HashMap <Cliente, Double>();
		List<Object[]> resultados = em.createQuery("SELECT c.nombre, SUM(hc.consumo) FROM Cliente c "
			+ " INNER JOIN  c.dispositivos AS d"
			+ " INNER JOIN  d.tipoDispositivo.consumosHastaElMomento AS hc"
			+ " WHERE hc.fecha <= :desde AND hc.fecha >= :hasta"
			+ " GROUP BY d.id, c.id").setParameter("desde", desde).setParameter("hasta", hasta).getResultList();
		
		/*for (Object[] resultado : resultados) {
			for(int i = 0 ; i< 3; i++) {
				System.out.println(resultado[i]);
				
			}
		}*/
		return resultados;
	}
	
	@SuppressWarnings("unchecked")
	public List<Object[]> consumoPromedioPorDispositivoDe() {
		//Map<Dispositivo, Double> consumoPromedioPorDispositivo = new HashMap<Dispositivo, Double>();
		List<Object[]> lista =
			em.createQuery("SELECT c.nombre, d.nombre, AVG(hc.consumo)" + " FROM Cliente c "
					+ " INNER JOIN c.dispositivos AS d" 
					+ " INNER JOIN d.tipoDispositivo AS td"
					+ " INNER JOIN td.consumosHastaElMomento AS hc" 
					+ " GROUP BY d.id, c.id").getResultList();
		
		List<Cliente> lista2 =
				em.createQuery("FROM Cliente").getResultList();
		
		for(Cliente c : lista2) {
			System.out.print("fafaf " + c.getNombre());
		}
		
		System.out.println("asdasd");
		
		for (Object[] object : lista) {
			for (int i = 0; i < 3; i++) {
				System.out.println("asdasd" + object[i]);

			}
		}
		
		System.out.println("asdasd");
		return lista;
	}

	public Map<Transformador, Double> consumoPorTransformadorEntre(LocalDateTime fechaInicial,
			LocalDateTime fechaFinal) {
		Map<Transformador, Double> consumoPorTransformador = new HashMap<Transformador, Double>();

		RepoTransformadores.getInstance().obtenerTodas().forEach(transformador -> consumoPorTransformador
				.put(transformador, transformador.consumoEntre(fechaInicial, fechaFinal)));
		return consumoPorTransformador;
	}

}
