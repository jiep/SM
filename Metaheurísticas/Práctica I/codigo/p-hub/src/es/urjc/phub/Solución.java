package es.urjc.phub;

public class Solución {

	private boolean[] solucion;
	private double objetivo;
	
	public Solución(boolean[] solucion){
		this.solucion = solucion;
		this.objetivo = calcularObjetivo(solucion);
	}

	public double calcularObjetivo(boolean[] solucion) {
		// TODO Auto-generated method stub
		return 0;
	}
	
}
