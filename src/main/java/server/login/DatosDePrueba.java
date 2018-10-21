package server.login;

import java.time.LocalDateTime;
import java.util.ArrayList;

import javax.persistence.EntityManager;

import org.uqbar.geodds.Point;
import org.uqbarproject.jpa.java8.extras.WithGlobalEntityManager;
import org.uqbarproject.jpa.java8.extras.test.AbstractPersistenceTest;

import categoria.Categoria;
import cliente.Cliente;
import cliente.TipoDocumento;
import dispositivo.Dispositivo;
import dispositivo.DispositivoConcreto;
import tipoDispositivo.DispositivoEstandar;
import tipoDispositivo.DispositivoInteligente;

public class DatosDePrueba extends AbstractPersistenceTest implements WithGlobalEntityManager{
	public void init() {
		EntityManager em = entityManager();
		
		Point ubicacionLaMatanza = new Point(-34.762985, -58.631242);
		Categoria r1 = new Categoria("R1", 0, 150, 18.76, 0.644);
		
		Admin unAdmin = new Admin("admin", "123");
		Cliente unCliente = new Cliente("asaez", "1", "Alejandro", "Saez", TipoDocumento.DNI, 3876675, 43543245, "Macos Sastre 324", r1, new ArrayList<>(), ubicacionLaMatanza);
		
		Dispositivo play4 = new Dispositivo("Play 4", new DispositivoInteligente(DispositivoConcreto.TVINTELIGENTE), 45.987);
		Dispositivo play3 = new Dispositivo("Play 3", new DispositivoEstandar(), 455.987);
		Dispositivo play2 = new Dispositivo("Play 2", new DispositivoEstandar(), 87.987);
		Dispositivo play1 = new Dispositivo("Play 1", new DispositivoEstandar(), 990.987);
		Dispositivo asus = new Dispositivo("Asus ZenBook pro", new DispositivoInteligente(DispositivoConcreto.TVINTELIGENTE), 990.987);
		Dispositivo dell = new Dispositivo("Dell XPS 14", new DispositivoInteligente(DispositivoConcreto.TVINTELIGENTE), 880.99);
		
		em.remove(unCliente);

		play4.guardarConsumoDeFecha(LocalDateTime.now(), 35);
		play4.guardarConsumoDeFecha(LocalDateTime.now(), 28);
		play4.guardarConsumoDeFecha(LocalDateTime.now(), 17);

		asus.guardarConsumoDeFecha(LocalDateTime.now(), 155);
		asus.guardarConsumoDeFecha(LocalDateTime.now(), 148);
		asus.guardarConsumoDeFecha(LocalDateTime.now(), 137);

		dell.guardarConsumoDeFecha(LocalDateTime.now(), 123);
		dell.guardarConsumoDeFecha(LocalDateTime.now(), 254);
		dell.guardarConsumoDeFecha(LocalDateTime.now(), 132);

		unCliente.agregarDispositivo(play4);
		unCliente.agregarDispositivo(play3);
		unCliente.agregarDispositivo(play2);
		unCliente.agregarDispositivo(play1);
		unCliente.agregarDispositivo(asus);
		unCliente.agregarDispositivo(dell);
		
		withTransaction(() -> {
			em.persist(unAdmin);		
			em.persist(r1);			
			em.persist(unCliente);
		});
	}
}
