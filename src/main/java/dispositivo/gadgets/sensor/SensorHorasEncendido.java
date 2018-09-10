package dispositivo.gadgets.sensor;

import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;

import dispositivo.Dispositivo;

@Entity
public class SensorHorasEncendido extends Sensor {
	
	@OneToOne
	@JoinColumn(name = "idDispositivo")
	protected Dispositivo dispositivo;
	
	public SensorHorasEncendido(Dispositivo dispositivo) {
		this.dispositivo = dispositivo;		
	}
	
	public double medir() {
		return dispositivo.horasPrendidoEnMesActual();
	}
}