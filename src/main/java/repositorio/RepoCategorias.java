package repositorio;
import categoria.Categoria;

public class RepoCategorias extends RepoEnDB<Categoria> {

	
	private static RepoCategorias instancia;
	
	
	public RepoCategorias(String tabla) {
		this.tabla = tabla;
	}
	
	public static  RepoCategorias getInstance() {
		
		if (instancia == null) {
			instancia = new RepoCategorias("Categoria");
		}
		
		return instancia;
	}
	
	public Categoria obtenerCategoriaSegunConsumo(double consumo) {
		return this.obtenerTodas().stream().filter(categoria -> categoria.meCorrespondeElConsumo(consumo)).findFirst().orElse(dameR1());
	}
	
	private Categoria dameR1() {
		return this.obtenerTodas().stream().filter(categoria -> categoria.getNombre() == "R1").findFirst().orElse(null);
	}
}