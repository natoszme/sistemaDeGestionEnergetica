package usuario;

import javax.persistence.Entity;

import db.DatosBasicos;

@Entity
public class Usuario extends DatosBasicos{
	private String usuario;
	private String password;
	private boolean esAdmin;
	
	public Usuario(String usuario, String password, boolean esAdmin) {
		this.usuario = usuario;
		this.password = password;
		this.esAdmin = esAdmin;
	}
	
	public String getUser() {
		return usuario;
	}

	public boolean esAdmin() {
		return esAdmin;
	}
	
}
