package dispositivo.gadgets.regla;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.DiscriminatorColumn;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Transient;

import db.DatosBasicos;
import dispositivo.Dispositivo;
import dispositivo.gadgets.Gadget;
import dispositivo.gadgets.actuador.Actuador;

@Entity
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(name = "tipo")
public abstract class Regla extends DatosBasicos{
	
	@ManyToOne(fetch = FetchType.EAGER, optional = false)
	protected Dispositivo dispositivo;
	
	@OneToMany(cascade = CascadeType.PERSIST)
	@JoinColumn(name = "idRegla", nullable = false)
	protected List<CondicionSobreSensor> condiciones = new ArrayList<>();
	
	//TODO
	@Transient
	protected List<Actuador> actuadores;
	
	public Regla(List<Actuador> actuadores, List<CondicionSobreSensor> condiciones, Dispositivo dispositivo) {
		validarDispositivoInteligente(dispositivo);
		this.dispositivo = dispositivo;
		this.actuadores = actuadores;
		this.condiciones = condiciones;
	}

	public Dispositivo getDispositivo() {
		return dispositivo;
	}

	public List<CondicionSobreSensor> getCondiciones() {
		return condiciones;
	}

	public List<Actuador> getActuadores() {
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
	private boolean sonIgualesA(List<? extends Gadget> gadgets, List<? extends Gadget> otrosGadgets) {
		return todosLosDeUnaListaEstanEnLaOtra(gadgets, otrosGadgets) && todosLosDeUnaListaEstanEnLaOtra(otrosGadgets, gadgets);
	}
	
	//ademas aca hay algo raro: en el casteo se esta pasando de una lista mas restrictiva a una menos, que es eso que Java no puede resolver...
	@SuppressWarnings("unchecked")
	private boolean todosLosDeUnaListaEstanEnLaOtra(List<? extends Gadget> gadgets, List<? extends Gadget> otrosGadgets) {
		return gadgets.stream().allMatch(gadget -> gadget.estaEn((List<Gadget>) otrosGadgets));
	}

	public boolean esDe(Dispositivo dispositivo) {
		return this.dispositivo.esElMismoConcretoQue(dispositivo);
	}

	// Se pueden abstraer los condiciones.stream().X(condicion -> condicion.seCumpleCondicion()); ? (de las subclases)
	
	/*private <Condicion> boolean seCumpleQue(Stream<Condicion> stream, Predicate<? super Condicion> predicate) {
		return stream.allMatch(predicate);
	}*/
}
