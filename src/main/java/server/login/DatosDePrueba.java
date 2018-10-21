package server.login;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.EntityManager;

import org.uqbar.geodds.Point;
import org.uqbarproject.jpa.java8.extras.WithGlobalEntityManager;
import org.uqbarproject.jpa.java8.extras.test.AbstractPersistenceTest;

import categoria.Categoria;
import cliente.Cliente;
import cliente.TipoDocumento;
import dispositivo.Dispositivo;
import dispositivo.DispositivoConcreto;
import dispositivo.gadgets.actuador.Actuador;
import dispositivo.gadgets.regla.CondicionDeConsumoMayorOIgual;
import dispositivo.gadgets.regla.CondicionSobreSensor;
import dispositivo.gadgets.regla.Regla;
import dispositivo.gadgets.regla.ReglaEstricta;
import dispositivo.gadgets.sensor.SensorHorasEncendido;
import repositorio.RepoReglas;
import tipoDispositivo.DispositivoEstandar;
import tipoDispositivo.DispositivoInteligente;

public class DatosDePrueba extends AbstractPersistenceTest implements WithGlobalEntityManager{
	public void init() {
		EntityManager em = entityManager();
		
		Point ubicacionLaMatanza = new Point(-34.762985, -58.631242);
		Categoria r1 = new Categoria("R1", 0, 150, 18.76, 0.644);
		
		Admin unAdmin = new Admin("admin", "123");
		Cliente unCliente = new Cliente("asaez", "1", "Alejandro", "Saez", TipoDocumento.DNI, 3876675, 43543245, "Macos Sastre 324", r1, new ArrayList<>(), ubicacionLaMatanza);
		
		Set<Actuador> actuadores = new HashSet<>();
		Set<CondicionSobreSensor> condiciones = new HashSet<>();

		Actuador actuadorQueApaga = Actuador.ActuadorQueApaga;
		SensorHorasEncendido sensorHoras = new SensorHorasEncendido();
		CondicionDeConsumoMayorOIgual condicionConsumo = new CondicionDeConsumoMayorOIgual(300, sensorHoras);
		actuadores.add(actuadorQueApaga);
		condiciones.add(condicionConsumo);

		Dispositivo teleSmart = new Dispositivo("Televisor Smart", new DispositivoInteligente(DispositivoConcreto.TVINTELIGENTE), 0.9);
		Regla unaReglaEstricta = new ReglaEstricta(actuadores, condiciones, teleSmart);
		
		em.remove(unCliente);
		
		withTransaction(() -> {
			em.persist(unAdmin);
			
			em.persist(r1);
			
			unCliente.agregarDispositivo(teleSmart);
			unCliente.agregarDispositivo(new Dispositivo("Play 4", new DispositivoEstandar(), 45.987));
			unCliente.agregarDispositivo(new Dispositivo("Play 3", new DispositivoEstandar(), 455.987));
			unCliente.agregarDispositivo(new Dispositivo("Play 2", new DispositivoEstandar(), 87.987));
			unCliente.agregarDispositivo(new Dispositivo("Play 1", new DispositivoEstandar(), 990.987));
			unCliente.agregarDispositivo(new Dispositivo("Play 0", new DispositivoEstandar(), 7.987));
			unCliente.agregarDispositivo(new Dispositivo("Play -1", new DispositivoEstandar(), 45.987));
			unCliente.agregarDispositivo(new Dispositivo("Play -2", new DispositivoEstandar(), 35.987));

			em.persist(unCliente);

			RepoReglas.getInstance().agregarEntidad(unaReglaEstricta);
		});
	}
}
