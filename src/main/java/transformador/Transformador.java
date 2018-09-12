package transformador;

import java.time.LocalDateTime;
import java.util.List;

import javax.persistence.Entity;

import org.uqbar.geodds.Point;

import cliente.Cliente;
import consumoMasivo.ConsumidorMasivo;
import consumoMasivo.ConsumoMasivoEnBaseA;
import repositorio.RepoTransformadores;

@Entity
public class Transformador extends ConsumoMasivoEnBaseA<Cliente> implements ConsumidorMasivo {
	
	public Transformador(Point ubicacion) {
		super(ubicacion);
	}
	
	public Transformador() {}

	public double consumoActual(Cliente cliente) {
		return cliente.consumoActual();
	}

	public Point ubicacion() {
		return ubicacion;
	}
	
	public void setUbicacion(Point ubicacion) {
		this.ubicacion = ubicacion;
	}
		
	public double distanciaA(Cliente cliente) {	
		return ubicacion.distance(cliente.ubicacion());	
 	}

	public List<Cliente> obtenerFuentesDeConsumo() {
		return RepoTransformadores.getInstance().obtenerClientesDe(this);
	}

	public double consumoEntre(LocalDateTime fechaInicial, LocalDateTime fechaFinal) {
		
		return obtenerFuentesDeConsumo().stream().mapToDouble(cliente -> cliente.consumoRealizadoEntre(fechaInicial,fechaFinal)).sum();
	}

}
