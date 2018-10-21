package importador;

import java.util.List;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.uqbar.geodds.Point;
import org.uqbarproject.jpa.java8.extras.WithGlobalEntityManager;
import org.uqbarproject.jpa.java8.extras.test.AbstractPersistenceTest;

import transformador.Transformador;
import importacion.ImportadorTransformadores;
import repositorio.RepoTransformadores;

public class TestImportadorTransformador extends AbstractPersistenceTest implements WithGlobalEntityManager{
	static List<Transformador> transformadores;

	@Before
	public void fixture() {
		ImportadorTransformadores.getInstance().importarJSON();
		transformadores = RepoTransformadores.getInstance().obtenerTodas();
	}

	@Test
	public void laUbicacionDelPrimerTransformadorEs1_1() {		
		Assert.assertEquals(new Point (1, 1).getX(), transformadores.get(0).ubicacion().getX(), 0);
		Assert.assertEquals(new Point (1, 1).getY(), transformadores.get(0).ubicacion().getY(), 0);
	}
	@Test
	public void laUbicacionDelTercerTransformadorEnXNoEs2() {		
		Assert.assertNotEquals(new Point (2, 1).getX(), transformadores.get(2).ubicacion().getX());	
	}
	@Test
	public void existeUnTransformadorConUbicacion2_1() {		
		Assert.assertTrue(transformadores.stream()
				.anyMatch(transformador->(transformador.ubicacion().getX()==new Point (2, 1).getX()) 
						&& transformador.ubicacion().getY()==new Point (2, 1).getY()));	
	}
}