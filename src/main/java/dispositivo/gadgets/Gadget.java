package dispositivo.gadgets;

import java.util.List;

public abstract class Gadget{
	public boolean estaEn(List<Gadget> gadgets) {
		return gadgets.stream().anyMatch(unGadget -> esIgualA(unGadget));
	}
	
	public boolean esIgualA(Gadget otroGadget) {
		return otroGadget.getClass() == this.getClass();
	}
}
