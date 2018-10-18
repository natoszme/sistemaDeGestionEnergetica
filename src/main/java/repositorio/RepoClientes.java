package repositorio;

import java.util.List;
import java.util.stream.Collectors;

import cliente.Cliente;

public class RepoClientes extends RepoEnDB<Cliente> {
	private static RepoClientes instancia;
	
	public RepoClientes(String tabla) {
		super(tabla);
	}
	
	
	public static RepoClientes getInstance(){
		if (instancia == null) {
			instancia = new RepoClientes("Cliente");
		}
		return instancia;
	}
	
	public List<Cliente> obtenerAhorradores() {
		return this.obtenerTodas().stream().filter(Cliente::permiteAhorroAutomatico).collect(Collectors.toList());
	}
}