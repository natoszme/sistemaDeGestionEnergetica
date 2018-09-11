package consumoMasivo;

import java.util.List;

import javax.persistence.Convert;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;

import org.uqbar.geodds.Point;

import converter.PointConverter;

@Entity
@Inheritance(strategy = InheritanceType.TABLE_PER_CLASS)
public abstract class ConsumoMasivoEnBaseA<TConsumidorMasivo extends ConsumidorMasivo> {
	
	@Id @GeneratedValue(strategy = GenerationType.TABLE)
	private long id;
	
	@Convert(converter = PointConverter.class)
	protected Point ubicacion;
	
	public ConsumoMasivoEnBaseA(Point ubicacion){
		this.ubicacion = ubicacion;
	}
	
	public ConsumoMasivoEnBaseA() {}
	
	public double consumoActual(){
		return obtenerFuentesDeConsumo().stream().mapToDouble(ConsumidorMasivo::consumoActual).sum();
	}
	
	public abstract List<TConsumidorMasivo> obtenerFuentesDeConsumo();
}
