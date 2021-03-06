package es.urjc.phub;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Utils {
	public static boolean esSoluci�nV�lida(Soluci�n s, InstanciaPHub instancia) {
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
				// Si hay conexi�n entre los nodos, sumamos
				if (matrizAdyacencia[i][j]) {
					obj += distancia[i][j];
				}
			}
		}

		return obj;
	}

	public static Soluci�n busquedaLocal(String tipo_orden, List<Soluci�n> vecindades, Soluci�n actual) {
		Soluci�n mejor_soluci�n = actual;
		
		if (tipo_orden == "lexicogr�fico") {
			for (int i = 0; i < vecindades.size(); i++) {
				if (vecindades.get(i).getObjetivo() < mejor_soluci�n.getObjetivo()) {
					mejor_soluci�n = vecindades.get(i);
				}
			}
		} else if (tipo_orden == "aleatorio") {
			// Generamos un n�mero aleatorio entre 0 y n�mero de vecindades
			
			// Copiamos la lista con las vecindades
			List<Soluci�n> vecindades_copia = new ArrayList<Soluci�n>(vecindades);


			int n = vecindades_copia.size();

			Soluci�n s = null;

			while (n > 1) {
				n = vecindades_copia.size();
				Random r = new Random();
				int indice = r.nextInt(n);
				s = vecindades_copia.get(indice);

				if (s.getObjetivo() < mejor_soluci�n.getObjetivo()) {
					mejor_soluci�n = s;
				}

				// Eliminamos de la vecindad
				vecindades_copia.remove(s);
			}

		}

		return mejor_soluci�n;
	}
	
	public static Soluci�n busquedaLocalFirst(String tipo_orden, List<Soluci�n> vecindades, Soluci�n actual) {
		Soluci�n mejor_soluci�n = actual;
		
		if (tipo_orden == "lexicogr�fico") {
			boolean first = false;
			
			for (int i = 0; i < vecindades.size() && !first; i++) {
				if (vecindades.get(i).getObjetivo() < mejor_soluci�n.getObjetivo()) {
					mejor_soluci�n = vecindades.get(i);
					first = true;
				}
			}
		} else if (tipo_orden == "aleatorio") {
			// Generamos un n�mero aleatorio entre 0 y n�mero de vecindades
			
			// Copiamos la lista con las vecindades
			List<Soluci�n> vecindades_copia = new ArrayList<Soluci�n>(vecindades);


			int n = vecindades_copia.size();

			Soluci�n s = null;
			
			boolean first = false;

			while (n > 1 && !first) {
				n = vecindades_copia.size();
				Random r = new Random();
				int indice = r.nextInt(n);
				s = vecindades_copia.get(indice);

				if (s.getObjetivo() < mejor_soluci�n.getObjetivo()) {
					mejor_soluci�n = s;
					first = true;
				}

				// Eliminamos de la vecindad
				vecindades_copia.remove(s);
			}

		}

		return mejor_soluci�n;
	}
}
