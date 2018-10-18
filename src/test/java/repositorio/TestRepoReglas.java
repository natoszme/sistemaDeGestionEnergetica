package repositorio;

import java.util.Arrays;
import java.util.HashSet;

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
		
		RepoReglas.getInstance().agregarEntidad(new ReglaPermisiva(new HashSet<Actuador>(Arrays.asList(Actuador.ActuadorQueApaga)), new HashSet<CondicionSobreSensor>(Arrays.asList(new CondicionDeConsumoMayorOIgual(10, new SensorHorasEncendido(tele40DeNico)))), tele40DeNico));
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
