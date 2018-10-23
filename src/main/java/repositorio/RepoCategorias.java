package repositorio;
import javax.persistence.NoResultException;

import categoria.Categoria;

public class RepoCategorias extends RepoEnDB<Categoria> {

	
	private static RepoCategorias instancia;
	
	
	public RepoCategorias(String tabla) {
		super(tabla);
	}
	
	public static  RepoCategorias getInstance() {
		
		if (instancia == null) {
			instancia = new RepoCategorias("Categoria");
		}
		
		return instancia;
	}
	
	public Categoria obtenerCategoriaSegunConsumo(double consumo) {
		String query = "FROM Categoria c WHERE :consumo > c.consumoMinimo AND :consumo <= c.consumoMaximo";
		
		// TODO: esta muy mal hacer esto?		
		try {
			return (Categoria) em.createQuery(query, Categoria.class).setParameter("consumo", consumo).getSingleResult();
		} catch(NoResultException e) {
			return dameR1();
		}
	}
	
	private Categoria dameR1() {
		// TODO: que deberia hacerse si no encuentra el resultado		
		return obtenerCategoriaPorNombre("R1");
	}
	public Categoria obtenerCategoriaPorNombre(String nombre) {
		return (Categoria) em.createQuery("FROM Categoria c WHERE c.nombre = :nombre", Categoria.class).setParameter("nombre", nombre).getSingleResult();
	}
}