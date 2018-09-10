package transformador;

import java.util.List;

import javax.persistence.Convert;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

import org.uqbar.geodds.Point;

import cliente.Cliente;
import consumoMasivo.ConsumidorMasivo;
import consumoMasivo.ConsumoMasivoEnBaseA;
import converter.PointConverter;
import repositorio.RepoTransformadores;

@Entity
public class Transformador extends ConsumoMasivoEnBaseA<Cliente> implements ConsumidorMasivo {
	
	@Id @GeneratedValue
	public long id;
	
	@Convert(converter = PointConverter.class)
	private Point ubicacion;
	
	public Transformador() {}

	public Transformador(Point ubicacion) {
		this.ubicacion = ubicacion;
	}

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
}
