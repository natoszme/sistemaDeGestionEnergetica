package tipoDispositivo;

import java.time.LocalDateTime;

import javax.persistence.Embeddable;


@Embeddable
public class ConsumoEnFecha {
	public LocalDateTime getFecha() {
		return fecha;
	}

	public void setFecha(LocalDateTime fecha) {
		this.fecha = fecha;
	}

	public Double getConsumo() {
		return consumo;
	}

	public void setConsumo(Double consumo) {
		this.consumo = consumo;
	}

	LocalDateTime fecha;
	Double consumo;
	
	public  ConsumoEnFecha() {};
	
	public ConsumoEnFecha(LocalDateTime fecha,Double consumo) {
		this.fecha = fecha;
		this.consumo = consumo;
	}
}
