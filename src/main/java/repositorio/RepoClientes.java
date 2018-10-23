package repositorio;

import java.util.List;
import java.util.stream.Collectors;

import cliente.Cliente;
import server.login.RepoAutenticables;

public class RepoClientes extends RepoAutenticables<Cliente> {
	private static RepoClientes instancia;
	

	public RepoClientes(String tabla, Class<Cliente> entidad){
		super(tabla, entidad);
		this.tabla = tabla;
	}	
  
	public static RepoClientes getInstance(){
		if (instancia == null) {
			instancia = new RepoClientes("Cliente", Cliente.class);
		}
		return instancia;
	}
	
	public List<Cliente> obtenerAhorradores() {
		return (List<Cliente>) em.createQuery("FROM Cliente c WHERE c.ahorroAutomatico = true").getResultList();
	}

	public Cliente dameCliente(long id) {
		return entityManager().find(Cliente.class, id);
	}
}