package dispositivo.gadgets.regla;

import javax.persistence.DiscriminatorColumn;
import javax.persistence.Entity;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import db.DatosBasicos;
import dispositivo.gadgets.Gadget;
import dispositivo.gadgets.sensor.Sensor;

@Entity
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(name = "tipo")
public abstract class CondicionSobreSensor extends DatosBasicos implements Gadget{
	
	@ManyToOne(optional = false)
	@JoinColumn(name = "idSensor")
	private Sensor sensor;
	
	public CondicionSobreSensor(Sensor sensor) {
		this.sensor = sensor;		
	}
	
	public abstract boolean condicionSobreMedicion(double medicion);
	
	public boolean seCumpleCondicion() {
		return condicionSobreMedicion(sensor.medir());
	}
	
	public Sensor getSensor() {
		return sensor;
	}
	
	@Override
	public boolean esIgualA(Gadget gadget) {
		return Gadget.super.esIgualA(gadget) && sensor == ((CondicionSobreSensor)gadget).getSensor();
	}
}