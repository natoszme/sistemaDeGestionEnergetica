package dispositivo;

public interface InterfazDispositivoConcreto {
	public void encender();
	public void apagar();	
	public void ponerEnAhorroDeEnergia();
	public double consumoDuranteLasUltimas(int horas);
	public boolean estaEncendido();
	public boolean estaApagado();
	public boolean estaEnAhorroEnergia();

	public double horasEncendidoEn(double horasDeMesActual);

	public double consumoActual();
}
