package dispositivo.gadgets.regla;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.DiscriminatorColumn;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;

import db.DatosBasicos;
import dispositivo.Dispositivo;
import dispositivo.gadgets.Gadget;
import dispositivo.gadgets.actuador.Actuador;

@Entity
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(name = "tipo")
public abstract class Regla extends DatosBasicos{
	
	@ManyToOne(fetch = FetchType.EAGER, optional = false)
	@JoinColumn(name = "idDispositivo")
	protected Dispositivo dispositivo;
	
	@OneToMany(cascade = {CascadeType.PERSIST,CascadeType.MERGE})
	@JoinColumn(name = "idRegla", nullable = true)
	protected Set<CondicionSobreSensor> condiciones = new HashSet<>();
	
	@ElementCollection
	@Column(name = "actuador", nullable = false)
	protected Set<Actuador> actuadores;
	
	public Regla(Set<Actuador> actuadores, Set<CondicionSobreSensor> condiciones, Dispositivo dispositivo) {
		validarDispositivoInteligente(dispositivo);
		this.dispositivo = dispositivo;
		this.actuadores = actuadores;
		this.condiciones = condiciones;
	}

	public Dispositivo getDispositivo() {
		return dispositivo;
	}

	public Set<CondicionSobreSensor> getCondiciones() {
		return condiciones;
	}

	public Set<Actuador> getActuadores() {
		return actuadores;
	}
	
	private void validarDispositivoInteligente(Dispositivo dispositivo) {
		if(!dispositivo.esInteligente()) throw new NoSePuedeUsarReglaSobreDispositivoNoInteligenteException();
	}
	
	public void aplicarSiCumpleCriterio() {
		if(seCumpleCriterio()) {
			actuadores.stream().forEach(actuador -> actuador.actuarSobre(dispositivo));
		}
	}
	
	protected abstract boolean seCumpleCriterio();
	
	public boolean esIgualA(Regla otraRegla) {		
		return dispositivo.esIgualA(otraRegla.getDispositivo()) && sonIgualesA(condiciones, otraRegla.getCondiciones())
				&& sonIgualesA(actuadores, otraRegla.getActuadores());
	}
	
	//TODO revisar desde aca. no es tan trivial hacer esto porque las listas que se pasan como parametro son de un tipo mas restrictivo que Gadget
	//y Java no permite eso. Por eso hay que hacerlo asi:
	private boolean sonIgualesA(Set<? extends Gadget> gadgets, Set<? extends Gadget> otrosGadgets) {
		return todosLosDeUnaListaEstanEnLaOtra(gadgets, otrosGadgets) && todosLosDeUnaListaEstanEnLaOtra(otrosGadgets, gadgets);
	}
	
	//ademas aca hay algo raro: en el casteo se esta pasando de una lista mas restrictiva a una menos, que es eso que Java no puede resolver...
	@SuppressWarnings("unchecked")
	private boolean todosLosDeUnaListaEstanEnLaOtra(Set<? extends Gadget> gadgets, Set<? extends Gadget> otrosGadgets) {
		return gadgets.stream().allMatch(gadget -> gadget.estaEn((Set<Gadget>) otrosGadgets));
	}

	public boolean esDe(Dispositivo dispositivo) {
		return this.dispositivo.esElMismoConcretoQue(dispositivo);
	}

	// Se pueden abstraer los condiciones.stream().X(condicion -> condicion.seCumpleCondicion()); ? (de las subclases)
	
	/*private <Condicion> boolean seCumpleQue(Stream<Condicion> stream, Predicate<? super Condicion> predicate) {
		return stream.allMatch(predicate);
	}*/
}
