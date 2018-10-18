package simplex;

import javax.persistence.Entity;
import javax.persistence.Enumerated;
import javax.persistence.ManyToOne;

import db.DatosBasicos;
import dispositivo.Dispositivo;
import dispositivo.gadgets.actuador.Actuador;

@Entity
public class RestriccionUsoDispositivo extends DatosBasicos{
	
	@ManyToOne
	private Dispositivo dispositivo;
	
	double usoMensualMinimo;
	double usoMensualMaximo;
	
	@Enumerated
	private Actuador actuadorAlExcederse;
	
	public RestriccionUsoDispositivo(Dispositivo dispositivo, double usoMensualMinimo, double usoMensualMaximo, Actuador actuadorAlExcederse) {
		this.dispositivo = dispositivo;
		this.usoMensualMinimo = usoMensualMinimo;
		this.usoMensualMaximo = usoMensualMaximo;
		this.actuadorAlExcederse = actuadorAlExcederse;
	}

	public boolean esDe(Dispositivo dispositivo) {
		return this.dispositivo.esIgualA(dispositivo);
	}

	public double getUsoMensualMinimo() {
		return usoMensualMinimo;
	}

	public double getUsoMensualMaximo() {
		return usoMensualMaximo;
	}
	
	public Actuador getActuadorAlExcederse() {
		return actuadorAlExcederse;
	}
}
