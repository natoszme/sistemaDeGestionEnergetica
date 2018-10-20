package repositorio;

import java.util.List;
import java.util.stream.Collectors;

import cliente.Cliente;
import server.login.RepoAutenticables;

public class RepoClientes extends RepoAutenticables<Cliente> {
	private static RepoClientes instancia;
	
	public RepoClientes(String tabla, Class<Cliente> entidad) {
		super(entidad);
		this.tabla = tabla;
	}	
	
	public static RepoClientes getInstance(){
		if (instancia == null) {
			instancia = new RepoClientes("Cliente", Cliente.class);
		}
		return instancia;
	}
	
	public List<Cliente> obtenerAhorradores() {
		return this.obtenerTodas().stream().filter(Cliente::permiteAhorroAutomatico).collect(Collectors.toList());
	}

	@Override
	public void limpiarEntidades() {
		// TODO Auto-generated method stub
		
	}

	public Cliente dameCliente(long id) {
		return entityManager().find(Cliente.class, id);
	}
}