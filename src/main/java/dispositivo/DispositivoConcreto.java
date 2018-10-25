package dispositivo;

public enum DispositivoConcreto implements InterfazDispositivoConcreto{
	
	//revisar esto, quedo muy forzado
	TVINTELIGENTE{
		public void encender() {
			
		}
		
		public void apagar() {
			
		}
		
		public void ponerEnAhorroDeEnergia() {
			
		}
		
		public double consumoDuranteLasUltimas(int horas) {
			return 0;
		}
		
		public boolean getEsInteligente() {
			return true;
		}
		
		public boolean estaEncendido() {
			return true;
		}
		
		public boolean estaApagado() {
			return false;
		}
		
		public boolean estaEnAhorroEnergia() {
			return false;
		}
		
		public double horasEncendidoEn(double horasDeMesActual) {
			return 0;
		}

		public double consumoActual() {
			return 0;
		}
	};
}
