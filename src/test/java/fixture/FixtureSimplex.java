package fixture;

import java.time.LocalDateTime;

import org.junit.Before;

import dispositivo.Dispositivo;
import dispositivo.DispositivosBaseFactory;
import dispositivo.gadgets.actuador.Actuador;
import repositorio.RepoClientes;
import repositorio.RepoRestriccionesUsoDispositivo;
import simplex.RestriccionUsoDispositivo;

public class FixtureSimplex extends Fixture{
	
	protected Dispositivo aire2200Frigorias = DispositivosBaseFactory.getInstance().aire2200Frigorias(mockAireConcreto);
	protected Dispositivo aire3500Frigorias = DispositivosBaseFactory.getInstance().aire3500Frigorias(mockAireConcreto);
	protected Dispositivo compu = DispositivosBaseFactory.getInstance().computadoraDeEscritorio(mockPcConcreta);
	protected Dispositivo lavarropas = DispositivosBaseFactory.getInstance().lavarropasAutomatico5kg(mockLavarropas);
	protected Dispositivo microondas = DispositivosBaseFactory.getInstance().microondas(mockMicroondas);
	protected Dispositivo tv40 = DispositivosBaseFactory.getInstance().tvLed40Pulgadas(mockTv40);
	
	public FixtureSimplex() {
		super();
	}
	
	@Before
	public void before() {		
		lio.limpiarDispositivos();
		lio.agregarDispositivo(aire2200Frigorias);
		lio.agregarDispositivo(aire3500Frigorias);
		lio.agregarDispositivo(compu);
		lio.agregarDispositivo(lavarropas);
		lio.agregarDispositivo(microondas);
		
		nico.limpiarDispositivos();
		nico.agregarDispositivo(aire3500Frigorias);
		nico.agregarDispositivo(lavarropas);
		
		yanina.agregarDispositivo(tv40);
		yanina.agregarDispositivo(tvNormal);
		
		RepoClientes.getInstance().agregarEntidad(nico);
		RepoClientes.getInstance().agregarEntidad(lio);
		RepoClientes.getInstance().agregarEntidad(yanina);
		
		aire2200Frigorias.guardarConsumoDeFecha(LocalDateTime.now(), 325);
		
		lavarropas.guardarConsumoDeFecha(LocalDateTime.now(), 50);
		
		microondas.guardarConsumoDeFecha(LocalDateTime.now(), 100);
		
		RepoRestriccionesUsoDispositivo.getInstance().agregarEntidad(new RestriccionUsoDispositivo(aire2200Frigorias, 90, 360, Actuador.ActuadorQueApaga));
		RepoRestriccionesUsoDispositivo.getInstance().agregarEntidad(new RestriccionUsoDispositivo(compu, 90, 360, Actuador.ActuadorQueApaga));
		RepoRestriccionesUsoDispositivo.getInstance().agregarEntidad(new RestriccionUsoDispositivo(aire3500Frigorias, 90, 360, Actuador.ActuadorQueApaga));
		RepoRestriccionesUsoDispositivo.getInstance().agregarEntidad(new RestriccionUsoDispositivo(lavarropas, 6, 30, Actuador.ActuadorQuePoneEnAhorroDeEnergia));
		RepoRestriccionesUsoDispositivo.getInstance().agregarEntidad(new RestriccionUsoDispositivo(microondas, 6, 15, Actuador.ActuadorQueApaga));
		RepoRestriccionesUsoDispositivo.getInstance().agregarEntidad(new RestriccionUsoDispositivo(tvNormal, 50, 400, Actuador.ActuadorQueApaga));
		RepoRestriccionesUsoDispositivo.getInstance().agregarEntidad(new RestriccionUsoDispositivo(tv40, 50, 400, Actuador.ActuadorQueApaga));	
	}
}
