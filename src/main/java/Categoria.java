
public class Categoria {
	String nombre;
	double consumoMinimo;
	double consumoMaximo;
	double cargoFijo;
	double cargoVariable;
	
	public Categoria(String nombre, double consumoMinimo, double consumoMaximo, double cargoFijo, double cargoVariable) {
		this.nombre = nombre;
		this.consumoMinimo = consumoMinimo;
		this.consumoMaximo = consumoMaximo;
		this.cargoFijo = cargoFijo;
		this.cargoVariable = cargoVariable;
	}
	
	//como no queremos pasar nulls en estaEntre...
	/*public Categoria(String nombre, double consumoMinimo, double cargoFijo, double cargoVariable) {
		this.nombre = nombre;
		this.consumoMinimo = consumoMinimo;
		this.cargoFijo = cargoFijo;
		this.cargoVariable = cargoVariable;
	}*/
	
	public boolean meCorrespondeElConsumo(double consumo) {
		return estaEntre(consumo, consumoMinimo, consumoMaximo);
	}
	
	private boolean estaEntre(double consumo, double consumoMinimo, double consumoMaximo) {
		return consumo <= consumoMaximo && consumo > consumoMinimo;
	}
}
