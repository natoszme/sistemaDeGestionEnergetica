package usuario;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.OneToOne;

import cliente.Cliente;
import db.DatosBasicos;

@Entity
public class Usuario extends DatosBasicos{
	private String username;
	private String password;
	private boolean esAdmin;
	
	@OneToOne(fetch = FetchType.LAZY)
	private Cliente cliente;
	
	public Usuario(String username, String password, boolean esAdmin, Cliente cliente) {
		this.username = username;
		this.password = password;
		this.esAdmin = esAdmin;
		this.cliente = cliente;
	}
	
	public String getUsername() {
		return username;
	}

	public boolean esAdmin() {
		return esAdmin;
	}

	public Cliente getCliente() {
		return cliente;
	}
	
}
