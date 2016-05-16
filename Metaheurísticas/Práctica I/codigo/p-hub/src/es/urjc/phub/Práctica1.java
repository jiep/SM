package es.urjc.phub;

import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Pr�ctica1 {

	public static void main(String[] args) {
		
		// Establecemos el punto como separador de los decimales
		DecimalFormatSymbols simbolos = new DecimalFormatSymbols();
		simbolos.setDecimalSeparator('.');

		// Generamos 1000 simulaciones de cada instancia

		int n = 1000;

		// Cargamos las instancias de cada fichero
		List<InstanciaPHub> instancias = PHub.leerInstanciasDesdeDirectorio("instancias");

		List<Soluci�n> mejores_soluciones = new ArrayList<>();
		
		// N�mero de instancia
		int inst = 1;
		
		for (InstanciaPHub instancia : instancias) {
			Soluci�n mejor_solucion = null;
			double obj = 100000000;
			for (int i = 0; i < n; i++) {
				Soluci�n s1 = instancia.generarSoluci�nAleatoria();
				if (s1.getObjetivo() < obj) {
					mejor_solucion = s1;
					obj = s1.getObjetivo();
				}
			}
			mejores_soluciones.add(mejor_solucion);
			
			// Imprimimos los valores de la mejor soluci�n de cada instancia
			System.out.println("=====N�mero de instancia: " + inst);
			int servidores[] = new int[instancia.getServidores()];
			
			int cuenta = 0;
			for(int indice = 0; indice < mejor_solucion.getSolucion().length; indice++){
				if(mejor_solucion.getSolucion()[indice]){
					servidores[cuenta] = indice+1;
					cuenta++;
				}
			}
            DecimalFormat df = new DecimalFormat("#.####", simbolos);
			System.out.println("Soluci�n: " + Arrays.toString(mejor_solucion.getSolucion()));
			System.out.println("Servidores: " + Arrays.toString(servidores));
			System.out.println("Matriz de adyacencia: " + Arrays.deepToString(mejor_solucion.getMatrizAdyacencia()));
			System.out.println("Funci�n objetivo: " + df.format(mejor_solucion.getObjetivo()));
			System.out.println();
			inst++;
			
		}
		
			

	}

}
