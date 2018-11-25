package dispositivo;

import javax.persistence.DiscriminatorValue;

@DiscriminatorValue("Generico")
public class DispositivoConcretoGenerico extends DispositivoConcreto{
	
	public void encenderSegunConcreto() {
		/*Debería cada concreto posta hacer su magia*/
		this.encender();
	}
	public void apagarSegunConcreto() {
		/*Debería cada concreto hacer su magia*/
		this.apagar();
	}
	public void ponerEnAhorroDeEnergiaSegunConcreto() {
		/*Debería cada concreto hacer su magia*/
		this.ponerEnAhorroDeEnergia();
	}
	public void sacarDeAhorroDeEnergiaSegunConcreto() {
		/*Debería cada concreto hacer su magia*/
		this.sacarDeAhorroDeEnergia();
	}

}
