package db;

import org.junit.Assert;
import org.junit.Before;

import java.time.LocalDateTime;
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
import dispositivo.gadgets.sensor.Sensor;
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
		
		em.flush();
		em.clear();
		
		lio = em.find(Cliente.class, lio.id);
		lio.setUbicacion(ubicacionPalermo);	
		
		em.flush();		
		em.clear();
		
		lio = em.find(Cliente.class, lio.id);

		Assert.assertEquals(ubicacionPalermo.toString(), lio.ubicacion().toString());
	}
	
	@Test
	public void modificoUnDispositivoYSeGuardaSuModificacion() {
		lio.agregarDispositivo(play4);
		em.persist(lio);
		
		em.flush();
		em.clear();
		
		play4 = em.find(Dispositivo.class, play4.id);
		
		final String nuevoNombre = "PlayStation 4"; 
		play4.setNombre(nuevoNombre);
		
		em.flush();
		em.clear();
		
		Dispositivo play4Modificada = em.find(Dispositivo.class, play4.id);
		Assert.assertEquals(nuevoNombre, play4Modificada.getNombre());
	}
	
	@Test
	public void modificoUnDispositivo2VecesYDejaGuardadaLaUltimaModificacion() {
		lio.agregarDispositivo(play4);
		em.persist(lio);
		
		em.persist(play4);
		em.clear();
		
		play4 = em.find(Dispositivo.class, play4.id);
		play4.setNombre("PlayStation 4");
		play4.setNombre("PlayStation 4 Plus");
		Dispositivo play4Modificada = em.find(Dispositivo.class, play4.id);
		Assert.assertEquals("PlayStation 4 Plus", play4Modificada.getNombre());
	}
	
	/*@Test
	public void persistirReglasYCondiciones() {

	public void laPcLioEstuvoPrendidaUnaVezEnElMes() {
		pc.guardarConsumoDeFecha(LocalDateTime.now(), 20);

		lio.agregarDispositivo(pc);
		em.persist(lio);
		
		//TODO depende de la clase que tiene que crear Nico
		/*int cantidadUsos = (int) em.createQuery("select count(*) from ConsumoDeFecha where Dispositivo.id = " + pc.id + " and ConsumoDeFecha.fecha between current_timestamp() and (current_timestamp() - 31)").getSingleResult();
		
		Assert.assertEquals(1, cantidadUsos);*/
	
	
	@Test
	public void sePersistenLasReglasYSusCondiciones() {
		lio.agregarDispositivo(pc);
		em.persist(lio);
		
		Set<CondicionSobreSensor> condiciones = new HashSet<>();
		Sensor sensorHorasPcEncendida = new SensorHorasEncendido(pc);
		
		em.persist(sensorHorasPcEncendida);
		condiciones.add(new CondicionDeConsumoMayorOIgual(20, sensorHorasPcEncendida));
		
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
