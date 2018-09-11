package dispositivo.gadgets;

import java.util.Set;

public interface Gadget{
	public default boolean estaEn(Set<Gadget> otrosGadgets) {
		return otrosGadgets.stream().anyMatch(unGadget -> esIgualA(unGadget));
	}
	
	public default boolean esIgualA(Gadget otroGadget) {
		return otroGadget.getClass() == this.getClass();
	}
}
