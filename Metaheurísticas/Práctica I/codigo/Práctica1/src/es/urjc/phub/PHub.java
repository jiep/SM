package es.urjc.phub;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
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

				// C�lculo de la distancia (eucl�dea)
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
			 * Leemos la primera l�nea
			 */
			String linea1 = scanner.nextLine();

			String datos[] = linea1.split(" ");

			/*
			 * Comenzamos a leer los datos desde el elemento 1, puesto que cada
			 * l�nea comienza con un espacio
			 */
			nodos = Integer.parseInt(datos[1]);
			servidores = Integer.parseInt(datos[2]);
			capacidad = Integer.parseInt(datos[3]);

			/*
			 * Creamos el vector de demanda
			 */
			demanda = new double[nodos];

			/*
			 * Matriz de vectores para guardar la posici�n (x,y) de cada nodo
			 */
			posiciones = new Vector[nodos];

			for (int i = 0; i < nodos; i++) {

				Vector<Double> posicion = new Vector<>(2);

				// N�mero de nodo
				int num_nodo = scanner.nextInt();
				// Posici�n (x,y) del nodo
				double pos_x = scanner.nextDouble();
				double pos_y = scanner.nextDouble();

				// A�adimos posici�n al vector
				posicion.add(pos_x);
				posicion.add(pos_y);
				posiciones[i] = posicion;

				// A�adimos demanda al vector
				demanda[i] = scanner.nextInt();
			}
			scanner.close();
		} catch (IOException e) {
			System.out.println("No se pudo leer el archivo correctamente.");
		}
		double[][] distancia = calcularDistancias(posiciones);
		return new InstanciaPHub(nodos, servidores, distancia, demanda, capacidad);

	}
}
