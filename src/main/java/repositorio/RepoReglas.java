package repositorio;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

import cliente.Cliente;
import dispositivo.Dispositivo;
import dispositivo.gadgets.regla.Regla;

public class RepoReglas extends RepoEnDB<Regla>{
	
	private static RepoReglas instancia;
	
	public static RepoReglas getInstance() {
		if(instancia == null) {
			instancia = new RepoReglas("Regla");
		}
		return instancia;
	}
	
	public RepoReglas(String tabla) {
		super(tabla);
	}
	
	// TODO revisar y discutir todo lo de abajo!!!

	@Override
	public void agregarEntidad(Regla regla) {
		reemplazarSiExiste(regla);
	}

	@Override
	public void agregarEntidades(List<Regla> reglas) {
		reglas.forEach(regla -> agregarEntidad(regla));
	}
	
	private void reemplazarSiExiste(Regla nuevaRegla) {
		obtenerTodas().stream().filter(reglaExistente -> !reglaExistente.esIgualA(nuevaRegla)).collect(Collectors.toList());
		super.agregarEntidad(nuevaRegla);
	}

	public boolean tieneReglaDe(Dispositivo dispositivo) {
		return obtenerTodas().stream().anyMatch(regla -> regla.esDe(dispositivo));

		// TODO: con esto tira error de dispositivos transient
		// return !reglasDeDispositivo(dispositivo).isEmpty(); 
	}
	
	public List<Regla> reglasDeDispositivo(Dispositivo dispositivo) {
		String string = "FROM Regla r WHERE r.dispositivo = :dispositivo";
		
		return (List<Regla>) em.createQuery(string, Regla.class).setParameter("dispositivo", dispositivo).getResultList();
	}

	public void ejecutarTodas() {
		obtenerTodas().forEach(Regla::aplicarSiCumpleCriterio);		
	}

	public static List<Regla> obtenerXCliente(Cliente cliente) {
		return (List<Regla>) cliente.getDispositivos().stream().filter(Dispositivo::getEsInteligente).map(dispositivo -> dispositivo.getReglas()).flatMap(Collection::stream).collect(Collectors.toList());
	}
}