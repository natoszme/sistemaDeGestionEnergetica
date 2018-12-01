package dispositivo;

import javax.persistence.DiscriminatorColumn;
import javax.persistence.Entity;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;

import db.DatosBasicos;
import tipoDispositivo.TipoDispositivo;

@Entity
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(name = "tipo")
public abstract class DispositivoConcreto extends DatosBasicos{
	
	public boolean estaEncendido = true;
	public boolean estaEnAhorroDeEnergia;
	
	public void encender() {
		this.estaEncendido = true;
	};
	public void apagar() {
		this.estaEncendido = false;
	};	
	public void ponerEnAhorroDeEnergia() {
		this.estaEnAhorroDeEnergia = true;
	};
	public void sacarDeAhorroDeEnergia() {
		this.estaEnAhorroDeEnergia = false;
	}
	public boolean estaEncendido() {
		return this.estaEncendido;
	}
	public boolean estaApagado() {
		return !this.estaEncendido;
	}
	public boolean estaEnAhorroEnergia() {
		return this.estaEnAhorroDeEnergia;
	}
	public boolean getEsInteligente() {
		return true;
	}
	
	public abstract void encenderSegunConcreto();
	public abstract void apagarSegunConcreto();
	public abstract void ponerEnAhorroDeEnergiaSegunConcreto();
	public abstract void sacarDeAhorroDeEnergiaSegunConcreto();

	public abstract double consumoDuranteLasUltimas(long horas);
//	public abstract double horasEncendidoEn(double horasDeMesActual, TipoDispositivo dispostivo);
//	public abstract double consumoActual(TipoDispositivo dispostivo);
}
