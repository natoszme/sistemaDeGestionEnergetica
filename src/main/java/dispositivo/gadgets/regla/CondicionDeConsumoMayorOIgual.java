package dispositivo.gadgets.regla;

import javax.persistence.Entity;

import dispositivo.gadgets.sensor.Sensor;

@Entity
public class CondicionDeConsumoMayorOIgual extends CondicionSobreSensor {
	
	private double factorDeComparacion;
	
	public CondicionDeConsumoMayorOIgual(double factorDeComparacion, Sensor sensor) {
		super(sensor);
		this.factorDeComparacion = factorDeComparacion;
	}
	
	public boolean condicionSobreMedicion(double medicion) {
		return medicion >= factorDeComparacion;
	}
}