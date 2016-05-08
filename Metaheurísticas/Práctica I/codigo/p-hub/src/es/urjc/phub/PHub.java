package es.urjc.phub;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;
import java.util.Scanner;
import java.util.Vector;

public class PHub {

	static List<InstanciaPHub> leerInstanciasDesdeDirectorio(String directorio) {
		List<InstanciaPHub> instancias = new ArrayList<>();

		File dir = new File(directorio);
		File[] ficheros = dir.listFiles();

		for (int i = 0; i < ficheros.length; i++) {
			if (ficheros[i].isFile()) {
				instancias.add(leerInstancia(ficheros[i].getPath()));
			}
		}

		return instancias;
	}

	static double[][] calcularDistancias(Vector<Double> posiciones[]) {
		int n = posiciones.length;
		double[][] distancias = new double[n][n];

		for (int i = 0; i < n; i++) {
			for (int j = 0; j <= i; j++) {
				// Posiciones del nodo i
				double posicion_x_i = posiciones[i].get(0);
				double posicion_y_i = posiciones[i].get(1);

				// Posiciones del nodo j
				double posicion_x_j = posiciones[j].get(0);
				double posicion_y_j = posiciones[j].get(1);

				// Cálculo de la distancia (euclídea)
				double distancia = Math.sqrt((posicion_x_i - posicion_x_j) * (posicion_x_i - posicion_x_j)
						+ (posicion_y_i - posicion_y_j) * (posicion_y_i - posicion_y_j));
				distancias[i][j] = distancia;
				distancias[j][i] = distancia;
			}
		}

		return distancias;
	}

	static InstanciaPHub leerInstancia(String filepath) {

		int nodos = 0;
		int servidores = 0;
		int capacidad = 0;

		double[] demanda = null;
		Vector<Double>[] posiciones = null;

		try {
			Scanner scanner = new Scanner(new File(filepath));

			/*
			 * Leemos la primera línea
			 */
			String linea1 = scanner.nextLine();

			String datos[] = linea1.split(" ");

			/*
			 * Comenzamos a leer los datos desde el elemento 1, puesto que cada
			 * línea comienza con un espacio
			 */
			nodos = Integer.parseInt(datos[1]);
			servidores = Integer.parseInt(datos[2]);
			capacidad = Integer.parseInt(datos[3]);

			/*
			 * Creamos el vector de demanda
			 */
			demanda = new double[nodos];

			/*
			 * Matriz de vectores para guardar la posición (x,y) de cada nodo
			 */
			posiciones = new Vector[nodos];

			for (int i = 0; i < nodos; i++) {

				Vector<Double> posicion = new Vector<>(2);

				// Número de nodo
				int num_nodo = scanner.nextInt();
				// Posición (x,y) del nodo
				double pos_x = scanner.nextDouble();
				double pos_y = scanner.nextDouble();

				// Añadimos posición al vector
				posicion.add(pos_x);
				posicion.add(pos_y);
				posiciones[i] = posicion;

				// Añadimos demanda al vector
				demanda[i] = scanner.nextInt();
			}
			scanner.close();
		} catch (IOException e) {
			System.out.println("No se pudo leer el archivo correctamente.");
		}
		double[][] distancia = calcularDistancias(posiciones);
		return new InstanciaPHub(nodos, servidores, distancia, demanda, capacidad);

	}

	public static void main(String[] args) {

		// Generamos 1000 simulaciones de cada instancia

		int n = 1000;

		double[][] distancia = { { 0, 3, 6 }, { 3, 0, 4 }, { 6, 4, 0 } };
		boolean[][] ady = { { false, true, true }, { true, false, false }, { true, false, false } };
		double[] demanda = { 100, 30, 50 };

		InstanciaPHub ins = new InstanciaPHub(3, 1, distancia, demanda, 120);

		/*
		 * boolean[] solucion = { false, true, false, false, true }; boolean[][]
		 * ady = { { false, false, false, false, true }, { false, false, true,
		 * true, false }, { false, true, false, false, false }, { false, true,
		 * false, false, false }, { true, false, false, false, false } };
		 * 
		 * Solución s = new Solución(solucion, ady, new double[5][5]);
		 */

//		Solución s = ins.generarSoluciónAleatoria();
//
//		System.out.println("Solución aleatoria: " + Arrays.toString(s.getSolucion()));
//		System.out.println("Matriz de adyacencia: " + Arrays.deepToString(s.getMatrizAdyacencia()));
//		System.out.println("Función objetivo: " + s.getObjetivo());
//		System.out.println("Demanda: " + Arrays.toString(ins.getDemanda()));
//		System.out.println("========================================================");
//
//		ArrayList<Solución> vecindad = (ArrayList<Solución>) generarVecindad(s, ins);
//		
//		Solución mejorSolución = Utils.busquedaLocal("aleatorio", vecindad, s);
//		
//		System.out.println("Solución aleatoria: " + Arrays.toString(mejorSolución.getSolucion()));
//		System.out.println("Matriz de adyacencia: " + Arrays.deepToString(mejorSolución.getMatrizAdyacencia()));
//		System.out.println("Función objetivo: " + mejorSolución.getObjetivo());

		// Cargamos las instancias de cada fichero

		List<InstanciaPHub> instancias = leerInstanciasDesdeDirectorio("instancias");

		List<Solución> mejores_soluciones = new ArrayList<>();
		List<Solución>[] vecindades = (ArrayList<Solución>[]) new ArrayList[instancias.size()];
		int num_instancia = 0;
		for (InstanciaPHub instancia : instancias) {
			System.out.println("Instancia: " + instancia);
			Solución mejor_solucion = null;
			double obj = 100000000;
			for (int i = 0; i < n; i++) {
				Solución s1 = instancia.generarSoluciónAleatoria();
				if (s1.getObjetivo() < obj) {
					mejor_solucion = s1;
					obj = s1.getObjetivo();
				}
			}
			mejores_soluciones.add(mejor_solucion);
			vecindades[num_instancia] = generarVecindad(mejor_solucion, instancia);
			num_instancia++;
		}
		
		for(int i = 0; i < instancias.size(); i++){
			System.out.println("Número de instancia: " + i);
			
			System.out.println("Solución sin búsqueda: " + Arrays.toString(mejores_soluciones.get(i).getSolucion()));
			System.out.println("Matriz de adyacencia: " + Arrays.deepToString(mejores_soluciones.get(i).getMatrizAdyacencia()));
			System.out.println("Función objetivo: " + mejores_soluciones.get(i).getObjetivo());
			
			Solución s = Utils.busquedaLocal("aleatorio", vecindades[i], mejores_soluciones.get(i));
			
			System.out.println("--------- Orden de búsqueda: aleatorio -------");
			System.out.println("Solución tras búsqueda: " + Arrays.toString(s.getSolucion()));
			System.out.println("Matriz de adyacencia: " + Arrays.deepToString(s.getMatrizAdyacencia()));
			System.out.println("Función objetivo: " + s.getObjetivo());
			
			Solución s1 = Utils.busquedaLocal("lexicográfico", vecindades[i], mejores_soluciones.get(i));

			System.out.println("--------- Orden de búsqueda: lexicográfico -------");
			System.out.println("Solución tras búsqueda: " + Arrays.toString(s1.getSolucion()));
			System.out.println("Matriz de adyacencia: " + Arrays.deepToString(s1.getMatrizAdyacencia()));
			System.out.println("Función objetivo: " + s1.getObjetivo());
			
			
			System.out.println("============================================================");
		}		
		

		/*
		 * for (int i = 0; i < instancias.get(0).getDistancia().length; i++) {
		 * for (int j = 0; j < instancias.get(0).getDistancia().length; j++) {
		 * System.out.print(instancias.get(0).getDistancia()[i][j] + " "); }
		 * System.out.print("\n"); }
		 */

	}

	static List<Solución> generarVecindad(Solución s, InstanciaPHub instancia) {
		List<Solución> vecindad = new ArrayList<>();

		boolean[] sol = s.getSolucion();
		boolean[] sol2 = new boolean[sol.length];

		int nodos = sol.length;

		// Generamos las permutaciones
		for (int i = 0; i < sol.length; i++) {
			for (int j = 0; j < sol.length; j++) {
				// Buscamos un true y un falso entre un servidor y un cliente
				if (sol[i] == true && sol[j] == false) {
					// Copiamos el array sol
					System.arraycopy(sol, 0, sol2, 0, sol.length);

					// Intercambiamos las posiciones
					sol2[i] = false;
					sol2[j] = true;

					// Creamos la matriz de adyacencia de la nueva solución
					boolean[][] ady = new boolean[nodos][nodos];
					for (int z = 0; z < nodos; z++) {
						if (!sol2[z]) {
							// Seleccionamos el servidor más cercano que nos
							// encontremos
							int serv = Utils.seleccionarServidor(z, sol2, instancia.getDistancia());

							ady[z][serv] = true;
							ady[serv][z] = true;
						}

					}

					Solución s1 = new Solución(sol2, ady, instancia.getDistancia());

					if (Utils.esSoluciónVálida(s1, instancia)) {
						vecindad.add(s1);
					}
				}
			}

		}

		return vecindad;
	}

}
