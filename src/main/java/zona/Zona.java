package zona;

import java.util.List;
import java.util.stream.Collectors;

import javax.persistence.Convert;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

import org.uqbar.geodds.Point;

import consumoMasivo.ConsumoMasivoEnBaseA;
import converter.PointConverter;
import repositorio.RepoTransformadores;
import transformador.Transformador;

@Entity
public class Zona extends ConsumoMasivoEnBaseA<Transformador> {
	
	@Id @GeneratedValue
	public long id;
	
	@Convert(converter = PointConverter.class)
	private Point ubicacion;
	private double radio;
	
	public Zona(Point ubicacion, double radio) {
		this.ubicacion = ubicacion;
		this.radio = radio;
	}

	public double consumoActual(Transformador transformador) {
		return transformador.consumoActual();
	}

	public List<Transformador> obtenerFuentesDeConsumo() {
		return RepoTransformadores.getInstance().obtenerTodas().
				stream().
				filter(transformador -> mePertenece(transformador)).
				collect(Collectors.toList());
	}

	public boolean mePertenece(Transformador transformador) {
		return ubicacion.distance(transformador.ubicacion()) < radio;
	}
}
