package categoria;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import fixture.Fixture;
import repositorio.Repo;
import repositorio.RepoCategorias;

public
class TestCategoria extends Fixture {

	RepoCategorias repoCategorias = RepoCategorias.getInstance();
	
	@Before
	public void before() {
		//repoCategorias.limpiarEntidades();
	}
	
	@Test
	public void aR1LeCorrespondeElConsumo150() {
		System.out.print("\nINICIO PRINT\n");
		System.out.print("\nCONSUMO MAXIMO ="+repoCategorias.obtenerCategoriaPorNombre("R1").getConsumoMaximo() + "\n");
		System.out.print("\nFIN PRINT\n");
		Assert.assertTrue(repoCategorias.obtenerCategoriaPorNombre("R1").meCorrespondeElConsumo(150));
	}

	@Test
	public void aR1LeCorrespondeElConsumo0Con01() {
		Assert.assertTrue(repoCategorias.obtenerCategoriaPorNombre("R1").meCorrespondeElConsumo(0.01));
	}
	
	@Test
	public void aR9LeCorrespondeElConsumo1909Con55() {
		Assert.assertTrue(r9.meCorrespondeElConsumo(1909.55));
	}
	
	@Test
	public void aR1NoLeCorrespondeElConsumo0() {
		Assert.assertFalse(r1.meCorrespondeElConsumo(0));
	}	
}