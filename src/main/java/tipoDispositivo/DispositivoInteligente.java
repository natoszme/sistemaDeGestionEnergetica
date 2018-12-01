package tipoDispositivo;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.JoinTable;
import javax.persistence.OneToOne;
import javax.persistence.JoinColumn;
import javax.persistence.Transient;


import dispositivo.Dispositivo;
import dispositivo.DispositivoConcreto;

@Entity
public class DispositivoInteligente extends TipoDispositivo {
	
	@OneToOne(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
	@JoinColumn(name = "IdDispositivoConcreto")
	private DispositivoConcreto dispositivoConcreto;
	
	@JoinTable(name = "HistorialConsumos", joinColumns = @JoinColumn(name = "idDispositivoInteligente"))
	@ElementCollection(fetch = FetchType.LAZY)
	private List<ConsumoEnFecha> consumosHastaElMomento = new ArrayList<>();
	
	//TODO deberia ser una variable de entorno?
	@Transient
	private int duracionPlazoCronConsumo = 6;
	
	public DispositivoInteligente() {}
	
	public DispositivoInteligente(DispositivoConcreto dispositivoConcreto) {
		this.dispositivoConcreto = dispositivoConcreto;
	}
	
	public boolean esInteligente() {
		return true;
	}
	
	public double puntosPorRegistrar() {
		return 15;
	}
	
	public double consumoEnLasUltimas(long horas, Dispositivo dispositivo) {		
		return this.consumoEnLasUltimasHoras(horas);
	}
	
	private double consumoEnLasUltimasHoras(long horas) {
		if (horas <= duracionPlazoCronConsumo) {
			return dispositivoConcreto.consumoDuranteLasUltimas(horas);
		}
		//solo permite horas divisibles por duracionPlazoCronConsumo
		return dispositivoConcreto.consumoDuranteLasUltimas(horas % duracionPlazoCronConsumo) + consumoAlmacenadoEn(horas);
	}
	
	public double consumoAlmacenadoEn(long horas) {
		return consumosHastaElMomento.stream().
				filter(consumoFechado -> correspondeAPlazo(consumoFechado.getFecha(), horas - duracionPlazoCronConsumo)).
				mapToDouble(consumoFechado -> consumoFechado.getConsumo()).
				sum();
	}

	// ojo que podria calcular demas!
	private boolean correspondeAPlazo(LocalDateTime dateTimeAEvaluar, long horasABuscar) {
		return Duration.between(dateTimeAEvaluar, LocalDateTime.now()).toHours() <= horasABuscar;
	}

	public void guardarConsumoDeFecha(LocalDateTime fecha, double consumo) {
		ConsumoEnFecha nuevoConsumo = new ConsumoEnFecha();
		nuevoConsumo.setFecha(fecha);
		nuevoConsumo.setConsumo(consumo);
		consumosHastaElMomento.add(nuevoConsumo);
	}
	
	public double consumoEntre(LocalDateTime fechaInicial, LocalDateTime fechaFinal) {
		return consumosHastaElMomento.stream().filter(consumoFechado -> consumoEntreLasFechas(consumoFechado.getFecha(),fechaInicial, fechaFinal))
				.mapToDouble(consumoFechado -> consumoFechado.getConsumo()).sum();
	}
	
	public boolean consumoEntreLasFechas(LocalDateTime dateTimeAEvaluar, LocalDateTime fechaInicial, LocalDateTime fechaFinal) {
		return dateTimeAEvaluar.isAfter(fechaInicial) && dateTimeAEvaluar.isBefore(fechaFinal);
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

	public double horasPrendidoEnMesActual(double kwH) {
		return this.consumoEnLasUltimasHoras(CalculadoraHorasMesActual.getInstance().horasDeMesActual()) / kwH;
	}

	public double consumoActual() {
		return this.consumosHastaElMomento.stream().mapToDouble(consumo -> consumo.getConsumo()).sum();
	}

	public boolean esElMismoConcretoQue(Dispositivo dispositivo) {
		return dispositivoConcreto == dispositivo.dispositivoConcreto();
	}

	public DispositivoConcreto getDispositivoConcreto() {
		return dispositivoConcreto;
	}
}
