package progra2.trenecitos;

import java.util.Scanner;
import java.io.IOException;

public class Trenecitos {
	
	private static Scanner teclado = new Scanner(System.in);
	private Funcion funcion;
	
	public Trenecitos(String[] trenes, String[][] choques, int filas, int columnas) {
		do {
			funcion = new Funcion (trenes, choques, filas, columnas);
			Tablero tablero = new Tablero(filas, columnas, funcion.getTren());
			tablero.colocacion();
			tablero.simula();
			tablero.muestra();
		}while(teclado.hasNext());

	}
	
	public Tren getTren() {
		return funcion.getTren();
	}

}
