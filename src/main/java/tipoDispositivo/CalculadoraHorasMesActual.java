package tipoDispositivo;

import java.time.Duration;
import java.time.LocalDateTime;

public class CalculadoraHorasMesActual {
	
	private static CalculadoraHorasMesActual instancia;
	
	public static CalculadoraHorasMesActual getInstance() {
		if(instancia == null) {
			instancia = new CalculadoraHorasMesActual();
		}
		return instancia;
	}
	
	public long horasDeMesActual() {
		LocalDateTime hoy = LocalDateTime.now();
		LocalDateTime primerDiaDelMes = this.primerDiaMesActual();

	    return Duration.between(primerDiaDelMes, hoy).toHours();
	}
	
	public LocalDateTime primerDiaMesActual() {
		
		LocalDateTime hoy = LocalDateTime.now();
		return LocalDateTime.of(hoy.getYear(), hoy.getMonth(), 1, 0, 0);
	}
}
