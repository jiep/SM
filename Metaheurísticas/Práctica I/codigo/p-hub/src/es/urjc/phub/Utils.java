package es.urjc.phub;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Utils {
	public static boolean esSoluciónVálida(Solución s, InstanciaPHub instancia) {
		boolean esValida = false;

		boolean[][] ady = s.getMatrizAdyacencia();
		boolean[] sol = s.getSolucion();

		int capacidad = instancia.getCapacidad();

		// Comprobamos si se cumple el criterio de la demanda
		for (int i = 0; i < sol.length; i++) {
			int demanda = 0;
			for (int j = 0; j < sol.length; j++) {
				if (sol[i] && ady[i][j]) {
					demanda += instancia.getDemanda()[j];
				}

			}

			if (demanda <= capacidad && demanda != 0) {
				esValida = true;
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

	public static Solución busquedaLocal(String tipo_orden, List<Solución> vecindades, Solución actual) {
		Solución mejor_solución = actual;
		
		if (tipo_orden == "lexicográfico") {
			for (int i = 0; i < vecindades.size(); i++) {
				if (vecindades.get(i).getObjetivo() < mejor_solución.getObjetivo()) {
					mejor_solución = vecindades.get(i);
				}
			}
		} else if (tipo_orden == "aleatorio") {
			// Generamos un número aleatorio entre 0 y número de vecindades
			
			// Copiamos la lista con las vecindades
			List<Solución> vecindades_copia = new ArrayList<Solución>(vecindades);


			int n = vecindades_copia.size();

			Solución s = null;

			while (n > 1) {
				n = vecindades_copia.size();
				Random r = new Random();
				int indice = r.nextInt(n);
				s = vecindades_copia.get(indice);

				if (s.getObjetivo() < mejor_solución.getObjetivo()) {
					mejor_solución = s;
				}

				// Eliminamos de la vecindad
				vecindades_copia.remove(s);
			}

		}

		return mejor_solución;
	}
}
