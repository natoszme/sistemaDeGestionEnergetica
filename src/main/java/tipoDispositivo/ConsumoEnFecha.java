package tipoDispositivo;

import java.time.LocalDateTime;

import javax.persistence.Column;
import javax.persistence.Convert;
import javax.persistence.Embeddable;

import org.hibernate.annotations.Filter;
import org.hibernate.annotations.FilterDef;
import org.hibernate.annotations.ParamDef;

import org.uqbarproject.jpa.java8.extras.convert.LocalDateTimeConverter;

import com.fasterxml.jackson.annotation.JsonFormat;


@Embeddable
@FilterDef(
	name = "filtroDeFecha", 
	parameters = { @ParamDef(name = "desde", type = "datetime"), @ParamDef(name = "hasta", type = "datetime") }
)
public class ConsumoEnFecha {
	
	@Column(nullable = false)
	@Convert(converter = LocalDateTimeConverter.class)
    @Filter(name = "filtroDeFecha", condition = "fecha BETWEEN :desde AND :hasta")
	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd/MM/yyyy HH:mm:ss")
	private LocalDateTime fecha;
	
	@Column(nullable = false)
	private Double consumo;
	
	public ConsumoEnFecha(LocalDateTime fecha, Double consumo) {
		this.fecha = fecha;
		this.consumo = consumo;
	}
	
	public ConsumoEnFecha() {};
	
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
}
