package dispositivo.gadgets.regla;

import java.util.Set;

import javax.persistence.Entity;

import dispositivo.Dispositivo;
import dispositivo.gadgets.actuador.Actuador;

@Entity
public class ReglaEstricta extends Regla {
	
	public ReglaEstricta(Set<Actuador> actuadores, Set<CondicionSobreSensor> condiciones, Dispositivo dispositivo) {
		super(actuadores, condiciones, dispositivo);
		// TODO Auto-generated constructor stub
	}
	
	public ReglaEstricta() {}

	protected boolean seCumpleCriterio() {
		return condiciones.stream().allMatch(condicion -> condicion.seCumpleCondicion());
	}
}
