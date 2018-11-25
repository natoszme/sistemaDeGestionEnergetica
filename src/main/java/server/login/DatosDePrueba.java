package server.login;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.EntityManager;

import org.uqbar.geodds.Point;
import org.uqbarproject.jpa.java8.extras.WithGlobalEntityManager;
import org.uqbarproject.jpa.java8.extras.transaction.TransactionalOps;

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
import repositorio.RepoRestriccionesUsoDispositivo;
import simplex.RestriccionUsoDispositivo;
import tipoDispositivo.DispositivoEstandar;
import tipoDispositivo.DispositivoInteligente;

public class DatosDePrueba implements TransactionalOps, WithGlobalEntityManager{
	public void init() {
		EntityManager em = entityManager();
		
		Point ubicacionLaMatanza = new Point(-34.762985, -58.631242);
		Categoria r1 = new Categoria("R1", 0, 150, 18.76, 0.644);
		
		Admin unAdmin = new Admin("admin", "123");
		Cliente unCliente = new Cliente("asaez", "1", "Alejandro", "Saez", TipoDocumento.DNI, 3876675, 43543245, "Macos Sastre 324", r1, new ArrayList<>(), ubicacionLaMatanza);
		Cliente otroCliente = new Cliente("mkrane", "123", "Matias", "Kranevitter", TipoDocumento.DNI, 3696675, 43543245, "Figueroa Alcorta", r1, new ArrayList<>(), ubicacionLaMatanza);
		
		Set<Actuador> actuadores = new HashSet<>();
		Set<CondicionSobreSensor> condiciones = new HashSet<>();
		
		Actuador actuadorQueApaga = Actuador.ActuadorQueApaga;
		SensorHorasEncendido sensorHoras = new SensorHorasEncendido();
		CondicionDeConsumoMayorOIgual condicionConsumo = new CondicionDeConsumoMayorOIgual(300, sensorHoras);
		actuadores.add(actuadorQueApaga);
		condiciones.add(condicionConsumo);
		
		Dispositivo teleSmart = new Dispositivo("Televisor Smart", new DispositivoInteligente(DispositivoConcreto.TVINTELIGENTE), 0.9);
		Dispositivo tvSamsung = new Dispositivo("Samsung 4k FHD", new DispositivoInteligente(DispositivoConcreto.TVINTELIGENTE), 0.35);
		Dispositivo tvSony = new Dispositivo("Sony UHD curva", new DispositivoInteligente(DispositivoConcreto.TVINTELIGENTE), 0.26);
		
		Dispositivo play4 = new Dispositivo("Play 4", new DispositivoEstandar(), 0.2);
		Dispositivo play3 = new Dispositivo("Play 2", new DispositivoEstandar(), 0.3);
		Dispositivo play2 = new Dispositivo("Play 3", new DispositivoEstandar(), 1);
		Dispositivo play1 = new Dispositivo("Play 1", new DispositivoEstandar(), 0.56);
		Dispositivo asus = new Dispositivo("Asus ZenBook pro", new DispositivoEstandar(), 990.987);
		Dispositivo dell = new Dispositivo("Dell XPS 14", new DispositivoEstandar(), 880.99);
		
		Regla unaReglaEstricta = new ReglaEstricta(actuadores, condiciones, teleSmart);
		
		em.remove(unCliente);
		
		teleSmart.guardarConsumoDeFecha(LocalDateTime.now(), 35000);
		teleSmart.guardarConsumoDeFecha(LocalDateTime.now(), 28);
		teleSmart.guardarConsumoDeFecha(LocalDateTime.now(), 17);

		tvSamsung.guardarConsumoDeFecha(LocalDateTime.now(), 155);
		tvSamsung.guardarConsumoDeFecha(LocalDateTime.now(), 148);
		tvSamsung.guardarConsumoDeFecha(LocalDateTime.now(), 137);

		tvSony.guardarConsumoDeFecha(LocalDateTime.now(), 123);
		tvSony.guardarConsumoDeFecha(LocalDateTime.now(), 254);
		tvSony.guardarConsumoDeFecha(LocalDateTime.now(), 132);

		unCliente.agregarDispositivo(teleSmart);
		unCliente.agregarDispositivo(tvSamsung);
		unCliente.agregarDispositivo(play4);
		unCliente.agregarDispositivo(play3);
		unCliente.agregarDispositivo(play2);
		
		otroCliente.agregarDispositivo(play1);
		otroCliente.agregarDispositivo(tvSony);
		otroCliente.agregarDispositivo(asus);
		otroCliente.agregarDispositivo(dell);
		
		withTransaction(() -> {
			em.persist(unAdmin);
			
			em.persist(r1);
			
			unCliente.agregarDispositivo(teleSmart);
			unCliente.agregarDispositivo(play4);
			unCliente.agregarDispositivo(play3);
			unCliente.agregarDispositivo(play2);
			unCliente.agregarDispositivo(play1);

			em.persist(unCliente);

			RepoRestriccionesUsoDispositivo.getInstance().agregarEntidad(new RestriccionUsoDispositivo(teleSmart, 90, 360, Actuador.ActuadorQueApaga));
			RepoRestriccionesUsoDispositivo.getInstance().agregarEntidad(new RestriccionUsoDispositivo(play4, 90, 360, Actuador.ActuadorQueApaga));
			RepoRestriccionesUsoDispositivo.getInstance().agregarEntidad(new RestriccionUsoDispositivo(play3, 90, 360, Actuador.ActuadorQueApaga));
			RepoRestriccionesUsoDispositivo.getInstance().agregarEntidad(new RestriccionUsoDispositivo(play2, 6, 30, Actuador.ActuadorQuePoneEnAhorroDeEnergia));
			RepoRestriccionesUsoDispositivo.getInstance().agregarEntidad(new RestriccionUsoDispositivo(play1, 6, 15, Actuador.ActuadorQueApaga));
			
		});
	}
}
