package dispositivo.gadgets.actuador;

import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.times; 
import static org.mockito.Mockito.verify;

import org.junit.Test;

import fixture.Fixture;

public class TestActuadores extends Fixture {
    
    @Test
    public void elActuadorQueApagaHaceQueSeEnvieLaSenialDeApagado() {
		actuadorQueApaga.actuarSobre(pc);
		//verify(mockPcConcreta, times(1)).apagar();
		assertTrue(mockPcConcreta.estaApagado());
    }
    
    @Test
    public void elActuadorQueEnciendeUnAireHaceQueSeEnvieLaSenialDeApagado() {
		actuadorQueEnciende.actuarSobre(aireAcondicionado);    	
		//verify(mockAireConcreto, times(1)).encender();
		assertTrue(mockAireConcreto.estaEncendido());
    }   
}


