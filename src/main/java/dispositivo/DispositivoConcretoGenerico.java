package dispositivo;

import java.time.LocalDateTime;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.print.attribute.standard.DateTimeAtCompleted;

import tipoDispositivo.CalculadoraHorasMesActual;
import tipoDispositivo.TipoDispositivo;

@Entity
@DiscriminatorValue("Generico")
public class DispositivoConcretoGenerico extends DispositivoConcreto{
	
	public DispositivoConcretoGenerico() {}
	
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
	
	public double consumoDuranteLasUltimas(long horas) {
		/*Este método debería medir, dependiendo de cada implementación de dispositivo concreto 
		 * el consumo que tuvo posta, menor al tiempo de medición del cron.
		 * */
		return 20;
	}
//	public double horasEncendidoEn(double horasDeMesActual, TipoDispositivo dispositivo, double consumoPorHora) { 
//		return dispositivo.consumoEntre(CalculadoraHorasMesActual.getInstance().primerDiaMesActual(), LocalDateTime.now()) / consumoPorHora;
//	}
//	public double consumoActual(TipoDispositivo dispositivo) {
//		return 0;
//	}

}
