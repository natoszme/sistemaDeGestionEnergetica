package server.login;

import javax.persistence.Entity;
import db.DatosBasicos;

@Entity
public class Admin extends DatosBasicos implements Autenticable{
	private String username;
	private String password;
	
	public Admin(String username, String password) {
		this.username = username;
		this.password = password;
	}
	
	public Admin() {}
	
	public String getUsername() {
		return username;
	}

	public long id() {
		return id;
	}	
}
