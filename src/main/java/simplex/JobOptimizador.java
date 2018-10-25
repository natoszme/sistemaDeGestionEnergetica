package simplex;

import cliente.Cliente;
import dispositivo.Dispositivo;
import dispositivo.gadgets.actuador.Actuador;
import dispositivo.gadgets.regla.CondicionDeConsumoMayorOIgual;
import dispositivo.gadgets.regla.CondicionSobreSensor;
import dispositivo.gadgets.regla.Regla;
import dispositivo.gadgets.regla.ReglaEstricta;
import dispositivo.gadgets.sensor.SensorHorasEncendido;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

import repositorio.RepoClientes;
import repositorio.RepoReglas;
import repositorio.RepoRestriccionesUsoDispositivo;

public class JobOptimizador {
	
	public static JobOptimizador instancia;
	public static JobOptimizador getInstance() {
		if (instancia == null) {
			instancia = new JobOptimizador();
		}
		return instancia;
	}
	
	public void ejecutar() {
		RepoClientes.getInstance().obtenerAhorradores().forEach(cliente -> this.obtenerMaximosYGenerarReglas(cliente));
		RepoReglas.getInstance().ejecutarTodas();
	}

	public void obtenerMaximosYGenerarReglas(Cliente cliente) {
		new OptimizadorUsoDispositivos(cliente).obtenerMaximosDeConsumoDeInteligentes().forEach(
			par -> this.generarRegla(par.getFirst(), par.getSecond())
		);
	}
	
	private void generarRegla(Dispositivo dispositivo, double consumoMaximo) {
		CondicionDeConsumoMayorOIgual condicion = new CondicionDeConsumoMayorOIgual(consumoMaximo, new SensorHorasEncendido(dispositivo));
		Regla regla = new ReglaEstricta(obtenerActuadoresDe(dispositivo), new HashSet<CondicionSobreSensor>(Arrays.asList(condicion)), dispositivo);
		RepoReglas.getInstance().agregarEntidad(regla);
	}
	
	private Set<Actuador> obtenerActuadoresDe(Dispositivo dispositivo) {
		return new HashSet<Actuador>(Arrays.asList(RepoRestriccionesUsoDispositivo.getInstance().dameAccionDe(dispositivo)));
	}	
}