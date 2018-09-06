package tipoDispositivo;
import java.time.LocalDateTime;

import javax.persistence.Entity;

import dispositivo.Dispositivo;
import dispositivo.DispositivoConcreto;

@Entity
public class DispositivoEstandar extends TipoDispositivo{
	
	public DispositivoEstandar() {}
	
	public boolean esInteligente() {
		return false;
	}
	
	public double puntosPorRegistrar() {
		return 0;
	}
	
	public void convertirAInteligente(Dispositivo dispositivo, DispositivoConcreto dispositivoConcreto) {
		dispositivo.cambiarTipo(new DispositivoInteligente(dispositivoConcreto));
	}
	
	public boolean estaEncendido() {
		throw new ElMensajeEnviadoNoPuedeSerRespondidoPorUnEstandarException();
	}
	
	public boolean estaApagado() {
		throw new ElMensajeEnviadoNoPuedeSerRespondidoPorUnEstandarException();
	}
	
	public boolean estaEnAhorroEnergia() {
		throw new ElMensajeEnviadoNoPuedeSerRespondidoPorUnEstandarException();
	}

	public void apagar() {
		//no hace nada
	}

	public void encender() {
		//no hace nada
	}
	
	public void ponerEnAhorroDeEnergia() {
		//no hace nada
	}
	
	public void guardarConsumoDeFecha(LocalDateTime fecha, double consumo) {
		//no hace nada
	}

	public double consumoEnLasUltimas(int horas, Dispositivo dispositivo) {
		return dispositivo.estimacionDeConsumoEn(horas);
	}

	public double horasPrendidoEnMesActual() {
		throw new ElMensajeEnviadoNoPuedeSerRespondidoPorUnEstandarException();
	}

	public double consumoActual() {
		throw new ElMensajeEnviadoNoPuedeSerRespondidoPorUnEstandarException();
	}

	public boolean esElMismoConcretoQue(Dispositivo dispositivo) {
		throw new ElMensajeEnviadoNoPuedeSerRespondidoPorUnEstandarException();
	}

	public DispositivoConcreto getDispositivoConcreto() {
		throw new ElMensajeEnviadoNoPuedeSerRespondidoPorUnEstandarException();
	}
} 