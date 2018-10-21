package server.login;

public class RepoAdmins extends RepoAutenticables<Admin>{
	
	public RepoAdmins(Class<Admin> entidad) {
		super(entidad);
	}

	private static RepoAdmins instancia;
	
	public static RepoAdmins getInstance() {
		if(instancia == null) {
			instancia = new RepoAdmins(Admin.class);
		}
		return instancia;
	}
}
