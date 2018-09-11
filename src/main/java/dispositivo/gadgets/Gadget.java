package dispositivo.gadgets;

import java.util.List;

public interface Gadget{
	public default boolean estaEn(List<Gadget> gadgets) {
		return gadgets.stream().anyMatch(unGadget -> esIgualA(unGadget));
	}
	
	public default boolean esIgualA(Gadget otroGadget) {
		return otroGadget.getClass() == this.getClass();
	}
}
