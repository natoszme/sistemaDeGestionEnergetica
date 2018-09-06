package tipoDispositivo;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.LinkedHashMap;
import java.util.Map;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.MapKey;
import javax.persistence.OneToMany;
import javax.persistence.Transient;

import dispositivo.Dispositivo;
import dispositivo.DispositivoConcreto;

@Entity
public class DispositivoInteligente extends TipoDispositivo{
	
	@Enumerated(EnumType.STRING)
	@Column(nullable = false)
	private DispositivoConcreto dispositivoConcreto;
	
	//TODO mapeo de Double?
	@OneToMany(cascade = CascadeType.PERSIST)
	@MapKey(name = "fecha")
	@Transient
	private Map<LocalDateTime, Double> consumosHastaElMomento = new LinkedHashMap<>();
	
	//TODO deberia ser una variable de entorno?
	@Transient
	private int duracionPlazoCronConsumo = 6;
	
	public DispositivoInteligente(DispositivoConcreto dispositivoConcreto) {
		this.dispositivoConcreto = dispositivoConcreto;
	}
	
	public boolean esInteligente() {
		return true;
	}
	
	public double puntosPorRegistrar() {
		return 15;
	}
	
	public double consumoEnLasUltimas(int horas, Dispositivo dispositivo) {		
		if (horas <= duracionPlazoCronConsumo) {
			return dispositivoConcreto.consumoDuranteLasUltimas(horas);
		}
		//solo permite horas divisibles por duracionPlazoCronConsumo
		return dispositivoConcreto.consumoDuranteLasUltimas(duracionPlazoCronConsumo) + consumoAlmacenadoEn(horas);
	}

	public double consumoAlmacenadoEn(int horas) {
		return consumosHastaElMomento.entrySet().stream().
				filter(consumoFechado -> correspondeAPlazo(consumoFechado.getKey(), horas - duracionPlazoCronConsumo)).
				mapToDouble(consumoFechado -> consumoFechado.getValue()).
				sum();
	}

	// ojo que podria calcular demas!
	private boolean correspondeAPlazo(LocalDateTime dateTimeAEvaluar, int horasABuscar) {
		return Duration.between(dateTimeAEvaluar, LocalDateTime.now()).toHours() <= horasABuscar;
	}

	public void guardarConsumoDeFecha(LocalDateTime fecha, double consumo) {
		consumosHastaElMomento.put(fecha, consumo);
	}
	
	public void apagar() {
		dispositivoConcreto.apagar();
	}
	
	public void encender() {
		dispositivoConcreto.encender();
	}

	public void ponerEnAhorroDeEnergia() {
		dispositivoConcreto.ponerEnAhorroDeEnergia();
	}
	
	public boolean estaEncendido() {
		return dispositivoConcreto.estaEncendido();
	}
	
	public boolean estaApagado() {
		return dispositivoConcreto.estaApagado();
	}
	
	public void convertirAInteligente(Dispositivo dispositivo, DispositivoConcreto dispositivoConcreto) {
		throw new NoSePuedeReConvertirAInteligenteException();
	}

	public boolean estaEnAhorroEnergia() {
		return dispositivoConcreto.estaEnAhorroEnergia();
	}

	public double horasPrendidoEnMesActual() {
		return dispositivoConcreto.horasEncendidoEn(CalculadoraHorasMesActual.getInstance().horasDeMesActual());
	}

	public double consumoActual() {
		return dispositivoConcreto.consumoActual();
	}

	public boolean esElMismoConcretoQue(Dispositivo dispositivo) {
		return dispositivoConcreto == dispositivo.dispositivoConcreto();
	}

	public DispositivoConcreto getDispositivoConcreto() {
		return dispositivoConcreto;
	}
}
