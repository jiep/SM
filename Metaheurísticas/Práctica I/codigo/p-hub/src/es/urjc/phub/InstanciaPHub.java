package es.urjc.phub;

public class InstanciaPHub {
	
	/*
	 * N�mero de nodos de la instancia
	 */
	private int nodos;
	
	/*
	 * N�mero total de servidores
	 */
	private int servidores;
	
	/* 
	 * Matriz de distancias entre todos los nodos
	 * aplicando la distancia eucl�dea
	 */
	private double[][] distancia;
	
	/*
	 * Matriz que indica si hay asociaci�n entre
	 * el nodo i y j
	 */
	private boolean[][] asociacion;
	
	/*
	 * Vector con la demanda de cada nodo
	 */
	private double[] demanda;
	
	/*
	 * Vector de booleanos que indica si un nodo es servidor
	 */
	private boolean[] esServidor;
	
	/*
	 * Capacidad de los servidores (todos con la misma)
	 */
	private int capacidad;
	
	public InstanciaPHub(int nodos, int servidores, double[][] distancia, double[] demanda, int capacidad){
		this.nodos = nodos;
		this.servidores = servidores;
		this.setDistancia(distancia);
		this.setDemanda(demanda);
		this.setCapacidad(capacidad);
	}

	public double[][] getDistancia() {
		return distancia;
	}

	public void setDistancia(double[][] distancia) {
		this.distancia = distancia;
	}

	public boolean[][] getAsociacion() {
		return asociacion;
	}

	public void setAsociacion(boolean[][] asociacion) {
		this.asociacion = asociacion;
	}

	public double[] getDemanda() {
		return demanda;
	}

	public void setDemanda(double[] demanda) {
		this.demanda = demanda;
	}

	public boolean[] getEsServidor() {
		return esServidor;
	}

	public void setEsServidor(boolean[] esServidor) {
		this.esServidor = esServidor;
	}

	public int getCapacidad() {
		return capacidad;
	}

	public void setCapacidad(int capacidad) {
		this.capacidad = capacidad;
	}

	public int getNodos() {
		return nodos;
	}

	public void setNodos(int nodos) {
		this.nodos = nodos;
	}

	public int getServidores() {
		return servidores;
	}

	public void setServidores(int servidores) {
		this.servidores = servidores;
	}
}
