package db;

import static org.junit.Assert.*;
import org.junit.Assert;

import javax.persistence.EntityManager;

import org.junit.Test;

import cliente.Cliente;
import fixture.Fixture;

public class TestCasoDePrueba extends Fixture {
	EntityManager em = entityManager();
	
	@Test
	public void sePersisteYSeModificaElCliente() {
		
		em.persist(lio);
		em.clear();
		
		withTransaction(() -> {
			em.find(Cliente.class, lio.id);
			lio.setUbicacion(ubicacionPalermo);
		});
		
		em.clear();
		em.find(Cliente.class, lio.id);
		Assert.assertEquals(ubicacionPalermo, lio.ubicacion());
	}
}
