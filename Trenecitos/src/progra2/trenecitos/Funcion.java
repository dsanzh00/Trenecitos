package progra2.trenecitos;

import java.util.Scanner;
import java.io.IOException;

public class Funcion {
	private static Scanner teclado = new Scanner(System.in);
	
	private Tren tren;
	private String[] trenes;
	private String[][] choques;
	
	
	public Funcion(String[] trenes, String[][] choques, int filas, int columnas) {
		this.trenes = trenes;
		this.choques = choques;
		tren = new Tren(filas, columnas);
		leer();
	}
	private void leer(){
		tren.setNumTrenes(trenes.length);
		tren.setNumChoques(choques.length);
		for(int i = 0; i < tren.getNumTrenes(); i++) {
			tren.nuevo(trenes[i], i);
		}
		
		for (int i = 0; i < choques.length; i++) {
			tren.nuevoChoque(choques[i], i);
		}
	}
	
	public Tren getTren() {
		return tren;
	}
}
