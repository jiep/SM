package es.urjc.phub;

import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Práctica1 {

	public static void main(String[] args) {
		
		// Establecemos el punto como separador de los decimales
		DecimalFormatSymbols simbolos = new DecimalFormatSymbols();
		simbolos.setDecimalSeparator('.');

		// Generamos 1000 simulaciones de cada instancia

		int n = 1000;

		// Cargamos las instancias de cada fichero
		List<InstanciaPHub> instancias = PHub.leerInstanciasDesdeDirectorio("instancias");

		List<Solución> mejores_soluciones = new ArrayList<>();
		
		// Número de instancia
		int inst = 1;
		
		for (InstanciaPHub instancia : instancias) {
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
			
			// Imprimimos los valores de la mejor solución de cada instancia
			System.out.println("=====Número de instancia: " + inst);
			int servidores[] = new int[instancia.getServidores()];
			
			int cuenta = 0;
			for(int indice = 0; indice < mejor_solucion.getSolucion().length; indice++){
				if(mejor_solucion.getSolucion()[indice]){
					servidores[cuenta] = indice+1;
					cuenta++;
				}
			}
            DecimalFormat df = new DecimalFormat("#.####", simbolos);
			System.out.println("Solución: " + Arrays.toString(mejor_solucion.getSolucion()));
			System.out.println("Servidores: " + Arrays.toString(servidores));
			System.out.println("Matriz de adyacencia: " + Arrays.deepToString(mejor_solucion.getMatrizAdyacencia()));
			System.out.println("Función objetivo: " + df.format(mejor_solucion.getObjetivo()));
			System.out.println();
			inst++;
			
		}
		
			

	}

}
