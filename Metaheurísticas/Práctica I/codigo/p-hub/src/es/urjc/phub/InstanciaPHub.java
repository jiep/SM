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
		do{
			Random x = new Random();
	
			// Para poder reproducir los resultados
			x.setSeed(0);
	
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
					int serv = seleccionarServidor(i, sol, this.getDistancia());
	
					ady[i][serv] = true;
					ady[serv][i] = true;
				}
	
			}
			
			s1 = new Solución(sol, ady, this.getDistancia());
			
		}while(!esSoluciónVálida(s1, this));

		return s1 ;
	}
	
	static boolean esSoluciónVálida(Solución s, InstanciaPHub instancia) {
		boolean esValida = false;

		boolean[][] ady = s.getMatrizAdyacencia();
		boolean[] sol = s.getSolucion();

		int capacidad = instancia.getCapacidad();

		// Comprobamos si se cumple el criterio de la demanda
		for (int i = 0; i < sol.length; i++) {
			// Si es un servidor
			if (sol[i]) {
				// Comprobamos si la suma de todas las demandas
				// son menores o iguales a la capacidad del servidor
				int demanda = 0;
				for (int j = 0; i <= j && j < sol.length; j++) {
					if (ady[i][j]) {
						demanda += instancia.getDemanda()[j];
					}
				}

				if (demanda <= capacidad) {
					esValida = true;
				} else {
					esValida = false;
				}
			}
		}

		return esValida;

	}

	private static int seleccionarServidor(int cliente, boolean[] sol, double[][] dist) {
		//System.out.println("Cliente: " + cliente);
		// No encontrado
		int ind = -1;
		double min_dist = 10000000;
		for (int i = 0; i < dist.length; i++) {
			if (min_dist > dist[cliente][i] && sol[i] && (cliente != i)) {
				min_dist = dist[cliente][i];
				ind = i;
			}
		}

		//System.out.println("Servidor: " + ind);
		return ind;
	}
}
