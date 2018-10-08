package repositorio;

import java.util.List;
import java.util.stream.Collectors;

import cliente.Cliente;

public class RepoClientes extends RepoEnMemoria<Cliente> {
	private static RepoClientes instancia;
	
	public static RepoClientes getInstance(){
		if (instancia == null) {
			instancia = new RepoClientes();
		}
		return instancia;
	}
	
	public List<Cliente> obtenerAhorradores() {
		return entidades.stream().filter(Cliente::permiteAhorroAutomatico).collect(Collectors.toList());
	}
}