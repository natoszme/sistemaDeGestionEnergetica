package db;

import org.junit.Assert;
import org.junit.Before;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.persistence.EntityManager;

import org.junit.Test;

import cliente.Cliente;
import dispositivo.Dispositivo;
import dispositivo.gadgets.regla.CondicionDeConsumoMayorOIgual;
import dispositivo.gadgets.regla.CondicionSobreSensor;
import dispositivo.gadgets.regla.Regla;
import dispositivo.gadgets.regla.ReglaEstricta;
import dispositivo.gadgets.sensor.SensorHorasEncendido;
import fixture.Fixture;
import importacion.ImportadorTransformadores;
import repositorio.RepoTransformadores;
import transformador.Transformador;

public class TestCasoDePrueba extends Fixture {
	EntityManager em = entityManager();
	
	private void persistirCategorias() {		
		em.persist(r1);
		em.persist(r2);
		em.persist(r3);
		em.persist(r4);
		em.persist(r5);
		em.persist(r6);
		em.persist(r7);
		em.persist(r8);
		em.persist(r9);
	}
	
	@Before
	public void setUp() {
		this.persistirCategorias();
	}
	
	@Test
	public void sePersisteYSeModificaElCliente() {		
		em.persist(lio);
		System.out.println(lio.ubicacion());
		//em.flush();
		em.clear();
		
		lio = em.find(Cliente.class, lio.id);
		lio.setUbicacion(ubicacionPalermo);	
		
		//hay que hacer flush porque, por algun motivo, no considera la modificacion como una query
		em.flush();
		
		System.out.println(lio.ubicacion());
		
		em.clear();
		lio = em.find(Cliente.class, lio.id);
		System.out.println(lio.getApellido());
		System.out.println(lio.ubicacion());
		Assert.assertEquals(ubicacionPalermo.toString(), lio.ubicacion().toString());
	}
	
	@Test
	public void modificoUnDispositivoYSeGuardaSuModificacion() {
		lio.agregarDispositivo(play4);
		em.persist(lio);
		
		em.persist(play4);
		em.clear();
		
		play4 = em.find(Dispositivo.class, play4.id);
		
		final String nuevoNombre = "PlayStation 4"; 
		play4.setNombre(nuevoNombre);
		
		// TODO falta mostrar los intervalos que estuvo encendido en el ultimo mes (eso se podria mostrar con los reportes?)
		
		Dispositivo play4Modificada = em.find(Dispositivo.class, play4.id);
		Assert.assertEquals(nuevoNombre, play4Modificada.getNombre());
	}
	
	@Test
	public void persistirReglasYCondiciones() {
		lio.agregarDispositivo(pc);
		em.persist(lio);
		em.persist(pc);
		
		Set<CondicionSobreSensor> condiciones = new HashSet<>();
		condiciones.add(new CondicionDeConsumoMayorOIgual(20, new SensorHorasEncendido(pc)));
		
		// Como esta en cascade persist con los actuadores, deberia persistirlos al persistir la regla
		Regla otraReglaEstricta = new ReglaEstricta(actuadores, condiciones, pc);
		em.persist(otraReglaEstricta);
	}
	
	@Test
	public void importoYPersistoLosTransformadores() {
		// Persisto los transformadores del fixture
		em.persist(transformadorCaballito);
		em.persist(transformadorLaMatanza);
		em.persist(transformadorPalermo);
		
		// Se importan los transformadores del JSON y se persisten		
		ImportadorTransformadores.getInstance().importarJSON();
		
		List<Transformador> transformadores = RepoTransformadores.getInstance().obtenerTodas();		
		transformadores.forEach(transformador -> em.persist(transformador));
		
		long cantidadTransformadoresPersistidos = (long) em.createQuery("SELECT COUNT(*) FROM Transformador").getSingleResult();
		
		Assert.assertEquals(3 + transformadores.size(), cantidadTransformadoresPersistidos);
	}
}
