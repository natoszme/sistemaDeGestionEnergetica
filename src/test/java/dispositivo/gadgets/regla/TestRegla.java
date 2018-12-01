package dispositivo.gadgets.regla;
import org.junit.Before;
import org.junit.Test;

import fixture.Fixture;
import tipoDispositivo.DispositivoInteligente;
import dispositivo.Dispositivo;
import dispositivo.gadgets.actuador.Actuador;
import dispositivo.gadgets.regla.NoSePuedeUsarReglaSobreDispositivoNoInteligenteException;

import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.*;

import java.util.HashSet;
import java.util.Set;

public class TestRegla extends Fixture {
	
	@Before
	public void initialize() {
    	actuadorQueApaga = Actuador.ActuadorQueApaga;
    	actuadorQueEnciende = Actuador.ActuadorQueEnciende;
    	
    	when(mockCondicionSobreSensorQueCumple.seCumpleCondicion()).thenReturn(true);
    	when(mockCondicionSobreSensorQueNoCumple.seCumpleCondicion()).thenReturn(false);
    	
    	televisorSmart = new Dispositivo("Televisor Smart", new DispositivoInteligente(mockTelevisorSmartConcretoSpy), 90);
	}

    @Test(expected = NoSePuedeUsarReglaSobreDispositivoNoInteligenteException.class)
    public void alCrearReglaConDispositivoEstandarTiraExcepcion() {
    	unaReglaEstricta = new ReglaEstricta(null, condicionesSobreSensorQueCumplen, equipoMusica);
    }
    
    @Test
    public void alEvaluarUnInteligenteQueCumpleSeEnviaSenialApagado() {
    	actuadores.add(actuadorQueApaga);
		otraReglaEstricta = new ReglaEstricta(actuadores, condicionesSobreSensorQueCumplen, televisorSmart);
    	otraReglaEstricta.aplicarSiCumpleCriterio();

    	verify(mockTelevisorSmartConcretoSpy, times(1)).apagar();
    }
    
    @Test
    public void alEvaluarUnInteligenteQueCumpleSeEnviaSenialEncendido() {
    	actuadores.add(actuadorQueEnciende);
    	unaReglaEstricta = new ReglaEstricta(actuadores, condicionesSobreSensorQueCumplen, televisorSmart);
    	unaReglaEstricta.aplicarSiCumpleCriterio();
    	
    	verify(mockTelevisorSmartConcretoSpy, times(1)).encender();
    }
    
    @Test
    public void alEvaluarUnInteligenteQueNoCumpleNoSeEnviaSenialDeApagado() {
    	actuadores.add(actuadorQueApaga);
		unaReglaPermisiva = new ReglaEstricta(actuadores, condicionesSobreSensorQueNoCumplen, televisorSmart);
    	unaReglaPermisiva.aplicarSiCumpleCriterio();
    	
    	verify(mockTelevisorSmartConcretoSpy, times(0)).apagar();
    }
}