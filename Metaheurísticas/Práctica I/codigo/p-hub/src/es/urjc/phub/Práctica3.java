package es.urjc.phub;

import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Random;
import java.util.concurrent.TimeUnit;

import es.urjc.phub.Pr�ctica3.Enfriamiento.TIPOS_ENFRIAMIENTO;

public class Pr�ctica3 {

	public static class Enfriamiento {

		public enum TIPOS_ENFRIAMIENTO {
			DESCENSO_GEOMETRICO, BOLTZMANN, CAUCHY, LUNDYyMEES
		}

		public static double descensoGeom�trico(double t0, double alpha, int i) {
			double valores[] = new double[i + 1];
			valores[0] = t0;
			for (int k = 1; k <= i; k++) {
				valores[k] = valores[k - 1] * alpha;
			}
			return valores[i + 1];
		}

		public static double crierioBoltzmann(double t0, double alpha, int i) {
			return t0 / (1 + Math.log(i));
		}

		public static double esquemaCauchy(double t0, double alpha, int i) {
			return t0 / (1 + i);
		}

		public static double lundyYMees(double t0, double beta, int i) {
			double valores[] = new double[i + 1];
			valores[0] = t0;
			for (int k = 1; k <= i; k++) {
				valores[k] = valores[k - 1] / (1 + beta * valores[k - 1]);
			}
			return valores[i + 1];
		}

	}

	static double reducirTemperatura(int t0, int k) {

		return t0 / (1 + Math.log(k));
	}

	static Soluci�n recocidoSimulado(Enfriamiento.TIPOS_ENFRIAMIENTO tipo, Soluci�n sol_ini, InstanciaPHub instancia,
			int temp_ini, int nrep, double alpha, long tiempo) {

		double tk = 0;

		Soluci�n x = sol_ini;

		int i = 1;

		switch (tipo) {
		case BOLTZMANN:
			tk = Enfriamiento.crierioBoltzmann(temp_ini, alpha, i);
			break;
		case CAUCHY:
			tk = Enfriamiento.esquemaCauchy(temp_ini, alpha, i);
			break;
		case LUNDYyMEES:
			tk = Enfriamiento.esquemaCauchy(temp_ini, alpha, i);
			break;
		case DESCENSO_GEOMETRICO:
			tk = Enfriamiento.crierioBoltzmann(temp_ini, alpha, i);
		}

		long fin = System.currentTimeMillis() + tiempo;

		double delta;

		do {
			int m = 0;
			do {
				// Generamos n�mero aleatorio entre 0 y n�mero de soluciones de
				// la vecindad - 1
				Random r = new Random();
				List<Soluci�n> vecindad = PHub.generarVecindad(x, instancia);
				int n = r.nextInt(vecindad.size());

				Soluci�n y = vecindad.get(n);

				delta = y.getObjetivo() - x.getObjetivo();

				if (delta < 0) {
					x = y;
				} else {
					double u = r.nextDouble();
					if (u <= Math.exp(-delta / tk)) {
						x = y;
					}
				}
				m++;
			} while (m != nrep);
			switch (tipo) {
			case BOLTZMANN:
				tk = Enfriamiento.crierioBoltzmann(temp_ini, alpha, i);
				break;
			case CAUCHY:
				tk = Enfriamiento.esquemaCauchy(temp_ini, alpha, i);
				break;
			case LUNDYyMEES:
				tk = Enfriamiento.esquemaCauchy(temp_ini, alpha, i);
				break;
			case DESCENSO_GEOMETRICO:
				tk = Enfriamiento.crierioBoltzmann(temp_ini, alpha, i);
			}
			i++;
		} while (System.currentTimeMillis() <= fin);

		return x;

	}

	public static void main(String args[]) {

		// Leemos las instancias del directorio `instancias`
		List<InstanciaPHub> instancias = PHub.leerInstanciasDesdeDirectorio("instancias");

		int temp_ini = 10000;

		double alpha = 0.01;
		double alpha_geometrico = 0.99;
		double beta = 0.001;

		int nrep = 250;

		long tiempo = 60000;

		int inst = 1;
		
		String csv = "";

		Date inicio_fecha = new Date(System.currentTimeMillis());
		System.out.println("Inicio: " + inicio_fecha);
		System.out.println();

//		for (InstanciaPHub instancia : instancias) {
//			System.out.println("=====N�mero de instancia: " + inst);
//			
//			Soluci�n sol_ini = instancia.generarSoluci�nAleatoria();
//			
//			csv += inst + ",";
//			
//			System.out.println("---Soluci�n inicial");
//			System.out.println("Soluci�n inicial: " + Arrays.toString(sol_ini.getSolucion()));
//			System.out.println("Matriz de adyacencia: " + Arrays.deepToString(sol_ini.getMatrizAdyacencia()));
//			System.out.println("Funci�n objetivo: " + sol_ini.getObjetivo());
//			System.out.println();
//			
//			for (Enfriamiento.TIPOS_ENFRIAMIENTO tipo : Enfriamiento.TIPOS_ENFRIAMIENTO.values()) {
//				
//				if(tipo == TIPOS_ENFRIAMIENTO.DESCENSO_GEOMETRICO) alpha = alpha_geometrico;
//				if(tipo == TIPOS_ENFRIAMIENTO.LUNDYyMEES) alpha = beta;
//						
//				Soluci�n s = recocidoSimulado(tipo, sol_ini, instancia, temp_ini, nrep, alpha, tiempo);
//				
//				System.out.println("---Soluci�n con recocido simulado de tipo de enfriamiento " + tipo);
//				System.out.println("Soluci�n tras recocido simulado: " + Arrays.toString(s.getSolucion()));
//				System.out.println("Matriz de adyacencia: " + Arrays.deepToString(s.getMatrizAdyacencia()));
//				System.out.println("Funci�n objetivo: " + s.getObjetivo());
//				System.out.println();
//				
//				csv += s.getObjetivo() + ",";
//			}
//			inst++;
//			
//			csv += "\n";
//		}

		
		for (InstanciaPHub instancia : instancias) {
			System.out.println("=====N�mero de instancia: " + inst);
			
			Soluci�n sol_ini = instancia.generarSoluci�nAleatoria();
			
			csv += inst + ",";
			
			System.out.println("---Soluci�n inicial");
			System.out.println("Soluci�n inicial: " + Arrays.toString(sol_ini.getSolucion()));
			System.out.println("Matriz de adyacencia: " + Arrays.deepToString(sol_ini.getMatrizAdyacencia()));
			System.out.println("Funci�n objetivo: " + sol_ini.getObjetivo());
			System.out.println();
					
			for(int i = 0; i <= 10; i++){				
				beta = Math.pow(10, -i);
				Soluci�n s = recocidoSimulado(TIPOS_ENFRIAMIENTO.LUNDYyMEES, sol_ini, instancia, temp_ini, nrep, beta, tiempo);
				
				System.out.println("---Soluci�n con recocido simulado de tipo Lundi y Mees de par�metro: " + beta);
				System.out.println("Soluci�n tras recocido simulado: " + Arrays.toString(s.getSolucion()));
				System.out.println("Matriz de adyacencia: " + Arrays.deepToString(s.getMatrizAdyacencia()));
				System.out.println("Funci�n objetivo: " + s.getObjetivo());
				System.out.println();
			
				csv += s.getObjetivo() + ",";
			}

			inst++;
			
			csv += "\n";
		}
		Date fin_fecha = new Date(System.currentTimeMillis());
		System.out.println("Inicio: " + fin_fecha);
		System.out.println();
		System.out.println("Tiempo total: "
				+ TimeUnit.MINUTES.convert(fin_fecha.getTime() - inicio_fecha.getTime(), TimeUnit.MILLISECONDS)
				+ " minutos");
		
		System.out.println(csv);

	}
}
