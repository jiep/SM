package es.urjc.phub;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Pr�ctica2 {
	
	public static Soluci�n constAleatoriaYMejoraLexicogr�fica(Soluci�n sol, InstanciaPHub instancia){
		
		Soluci�n result = null;
		
		List<Soluci�n> vecindad = PHub.generarVecindad(sol, instancia);
		
		result = Utils.busquedaLocal("lexicogr�fico", vecindad, sol);
		
		return result;
	}
	
	public static Soluci�n constAleatoriaYMejoraAleatorio(Soluci�n sol, InstanciaPHub instancia){
		
		Soluci�n result = null;
		
		List<Soluci�n> vecindad = PHub.generarVecindad(sol, instancia);
		
		result = Utils.busquedaLocal("aleatorio", vecindad, sol);
		
		return result;
	}
	
public static Soluci�n constAleatoriaYMejoraLexicogr�ficaFirst(Soluci�n sol, InstanciaPHub instancia){
		
		Soluci�n result = null;
		
		List<Soluci�n> vecindad = PHub.generarVecindad(sol, instancia);
		
		result = Utils.busquedaLocalFirst("lexicogr�fico", vecindad, sol);
		
		return result;
	}
	
	public static Soluci�n constAleatoriaYMejoraAleatorioFirst(Soluci�n sol, InstanciaPHub instancia){
		
		Soluci�n result = null;
		
		List<Soluci�n> vecindad = PHub.generarVecindad(sol, instancia);
		
		result = Utils.busquedaLocalFirst("aleatorio", vecindad, sol);
		
		return result;
	}

	public static void main(String[] args) {
		


		// Cargamos las instancias de cada fichero
		List<InstanciaPHub> instancias = PHub.leerInstanciasDesdeDirectorio("instancias");


		// N�mero de instancia
		int inst = 1;
		
		
		
		double dev_aleatorio = 0;
		double dev_lexicografico = 0;
		
		double dev_aleatorio_first = 0;
		double dev_lexicografico_first = 0;

		String s = "";
		
		for(InstanciaPHub instancia : instancias){
			Soluci�n sol_aleatoria = instancia.generarSoluci�nAleatoria();
						
			System.out.println("=====N�mero de instancia: " + inst);
			
			System.out.println("----B�squeda best improvement:");
			System.out.println();
			
			System.out.println("Soluci�n generada aleatoriamente:");
			System.out.println("Soluci�n tras b�squeda: " + Arrays.toString(sol_aleatoria.getSolucion()));
			System.out.println("Matriz de adyacencia: " + Arrays.deepToString(sol_aleatoria.getMatrizAdyacencia()));
			System.out.println("Funci�n objetivo: " + sol_aleatoria.getObjetivo());
			System.out.println();
		
		
			Soluci�n sol_busq_aleatoria = constAleatoriaYMejoraAleatorio(sol_aleatoria, instancia);
			Soluci�n sol_busq_lexicografico = constAleatoriaYMejoraLexicogr�fica(sol_aleatoria, instancia);
				
			System.out.println("B�squeda lexicogr�fica:");
			int servidores[] = new int[instancia.getServidores()];
			
			int cuenta = 0;
			for(int indice = 0; indice < sol_busq_lexicografico.getSolucion().length; indice++){
				if(sol_busq_lexicografico.getSolucion()[indice]){
					servidores[cuenta] = indice+1;
					cuenta++;
				}
			}
			
			System.out.println("Servidores: " + Arrays.toString(servidores));
			System.out.println("Soluci�n tras b�squeda: " + Arrays.toString(sol_busq_lexicografico.getSolucion()));
			System.out.println("Matriz de adyacencia: " + Arrays.deepToString(sol_busq_lexicografico.getMatrizAdyacencia()));
			System.out.println("Funci�n objetivo: " + sol_busq_lexicografico.getObjetivo());
			System.out.println();
			
			s+= sol_busq_lexicografico.getObjetivo() + ";" + Arrays.toString(servidores) + ";";

			
			System.out.println("B�squeda aleatoria:");
			servidores = new int[instancia.getServidores()];
			
			cuenta = 0;
			for(int indice = 0; indice < sol_busq_aleatoria.getSolucion().length; indice++){
				if(sol_busq_aleatoria.getSolucion()[indice]){
					servidores[cuenta] = indice+1;
					cuenta++;
				}
			}
			
			System.out.println("Servidores: " + Arrays.toString(servidores));
			System.out.println("Soluci�n tras b�squeda: " + Arrays.toString(sol_busq_aleatoria.getSolucion()));
			System.out.println("Matriz de adyacencia: " + Arrays.deepToString(sol_busq_aleatoria.getMatrizAdyacencia()));
			System.out.println("Funci�n objetivo: " + sol_busq_aleatoria.getObjetivo());
			System.out.println();
			
			s+= sol_busq_aleatoria.getObjetivo() + ";" + Arrays.toString(servidores) + ";";

			

			
			System.out.println("----B�squeda first improvement:");
			
			Soluci�n sol_busq_aleatoria_first = constAleatoriaYMejoraAleatorioFirst(sol_aleatoria, instancia);
			Soluci�n sol_busq_lexicografico_first = constAleatoriaYMejoraLexicogr�ficaFirst(sol_aleatoria, instancia);
			
			System.out.println("B�squeda lexicogr�fica:");
			servidores = new int[instancia.getServidores()];
			
			cuenta = 0;
			for(int indice = 0; indice < sol_busq_lexicografico_first.getSolucion().length; indice++){
				if(sol_busq_lexicografico_first.getSolucion()[indice]){
					servidores[cuenta] = indice+1;
					cuenta++;
				}
			}
			
			s+= sol_busq_lexicografico_first.getObjetivo() + ";" + Arrays.toString(servidores) + ";";

			
			System.out.println("Servidores: " + Arrays.toString(servidores));
			System.out.println("Soluci�n tras b�squeda: " + Arrays.toString(sol_busq_lexicografico_first.getSolucion()));
			System.out.println("Matriz de adyacencia: " + Arrays.deepToString(sol_busq_lexicografico_first.getMatrizAdyacencia()));
			System.out.println("Funci�n objetivo: " + sol_busq_lexicografico_first.getObjetivo());
			System.out.println();
			
			System.out.println("B�squeda aleatoria:");
			servidores = new int[instancia.getServidores()];
			
			cuenta = 0;
			for(int indice = 0; indice < sol_busq_aleatoria_first.getSolucion().length; indice++){
				if(sol_busq_aleatoria_first.getSolucion()[indice]){
					servidores[cuenta] = indice+1;
					cuenta++;
				}
			}
			
			
			System.out.println("Servidores: " + Arrays.toString(servidores));
			System.out.println("Soluci�n tras b�squeda: " + Arrays.toString(sol_busq_aleatoria_first.getSolucion()));
			System.out.println("Matriz de adyacencia: " + Arrays.deepToString(sol_busq_aleatoria_first.getMatrizAdyacencia()));
			System.out.println("Funci�n objetivo: " + sol_busq_aleatoria_first.getObjetivo());
			
			System.out.println("==========================================");
			
			s+= sol_busq_aleatoria_first.getObjetivo() + ";" + Arrays.toString(servidores) + ";";

			
			inst++;
			
			dev_aleatorio += (sol_aleatoria.getObjetivo() - sol_busq_aleatoria.getObjetivo())/(100*sol_aleatoria.getObjetivo());
			dev_lexicografico += (sol_aleatoria.getObjetivo() - sol_busq_lexicografico.getObjetivo())/(100*sol_aleatoria.getObjetivo());

			dev_aleatorio_first += (sol_aleatoria.getObjetivo() - sol_busq_aleatoria_first.getObjetivo())/(100*sol_aleatoria.getObjetivo());
			dev_lexicografico_first += (sol_aleatoria.getObjetivo() - sol_busq_lexicografico_first.getObjetivo())/(100*sol_aleatoria.getObjetivo());

			
			s+="\n";
		}
		
		dev_aleatorio /= inst;
		dev_lexicografico /= inst;
		
		dev_aleatorio_first /= inst;
		dev_lexicografico_first /= inst;

		System.out.println("Best improvement: ");
		System.out.println("Dev lexicogr�fico: " + dev_lexicografico);
		System.out.println("Dev aleatorio: " + dev_aleatorio);
		System.out.println();
		
		System.out.println("First improvement: ");
		System.out.println("Dev lexicogr�fico: " + dev_lexicografico_first);
		System.out.println("Dev aleatorio: " + dev_aleatorio_first);
		
		
		System.out.println("CSV:----------");
		System.out.println(s);


	}



}
