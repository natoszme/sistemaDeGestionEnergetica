package repositorio;

import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

import cliente.Cliente;
import transformador.Transformador;

public class RepoTransformadores extends Repo<Transformador>{
	
	private static RepoTransformadores instancia;
	
	public static RepoTransformadores getInstance() {
		if(instancia == null) {
			instancia = new RepoTransformadores();
		}
		return instancia;
	}
	
	public List<Cliente> obtenerClientesDe(Transformador transformador){
		return RepoClientes.getInstance().obtenerTodas().stream().filter(cliente -> leCorresponde(cliente, transformador)).collect(Collectors.toList());
	}
	
	private boolean leCorresponde(Cliente cliente, Transformador transformador) {
		return transformadorMasCercanoA(cliente).equals(transformador);
	}
	
	private Transformador transformadorMasCercanoA(Cliente cliente) {
		return entidades.stream().min(Comparator.comparing(unTransformador -> unTransformador.distanciaA(cliente))).get();
	}
}
