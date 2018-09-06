package simplex;

import static org.junit.Assert.*;

import org.apache.commons.math3.optim.linear.LinearConstraint;
import org.apache.commons.math3.optim.linear.NoFeasibleSolutionException;
import org.apache.commons.math3.util.Pair;
import org.junit.Assert;
import org.junit.Test;

import dispositivo.Dispositivo;
import fixture.FixtureSimplex;
import repositorio.RepoRestriccionesUsoDispositivo;
import tipoDispositivo.DispositivoInteligente;

import java.util.Arrays;
import java.util.List;

public class TestSimplex extends FixtureSimplex {
	
	@Test
	public void TodasLasHorasDevueltasPorElSimplexCumplenLasRestriccionesDeCadaDispositivo() {
		OptimizadorUsoDispositivos optimizadorDeLio = new OptimizadorUsoDispositivos(lio);
		Boolean cumpleRestriccionesDeHoras = lio.getDispositivos().stream().allMatch(dispositivo -> this.cumpleConLaRestriccionParaElCliente(dispositivo, optimizadorDeLio));
		assertTrue(cumpleRestriccionesDeHoras);
	}

	public boolean cumpleConLaRestriccionParaElCliente(Dispositivo dispositivo, OptimizadorUsoDispositivos optimizador) {		
		double horaOptima = optimizador.obtenerHorasOptimasPara(dispositivo);		
		
		return RepoRestriccionesUsoDispositivo.getInstance().dameRestriccionMaximaDe(dispositivo) >= horaOptima 
				&& RepoRestriccionesUsoDispositivo.getInstance().dameRestriccionMinimaDe(dispositivo) <= horaOptima;
	}
	
	@Test
	public void elConsumoTotaDeLiolMaxDaMenosQue612() {
		OptimizadorUsoDispositivos optimizadorDeLio = new OptimizadorUsoDispositivos(lio);
		
		//TODO esto es realmente imprescindible?
		lio.getDispositivos().stream().forEach(dispositivo -> {
			System.out.println("KwXhora:");
			System.out.println(dispositivo.getKwPorHora());
			System.out.println("Horas:");
			System.out.println(optimizadorDeLio.obtenerHorasOptimasPara(dispositivo));
		});
		
		double consumoTotal = lio.getDispositivos().stream().mapToDouble(dispositivo -> 
			dispositivo.getKwPorHora() * optimizadorDeLio.obtenerHorasOptimasPara(dispositivo)															
		).sum();
		
		System.out.println("Consumo total:");
		System.out.println(consumoTotal);
		
		assertTrue(consumoTotal <= 612.00);
	}
	
	@Test
	public void ElSimplexEnElPrimerDispositivoDeNicoEs360() {		
		OptimizadorUsoDispositivos optimizadorDeNico = new OptimizadorUsoDispositivos(nico);
		List<Pair<Dispositivo, Double>> horasSimplex = optimizadorDeNico.optimizarUsoDispositivos();
				
		assertEquals(360, horasSimplex.get(0).getSecond(), 0);
	}
	
	@Test
	public void ElSegundoDispositivoDeNicoEstaMasTiempoPrendidoQueSuRestriccionDelSimplex() {		
		OptimizadorUsoDispositivos optimizadorDeNico = new OptimizadorUsoDispositivos(nico);
		List<Pair<Dispositivo, Double>> horasSimplex = optimizadorDeNico.optimizarUsoDispositivos();
						
		assertTrue(100 > horasSimplex.get(1).getSecond());
	}
	
	@Test
	public void LosCoeficientesDeLaFuncionEconomicaSonTodos1() {
		OptimizadorUsoDispositivos optimizadorDeLio = new OptimizadorUsoDispositivos(lio);
		assertTrue (Arrays.stream(optimizadorDeLio.getCoeficientesFuncionEconomica()).allMatch(coeficiente -> (coeficiente == 1)));
	}
	
	@Test
	public void EnLaFuncionEconomicaHayTantosCoeficientesComoDispositivosTieneElCliente() {
		OptimizadorUsoDispositivos optimizadorDeLio = new OptimizadorUsoDispositivos(lio);
		assertEquals(lio.cantidadDispositivos(), optimizadorDeLio.getCoeficientesFuncionEconomica().length, 0);
	}
	
	@Test (expected = NoFeasibleSolutionException.class)
	public void unDispositivoIrresolubleNoPuedeSerOptimizado() {
		Dispositivo jacuzzi = new Dispositivo("jacuzzi", new DispositivoInteligente(null), 7);
		RepoRestriccionesUsoDispositivo.getInstance().agregarEntidad(new RestriccionUsoDispositivo(jacuzzi, 100, 200, null));
		
		OptimizadorUsoDispositivos optimizadorDeLio = new OptimizadorUsoDispositivos(lio);
		lio.agregarDispositivo(jacuzzi);
		optimizadorDeLio.obtenerHorasOptimasPara(jacuzzi);
	}
	
	@Test
	public void lasRestriccionesHorariasDelOptimizadorDeLioEstanCorrectamenteArmadas() {
		OptimizadorUsoDispositivos optimizadorDeLio = new OptimizadorUsoDispositivos(lio);
		List<LinearConstraint> restriccionesDeDispositivos = optimizadorDeLio.getRestrccionesDeHora();

		double m [][] = {	{1,0,0,0,0},
							{1,0,0,0,0},
							{0,1,0,0,0},
							{0,1,0,0,0},
							{0,0,1,0,0},
							{0,0,1,0,0}, 
							{0,0,0,1,0},
							{0,0,0,1,0},
							{0,0,0,0,1},
							{0,0,0,0,1}
						};
		double m2 [][] = new double[10] [5];
		restriccionesDeDispositivos.forEach( restriccion -> m2[restriccionesDeDispositivos.indexOf(restriccion)]= restriccion.getCoefficients().toArray());

		Assert.assertArrayEquals(m, m2);
		
	}
	
}