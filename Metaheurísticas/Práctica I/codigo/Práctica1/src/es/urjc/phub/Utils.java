package es.urjc.phub;

public class Utils {
	public static boolean esSoluciónVálida(Solución s, InstanciaPHub instancia) {
		boolean esValida = true;

		boolean[][] ady = s.getMatrizAdyacencia();
		boolean[] sol = s.getSolucion();

		int capacidad = instancia.getCapacidad();

		int demanda[] = new int[instancia.getServidores()];

		int cont = 0;

		// Comprobamos si se cumple el criterio de la demanda
		for (int i = 0; i < sol.length; i++) {
			if (sol[i]) {
				for (int j = 0; j < sol.length; j++) {
					if (ady[i][j]) {
						demanda[cont] += instancia.getDemanda()[j];
					}
				}
				cont++;
			}
		}

		for (int i = 0; i < demanda.length; i++) {
			if (demanda[i] > capacidad) {
				esValida = false;
			}
		}

		return esValida;

	}

	public static int seleccionarServidor(int cliente, boolean[] sol, double[][] dist) {
		// System.out.println("Cliente: " + cliente);
		// No encontrado
		int ind = -1;
		double min_dist = 10000000;
		for (int i = 0; i < dist.length; i++) {
			if (min_dist > dist[cliente][i] && sol[i] && (cliente != i)) {
				min_dist = dist[cliente][i];
				ind = i;
			}
		}

		// System.out.println("Servidor: " + ind);
		return ind;
	}

	public static double calcularObjetivo(boolean[] solucion, boolean[][] matrizAdyacencia, double[][] distancia) {
		double obj = 0;
		for (int i = 0; i < matrizAdyacencia.length; i++) {
			for (int j = 0; j <= i; j++) {
				// Si hay conexión entre los nodos, sumamos
				if (matrizAdyacencia[i][j]) {
					obj += distancia[i][j];
				}
			}
		}

		return obj;
	}	
}