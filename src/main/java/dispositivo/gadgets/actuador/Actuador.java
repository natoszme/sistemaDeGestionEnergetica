package dispositivo.gadgets.actuador;

import dispositivo.Dispositivo;
import dispositivo.gadgets.Gadget;

public enum Actuador implements Gadget {
	
	ActuadorQueApaga{
		
		public void actuarSobre(Dispositivo dispositivo) {
			dispositivo.apagar();
		}
		
	}, ActuadorQueEnciende{
		
		public void actuarSobre(Dispositivo dispositivo) {
			dispositivo.encender();
		}
		
	}, ActuadorQuePoneEnAhorroDeEnergia{
		
		public void actuarSobre(Dispositivo dispositivo) {
			dispositivo.ponerEnAhorroDeEnergia();
		}
		
	};
	
	public abstract void actuarSobre(Dispositivo dispositivo);
}
