package es.urjc.phub;

public class Solución {
	/*
	 * 1, si el nodo i está conectado con el nodo j
	 * 0, en caso contrario
	 */
	private boolean[] solucion;
	private boolean[][] matrizAdyacencia;
	
	/*
	 * 1, si es un servidor,
	 * 0, en caso contrario
	 */
	private double objetivo;
	
	/*public Solución(boolean[] solucion, boolean[][] matrizAdyacencia, InstanciaPHub instancia){
		this.solucion = solucion;
		this.matrizAdyacencia = matrizAdyacencia;
		this.objetivo = calcularObjetivo(solucion, matrizAdyacencia, instancia.getDistancia());
	}*/
	
	public Solución(boolean[] solucion, boolean[][] matrizAdyacencia, double[][] dist){
		this.solucion = solucion;
		this.matrizAdyacencia = matrizAdyacencia;
		this.objetivo = calcularObjetivo(solucion, matrizAdyacencia, dist);
	}
	
	
	public boolean[] getSolucion() {
		return solucion;
	}



	public void setSolucion(boolean[] solucion) {
		this.solucion = solucion;
	}



	public boolean[][] getMatrizAdyacencia() {
		return matrizAdyacencia;
	}



	public void setMatrizAdyacencia(boolean[][] matrizAdyacencia) {
		this.matrizAdyacencia = matrizAdyacencia;
	}



	public double getObjetivo() {
		return objetivo;
	}



	public void setObjetivo(double objetivo) {
		this.objetivo = objetivo;
	}



	public double calcularObjetivo(boolean[] solucion, boolean[][] matrizAdyacencia, double[][] distancia) {
		double obj = 0;
		for(int i = 0; i < matrizAdyacencia.length; i++){
			for(int j = 0; j <= i; j++){
				// Si hay conexión entre los nodos, sumamos 
				if(matrizAdyacencia[i][j]){
					obj += distancia[i][j];
				}
			}
		}
		
		return obj;
	}
}
