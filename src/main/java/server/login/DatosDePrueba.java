package server.login;

import java.util.ArrayList;

import javax.persistence.EntityManager;

import org.uqbar.geodds.Point;
import org.uqbarproject.jpa.java8.extras.WithGlobalEntityManager;
import org.uqbarproject.jpa.java8.extras.test.AbstractPersistenceTest;

import categoria.Categoria;
import cliente.Cliente;
import cliente.TipoDocumento;
import dispositivo.Dispositivo;
import tipoDispositivo.DispositivoEstandar;

public class DatosDePrueba extends AbstractPersistenceTest implements WithGlobalEntityManager{
	public void init() {
		EntityManager em = entityManager();
		
		Point ubicacionLaMatanza = new Point(-34.762985, -58.631242);
		Categoria r1 = new Categoria("R1", 0, 150, 18.76, 0.644);
		
		Admin unAdmin = new Admin("admin", "123");
		Cliente unCliente = new Cliente("asaez", "1", "Alejandro", "Saez", TipoDocumento.DNI, 3876675, 43543245, "Macos Sastre 324", r1, new ArrayList<>(), ubicacionLaMatanza);
		
		em.remove(unCliente);
		
		withTransaction(() -> {
			em.persist(unAdmin);
		
			em.persist(r1);
			
			unCliente.agregarDispositivo(new Dispositivo("Play 4", new DispositivoEstandar(), 45.987));
			unCliente.agregarDispositivo(new Dispositivo("Play 3", new DispositivoEstandar(), 455.987));
			unCliente.agregarDispositivo(new Dispositivo("Play 2", new DispositivoEstandar(), 87.987));
			unCliente.agregarDispositivo(new Dispositivo("Play 1", new DispositivoEstandar(), 990.987));
			unCliente.agregarDispositivo(new Dispositivo("Play 0", new DispositivoEstandar(), 7.987));
			unCliente.agregarDispositivo(new Dispositivo("Play -1", new DispositivoEstandar(), 45.987));
			unCliente.agregarDispositivo(new Dispositivo("Play -2", new DispositivoEstandar(), 35.987));	
			
			em.persist(unCliente);
		});
	}
}
