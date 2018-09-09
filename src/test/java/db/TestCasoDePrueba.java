package db;

import org.junit.Assert;
import org.junit.Before;

import javax.persistence.EntityManager;

import org.junit.Test;

import cliente.Cliente;
import fixture.Fixture;

public class TestCasoDePrueba extends Fixture {
	EntityManager em = entityManager();
	
	@Before
	public void setUp() {
		this.run();
	}
	
	@Test
	public void sePersisteYSeModificaElCliente() {
		
		withTransaction(() -> {
			em.persist(lio);
		});
		em.clear();
		
		lio = em.find(Cliente.class, lio.id);
		withTransaction(() -> {
			lio.setUbicacion(ubicacionPalermo);
		});
		
		Cliente lioModificado = em.find(Cliente.class, lio.id);
		Assert.assertEquals(ubicacionPalermo, lioModificado.ubicacion());
	}
	
	/*@Test
	public void sePersisteYSeModificaElClienteV2() {
		
		withTransaction(() -> {
			em.persist(lio);
		});
		em.clear();
		
		lio = em.find(Cliente.class, lio.id);
		withTransaction(() -> {
			lio.setUbicacion(ubicacionPalermo);
		});
		
		Cliente lioModificado = em.find(Cliente.class, lio.id);
		Assert.assertEquals(ubicacionPalermo, lioModificado.ubicacion());
	}*/
}
