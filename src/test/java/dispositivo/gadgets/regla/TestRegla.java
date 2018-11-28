package dispositivo.gadgets.regla;
import org.junit.Before;
import org.junit.Test;

import fixture.Fixture;
import dispositivo.gadgets.actuador.Actuador;
import dispositivo.gadgets.regla.NoSePuedeUsarReglaSobreDispositivoNoInteligenteException;

import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.*;

public class TestRegla extends Fixture {
	
	@Before
	public void initialize() {
    	actuadorQueApaga = Actuador.ActuadorQueApaga;
    	actuadorQueEnciende = Actuador.ActuadorQueEnciende;
    	
    	when(mockCondicionSobreSensorQueCumple.seCumpleCondicion()).thenReturn(true);
    	when(mockCondicionSobreSensorQueNoCumple.seCumpleCondicion()).thenReturn(false);
	}

    @Test(expected = NoSePuedeUsarReglaSobreDispositivoNoInteligenteException.class)
    public void alCrearReglaConDispositivoEstandarTiraExcepcion() {
    	unaReglaEstricta = new ReglaEstricta(null, condicionesSobreSensorQueCumplen, equipoMusica);
    }
    
    @Test
    public void alEvaluarUnInteligenteQueCumpleSeEnviaSenialApagado() {
    	actuadores.add(actuadorQueApaga);
		unaReglaEstricta = new ReglaEstricta(actuadores, condicionesSobreSensorQueCumplen, televisorSmart);
    	unaReglaEstricta.aplicarSiCumpleCriterio();
    	
    	//verify(mockTelevisorSmartConcreto, times(1)).apagar();
    	assertTrue(mockTelevisorSmartConcreto.estaApagado());
    }
    
    @Test
    public void alEvaluarUnInteligenteQueCumpleSeEnviaSenialEncendido() {
    	actuadores.add(actuadorQueEnciende);
    	unaReglaEstricta = new ReglaEstricta(actuadores, condicionesSobreSensorQueCumplen, televisorSmart);
    	unaReglaEstricta.aplicarSiCumpleCriterio();
    	System.out.println("hola " + (mockTelevisorSmartConcreto.estaEncendido()? "prendido" : "apagado"));
    	//verify(mockTelevisorSmartConcreto, times(1)).encender();
    	assertTrue(mockTelevisorSmartConcreto.estaEncendido());
    }
    
    @Test
    public void alEvaluarUnInteligenteQueNoCumpleNoSeEnviaSenialDApagado() {
    	actuadores.add(actuadorQueApaga);
		unaReglaPermisiva = new ReglaEstricta(actuadores, condicionesSobreSensorQueNoCumplen, televisorSmart);
    	unaReglaPermisiva.aplicarSiCumpleCriterio();
    	
    	//verify(mockTelevisorSmartConcreto, times(0)).apagar();
    	assertTrue(mockTelevisorSmartConcreto.estaEncendido());
    }
}