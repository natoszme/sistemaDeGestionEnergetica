package dispositivo;

import tipoDispositivo.DispositivoInteligente;

public class DispositivosBaseFactory {
	
	private static DispositivosBaseFactory instancia;
	
	public static DispositivosBaseFactory getInstance() {
		if (instancia == null) {
			instancia = new DispositivosBaseFactory();
		}
		return instancia;
	}
	
	public Dispositivo aire3500Frigorias(DispositivoConcreto dispositivoConcreto) {
		return new Dispositivo("Aire acondicionado 3500 frigorias", new DispositivoInteligente(dispositivoConcreto), 1.613);
	}
	
	public Dispositivo aire2200Frigorias(DispositivoConcreto dispositivoConcreto) {
		return new Dispositivo("Aire acondicionado 2200 frigorias", new DispositivoInteligente(dispositivoConcreto), 1.013);
	}
	
	public Dispositivo computadoraDeEscritorio(DispositivoConcreto dispositivoConcreto) {
		return new Dispositivo("Computadora de escritorio", new DispositivoInteligente(dispositivoConcreto), 0.4);
	}
	
	public Dispositivo lampara20Watt(DispositivoConcreto dispositivoConcreto) {
		return new Dispositivo("Lampara de 20 W", new DispositivoInteligente(dispositivoConcreto), 0.02);
	}
	
	public Dispositivo lavarropasAutomatico5kg(DispositivoConcreto dispositivoConcreto) {
		return new Dispositivo("Lavarropas automatico de 5kg", new DispositivoInteligente(dispositivoConcreto), 0.175);
	}
	
	public Dispositivo microondas(DispositivoConcreto dispositivoConcreto) {
		return new Dispositivo("Microondas", new DispositivoInteligente(dispositivoConcreto), 0.64);
	}
	
	public Dispositivo plancha(DispositivoConcreto dispositivoConcreto) {
		return new Dispositivo("Plancha", new DispositivoInteligente(dispositivoConcreto), 0.75);
	}
	
	public Dispositivo tvLed40Pulgadas(DispositivoConcreto dispositivoConcreto) {
		return new Dispositivo("Televisor LED 40''", new DispositivoInteligente(dispositivoConcreto), 0.08);
	}
	
	public Dispositivo ventiladorDeTecho(DispositivoConcreto dispositivoConcreto) {
		return new Dispositivo("Ventilador de techo", new DispositivoInteligente(dispositivoConcreto), 0.06);
	}
	
	//TODO faltan todos los estandar? estaria bueno que haya al menos un ejemplo de uno
}
