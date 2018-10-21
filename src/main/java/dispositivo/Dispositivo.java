package dispositivo;

import java.time.LocalDateTime;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;

import db.DatosBasicos;
import dispositivo.gadgets.regla.Regla;
import repositorio.RepoReglas;
import tipoDispositivo.DispositivoInteligente;
import tipoDispositivo.TipoDispositivo;

@Entity
public class Dispositivo extends DatosBasicos {

	@Column(nullable = false)
	private String nombre;

	@OneToOne(cascade = {CascadeType.PERSIST, CascadeType.MERGE}, optional = false)
	@JoinColumn(name = "tipoDispositivo")
	private TipoDispositivo tipoDispositivo;

	private double kwPorHora;

	public Dispositivo(String nombre, TipoDispositivo tipoDispositivo, double kwPorHora) {
		this.nombre = nombre;
		this.tipoDispositivo = tipoDispositivo;
		this.kwPorHora = kwPorHora;
	}

	public Dispositivo() {}

	public String getNombre() {
		return nombre;
	}
	
	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public TipoDispositivo getTipoDispositivo() {
		return tipoDispositivo;
	}

	public double getKwPorHora() {
		return kwPorHora;
	}

	public void convertirAInteligente(DispositivoConcreto dispositivoConcreto) {
		tipoDispositivo.convertirAInteligente(this, dispositivoConcreto);
	}

	public boolean esInteligente() {
		return tipoDispositivo.esInteligente();
	}

	public boolean estaEncendido() {
		return tipoDispositivo.estaEncendido();
	}

	public boolean estaEnAhorroEnergia() {
		return tipoDispositivo.estaEnAhorroEnergia();
	}

	public boolean estaApagado() {
		return tipoDispositivo.estaApagado();
	}

	public double consumoEnLasUltimas(int horas, Dispositivo dispositivo) {
		return tipoDispositivo.consumoEnLasUltimas(horas, dispositivo);
	}

	public double estimacionDeConsumoEn(int horas) {
		return horas * kwPorHora;
	}

	public double puntosPorRegistrar() {
		return tipoDispositivo.puntosPorRegistrar();
	}

	public void apagar() {
		tipoDispositivo.apagar();
	}

	public void encender() {
		tipoDispositivo.encender();
	}

	public void ponerEnAhorroDeEnergia() {
		tipoDispositivo.ponerEnAhorroDeEnergia();
	}

	public void cambiarTipo(DispositivoInteligente dispositivoInteligente) {
		tipoDispositivo = dispositivoInteligente;
	}

	public void guardarConsumoDeFecha(LocalDateTime fecha, double consumo) {
		tipoDispositivo.guardarConsumoDeFecha(fecha, consumo);
	}

	public double horasPrendidoEnMesActual() {
		return tipoDispositivo.horasPrendidoEnMesActual();
	}

	public double consumoActual() {
		return tipoDispositivo.consumoActual();
	}

	public boolean esIgualA(Dispositivo otroDispositivo) {
		return otroDispositivo.getNombre() == nombre;
	}

	public boolean esElMismoConcretoQue(Dispositivo dispositivo) {
		return tipoDispositivo.esElMismoConcretoQue(dispositivo);
	}

	public DispositivoConcreto dispositivoConcreto() {
		return tipoDispositivo.getDispositivoConcreto();
	}
	
	public double consumoEntre(LocalDateTime fechaInicial, LocalDateTime fechaFinal) {
		return tipoDispositivo.consumoEntre(fechaInicial, fechaFinal);
	}
	
	public List<Regla> getReglas() {
		return RepoReglas.getInstance().reglasDeDispositivo(this);
	}
}
