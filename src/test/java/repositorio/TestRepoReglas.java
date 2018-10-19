package repositorio;

import java.util.Arrays;
import java.util.HashSet;

import javax.persistence.EntityManager;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;

import dispositivo.Dispositivo;
import dispositivo.DispositivoConcreto;
import dispositivo.DispositivosBaseFactory;
import dispositivo.gadgets.actuador.Actuador;
import dispositivo.gadgets.regla.CondicionDeConsumoMayorOIgual;
import dispositivo.gadgets.regla.CondicionSobreSensor;
import dispositivo.gadgets.regla.ReglaPermisiva;
import dispositivo.gadgets.sensor.SensorHorasEncendido;
import fixture.Fixture;

public class TestRepoReglas extends Fixture{
	
	DispositivoConcreto mockTv40DeNico = Mockito.mock(DispositivoConcreto.class);
	Dispositivo tele40DeNico = DispositivosBaseFactory.getInstance().tvLed40Pulgadas(mockTv40DeNico);
	
	
	@Before
	public void before() {
		//RepoReglas.getInstance().limpiarEntidades();
		EntityManager em = entityManager();
		nico.agregarDispositivo(tele40DeNico);
		em.merge(nico);
		//em.merge(tele40DeNico);
		SensorHorasEncendido sensorEncendido = new SensorHorasEncendido(tele40DeNico);
		//em.merge(sensorEncendido);
		CondicionDeConsumoMayorOIgual condicionMayora10 = new CondicionDeConsumoMayorOIgual(10, sensorEncendido);
		//em.merge(condicionMayora10);
		//System.out.print(em.find(CondicionDeConsumoMayorOIgual.class, condicionMayora10).toString());
		RepoReglas.getInstance().agregarEntidad(new ReglaPermisiva(new HashSet<Actuador>(Arrays.asList(Actuador.ActuadorQueApaga)), new HashSet<CondicionSobreSensor>(Arrays.asList(condicionMayora10)), tele40DeNico));
	}
	
	@Test
	public void tieneLaReglaDeLaTeleDeNico() {				
		Assert.assertTrue(RepoReglas.getInstance().tieneReglaDe(tele40DeNico));
	}
	
	@Test
	public void noTieneReglaDeDispositivoSimilarConOtroConcreto() {
		DispositivoConcreto mockTv40DeYanina = Mockito.mock(DispositivoConcreto.class);
	
		Dispositivo tele40DeYanina = DispositivosBaseFactory.getInstance().tvLed40Pulgadas(mockTv40DeYanina);
		
		Assert.assertFalse(RepoReglas.getInstance().tieneReglaDe(tele40DeYanina));
	}
}
