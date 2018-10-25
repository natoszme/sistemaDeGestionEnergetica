package fixture;

import java.time.LocalDateTime;
import java.util.ArrayList;
import javax.persistence.EntityManager;
import org.junit.After;
import org.junit.Before;
import org.uqbar.geodds.Point;
import categoria.Categoria;
import cliente.Cliente;
import cliente.TipoDocumento;
import dispositivo.Dispositivo;
import dispositivo.DispositivoConcreto;
import javafx.util.converter.LocalDateTimeStringConverter;
import tipoDispositivo.DispositivoEstandar;
import tipoDispositivo.DispositivoInteligente;

public class FixtureReportes extends Fixture{

	public FixtureReportes() {
		super();
	}
	
	@Before
	public void before() {
		this.init();
	}
	
	public void init() {
		EntityManager em = entityManager();
		
		Point ubicacionLaMatanza = new Point(-34.762985, -58.631242);
//		Categoria r1 = new Categoria("R1", 0, 150, 18.76, 0.644);
				
		Cliente unCliente = new Cliente("asaez", "1", "Alejandro", "Saez", TipoDocumento.DNI, 3876675, 43543245, "Macos Sastre 324", r1, new ArrayList<>(), ubicacionLaMatanza);

		Dispositivo teleSmart = new Dispositivo("Televisor Smart", new DispositivoInteligente(DispositivoConcreto.TVINTELIGENTE), 0.9);
		Dispositivo tvSamsung = new Dispositivo("Samsung 4k FHD", new DispositivoInteligente(DispositivoConcreto.TVINTELIGENTE), 0.35);
		Dispositivo tvSony = new Dispositivo("Sony UHD curva", new DispositivoInteligente(DispositivoConcreto.TVINTELIGENTE), 0.26);

		teleSmart.guardarConsumoDeFecha(LocalDateTime.now(), 20);
		teleSmart.guardarConsumoDeFecha(LocalDateTime.now(), 60);
		teleSmart.guardarConsumoDeFecha(LocalDateTime.now(), 40);

		tvSamsung.guardarConsumoDeFecha(LocalDateTime.now(), 155);
		tvSamsung.guardarConsumoDeFecha(LocalDateTime.now(), 148);
		tvSamsung.guardarConsumoDeFecha(LocalDateTime.now(), 137);

		tvSony.guardarConsumoDeFecha(LocalDateTime.now(), 123);
		tvSony.guardarConsumoDeFecha(LocalDateTime.now(), 254);
		tvSony.guardarConsumoDeFecha(LocalDateTime.now(), 132);

		withTransaction(() -> {
			
			em.persist(r1);
			
			unCliente.agregarDispositivo(teleSmart);
			unCliente.agregarDispositivo(tvSamsung);
			unCliente.agregarDispositivo(tvSony);
			
			
			
			em.persist(unCliente);

		});
		
	}

}
