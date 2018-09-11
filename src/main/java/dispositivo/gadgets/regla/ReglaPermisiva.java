package dispositivo.gadgets.regla;

import java.util.List;

import javax.persistence.Entity;

import dispositivo.Dispositivo;
import dispositivo.gadgets.actuador.Actuador;

@Entity
public class ReglaPermisiva extends Regla{
	public ReglaPermisiva(List<Actuador> actuadores, List<CondicionSobreSensor> condiciones, Dispositivo dispositivo) {
		super(actuadores, condiciones, dispositivo);
		// TODO Auto-generated constructor stub
	}

	protected boolean seCumpleCriterio() {
		return condiciones.stream().anyMatch(condicion -> condicion.seCumpleCondicion());
	}
}