package tipoDispositivo;

import java.time.LocalDateTime;

import javax.persistence.DiscriminatorColumn;
import javax.persistence.Entity;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;

import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonSubTypes.Type;
import com.fasterxml.jackson.annotation.JsonTypeInfo;

import db.DatosBasicos;
import dispositivo.Dispositivo;
import dispositivo.DispositivoConcreto;

@JsonTypeInfo(
	    use = JsonTypeInfo.Id.NAME,
	    include = JsonTypeInfo.As.PROPERTY,
	    property = "type")
@JsonSubTypes({
    @Type(value = DispositivoEstandar.class, name = "dispositivoEstandar"),
    @Type(value = DispositivoInteligente.class, name = "dispositivoInteligente")})

@Entity
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(name = "tipo")
public abstract class TipoDispositivo extends DatosBasicos{
	
	public abstract double puntosPorRegistrar();
	public abstract boolean esInteligente();
	public abstract double consumoEnLasUltimas(int horas, Dispositivo dispositivo);
	public abstract void apagar();
	public abstract void encender();
	public abstract boolean estaEncendido();
	public abstract boolean estaApagado();
	public abstract void convertirAInteligente(Dispositivo dispositivo, DispositivoConcreto dispositivoConcreto);
	public abstract boolean estaEnAhorroEnergia();
	public abstract void ponerEnAhorroDeEnergia();
	public abstract void guardarConsumoDeFecha(LocalDateTime fecha, double consumo);
	public abstract double horasPrendidoEnMesActual();
	public abstract double consumoActual();
	public abstract boolean esElMismoConcretoQue(Dispositivo dispositivo);
	public abstract DispositivoConcreto getDispositivoConcreto();
}
