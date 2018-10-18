package repositorio;

import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

import javax.persistence.EntityManager;

import org.uqbarproject.jpa.java8.extras.WithGlobalEntityManager;

import cliente.Cliente;
import transformador.Transformador;

import repositorio.RepoClientes;

public class RepoTransformadores extends RepoEnDB<Transformador> implements WithGlobalEntityManager{
	
	private static RepoTransformadores instancia;
	
	public static RepoTransformadores getInstance() {
		if(instancia == null) {
			instancia = new RepoTransformadores("Transformador");
		}
		return instancia;
	}
	
	public RepoTransformadores(String tabla) {
		super(tabla);
	}
	
	public List<Cliente> obtenerClientesDe(Transformador transformador){	
		return RepoClientes.getInstance().obtenerTodas().stream().filter(cliente -> leCorresponde(cliente, transformador)).collect(Collectors.toList());	
	}	
		
	private boolean leCorresponde(Cliente cliente, Transformador transformador) {	
		return transformadorMasCercanoA(cliente).equals(transformador); /*&& transformadorEnLaMismaZonaQue(cliente, transformador)*/	
	}
	
	//esto no es necesari: un trasnformador de una zona puede medir el consumo de un cliente de otra zona, la consigna no especifica
	/*private boolean transformadorEnLaMismaZonaQue(Cliente cliente, Transformador transformador) {
		RepoZonas.getInstance().zonaDe(transformador) == RepoZonas.getInstance().zonaDe(cliente);
	}*/
		
	private Transformador transformadorMasCercanoA(Cliente cliente) {	
		return obtenerTodas().stream().min(Comparator.comparing(unTransformador -> unTransformador.distanciaA(cliente))).get();	
	}
	
	public long obtenerCantidadTransformadores() {
		EntityManager em = entityManager();
		return (long) em.createQuery("SELECT COUNT(*) FROM Transformador").getSingleResult();
	}
	
}
