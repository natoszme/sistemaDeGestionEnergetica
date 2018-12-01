package fixture;

import static org.mockito.Mockito.when;

import java.time.LocalDateTime;

import org.junit.After;
import org.junit.Before;

import repositorio.RepoClientes;
import repositorio.RepoTransformadores;

public class FixtureConsumoMasivo extends Fixture{
	
	@After
	public void after() {
		//TODO por que sin estas dos rompe?
		//RepoTransformadores.getInstance().limpiarEntidades();
		//RepoClientes.getInstance().limpiarEntidades();
	}
	
	@Before
	public void before() {
		//TODO misma dudam aca abajo:
		//RepoTransformadores.getInstance().limpiarEntidades();
		//.RepoClientes.getInstance().limpiarEntidades();
		
		RepoTransformadores.getInstance().agregarEntidad(transformadorLaMatanza);
		RepoTransformadores.getInstance().agregarEntidad(transformadorPalermo);
		RepoTransformadores.getInstance().agregarEntidad(transformadorCaballito);
		
		//alejandro es de la matanza
		//when(mockAireConcreto.consumoActual()).thenReturn(180.0);
		aireAcondicionado.guardarConsumoDeFecha(LocalDateTime.now(), 160);
		alejandro.agregarDispositivo(aireAcondicionado);
		RepoClientes.getInstance().agregarEntidad(alejandro);
		
		//nico es de palermo
		//when(mockTelevisorSmartConcreto.consumoActual()).thenReturn(50.0);
		televisorSmart.guardarConsumoDeFecha(LocalDateTime.now(), 30);
		nico.agregarDispositivo(televisorSmart);
		RepoClientes.getInstance().agregarEntidad(nico);
		
		yanina.agregarDispositivo(aireAcondicionado);
		RepoClientes.getInstance().agregarEntidad(yanina);
		
	}
}
