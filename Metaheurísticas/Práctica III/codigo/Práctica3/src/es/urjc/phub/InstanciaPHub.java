package es.urjc.phub;

import java.util.Random;

public class InstanciaPHub {

	/*
	 * Número de nodos de la instancia
	 */
	private int nodos;

	/*
	 * Número total de servidores
	 */
	private int servidores;

	/*
	 * Matriz de distancias entre todos los nodos aplicando la distancia
	 * euclídea
	 */
	private double[][] distancia;

	/*
	 * Vector con la demanda de cada nodo
	 */
	private double[] demanda;

	/*
	 * Capacidad de los servidores (todos con la misma)
	 */
	private int capacidad;

	public InstanciaPHub(int nodos, int servidores, double[][] distancia, double[] demanda, int capacidad) {
		this.nodos = nodos;
		this.servidores = servidores;
		this.distancia = distancia;
		this.demanda = demanda;
		this.setCapacidad(capacidad);
	}
	
	public InstanciaPHub(){
		
	}

	public double[][] getDistancia() {
		return distancia;
	}

	public double[] getDemanda() {
		return demanda;
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

	public int getServidores() {
		return servidores;
	}

	public Solución generarSoluciónAleatoria() {
		
		Solución s1 = null;
		
		Random x = new Random();
		
		// Para poder reproducir los resultados
		x.setSeed(0);
		
		do{
			x = new Random();
			
			int nodos = this.getNodos();
			int num_servidores = 0;
	
			boolean[] sol = new boolean[nodos];
			boolean[][] ady = new boolean[nodos][nodos];
	
			// Elegimos al azar los servidores
			while (num_servidores < this.getServidores()) {
				int aleatorio = 0;
				do {
					aleatorio = x.nextInt(nodos);
				} while (sol[aleatorio]);
	
				sol[aleatorio] = true;
	
				num_servidores++;
			}
	
			// Generamos las aristas
			for (int i = 0; i < nodos; i++) {
				if (!sol[i]) {
					// Seleccionamos el servidor más cercano que nos encontremos
					int serv = Utils.seleccionarServidor(i, sol, this.getDistancia());
	
					ady[i][serv] = true;
					ady[serv][i] = true;
				}
	
			}
			
			s1 = new Solución(sol, ady, this.getDistancia());
			
		}while(!Utils.esSoluciónVálida(s1, this));

		return s1 ;
	}
}
