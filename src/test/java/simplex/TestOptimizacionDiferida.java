package simplex;

import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.when;
import static org.mockito.Mockito.verify;

import org.junit.Test;

import fixture.FixtureSimplex;
import repositorio.RepoReglas;
import tipoDispositivo.CalculadoraHorasMesActual;

public class TestOptimizacionDiferida extends FixtureSimplex {
	
	@Test
    public void elSimplexDiferidoNoApagaUnNoInteligente() {	
		JobOptimizador job = JobOptimizador.getInstance();
		job.ejecutar();
		
		//verify(mockTelevisorNormalConcreto, times(0)).apagar();
		assertTrue(mockTelevisorNormalConcreto.estaEncendido());
    }
	 
	@Test
	public void alOptimizarNoSePoneEnAhorroDeEnergiaElLavarropasEstandar() {
		JobOptimizador job = JobOptimizador.getInstance();
		job.ejecutar();
		
		//verify(mockLavarropas, times(0)).ponerEnAhorroDeEnergia();
		assertTrue(!mockLavarropas.estaEnAhorroEnergia());
	}
	
	@Test
    public void elSimplexDiferidoAgregaLaReglaParaApagarElTelevisor() {	    
		JobOptimizador job = JobOptimizador.getInstance();
		//when(mockTv40.horasEncendidoEn(CalculadoraHorasMesActual.getInstance().horasDeMesActual())).thenReturn(80000.0);
		job.ejecutar(); 
	
		assertTrue(RepoReglas.getInstance().tieneReglaDe(tv40));
    }
}
