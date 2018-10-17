package repositorio;
import dispositivo.Dispositivo;
import dispositivo.gadgets.actuador.Actuador;
import simplex.NoExisteRestriccionPara;
import simplex.RestriccionUsoDispositivo;

public class RepoRestriccionesUsoDispositivo extends RepoEnDB<RestriccionUsoDispositivo> {
	
	private static RepoRestriccionesUsoDispositivo instancia;	
	
	public static RepoRestriccionesUsoDispositivo getInstance(){
		if (instancia == null) {
			instancia = new RepoRestriccionesUsoDispositivo();
		}
		return instancia;
	}
	
	private RestriccionUsoDispositivo obtenerRestriccionDe(Dispositivo dispositivo) {
		RestriccionUsoDispositivo restriccion = obtenerTodas().stream().filter(unaRestriccion -> unaRestriccion.esDe(dispositivo)).
				findFirst().orElseThrow(() -> new NoExisteRestriccionPara(dispositivo));
		return restriccion;
	}
	
	public double dameRestriccionMaximaDe(Dispositivo dispositivo) {
		RestriccionUsoDispositivo restriccion = obtenerRestriccionDe(dispositivo);
		
		return restriccion.getUsoMensualMaximo();
	}
	
	public double dameRestriccionMinimaDe(Dispositivo dispositivo) {
		RestriccionUsoDispositivo restriccion = obtenerRestriccionDe(dispositivo);
		
		return restriccion.getUsoMensualMinimo();
	}

	public Actuador dameAccionDe(Dispositivo dispositivo) {
		return obtenerRestriccionDe(dispositivo).getActuadorAlExcederse();
	}
}
