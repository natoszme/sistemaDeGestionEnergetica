package reportes;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

import db.Reportes;
import fixture.FixtureReportes;

public class TestReportes extends FixtureReportes{
	
	@Test
    public void elConsumoPromedioDeLaTeleSmartEs40() {	
		
		assertEquals(new Reportes().consumoPromedioPorDispositivoDe().stream()
					.filter( objeto -> objeto[1] == "Televisor Smart" ).findFirst().get()[2], 40.0);
    }

}
