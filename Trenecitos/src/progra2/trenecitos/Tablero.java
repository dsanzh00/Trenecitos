package progra2.trenecitos;

public class Tablero {

	private String[][] tablero;
	private int filas;
	private int columnas;
	private Tren tren;
	
	public Tablero (int filas, int columnas, Tren tren) {
		this.filas = filas;
		this.columnas = columnas;
		this.tren = tren;
		tablero = new String[this.filas][this.columnas];
		rellenarTablero();
	}
	
	public void rellenarTablero() {
		for(int i=0; i<tablero.length; i++) {
			for(int j = 0; j<tablero.length; j++) {
				tablero[i][j] = ".";
			}
		}
	}
	
	public void colocacion() {
		for(int i = 0; i < tren.getNumTrenes(); i++) {
			String direccion = tren.getDireccion(i);
			int nTrenes = tren.getNumVagones(i);
			int cabezaFila = (filas-1) - tren.getValorY(i);
			int cabezaColumna = tren.getValorX(i);
			pintaTrenes(direccion, nTrenes, cabezaFila, cabezaColumna, i);
		}
	}
	
	public void pintaTrenes(String direccion, int nTrenes, int fila, int columna, int id) {
		for(int i=0; i<nTrenes;i++) {
			if(direccion.equals("A")) {
				if(fila+i>29 || fila+i<0 || columna>29 || columna<0 || !tablero[fila+i][columna].equals(".")) {
					System.out.println("Conjunto de trenes incorrecto.");
					System.exit(0);
				}
					tablero[fila+i][columna] = String.valueOf(id);
			}else if(direccion.equals("B")) {
				if(fila-i>29 || fila-i<0 || columna>29 || columna<0 || !tablero[fila-i][columna].equals(".")) {
					System.out.println("Conjunto de trenes incorrecto.");
					System.exit(0);
				}
					tablero[fila-i][columna] = String.valueOf(id);
			}else if(direccion.equals("I")) {
				if(fila>29 || fila<0 || columna+i>29 || columna+i<0 || !tablero[fila][columna+i].equals(".")) {
					System.out.println("Conjunto de trenes incorrecto.");
					System.exit(0);
				}
					tablero[fila][columna+i] = String.valueOf(id);
			}else if(direccion.equals("D")) {
				if(fila>29 || fila<0 || columna-i>29 || columna-i<0 || !tablero[fila][columna-i].equals(".")) {
					System.out.println("Conjunto de trenes incorrecto.");
					System.exit(0);
				}
					tablero[fila][columna-i] = String.valueOf(id);
			}	
		}
	}
	
	public void muestra() {
		StringBuilder sb = new StringBuilder("   0 0 0 0 0 0 0 0 0 0 1 1 1 1 1 1 1 1 1 1 2 2 2 2 2 2 2 2 2 2\n");
		sb.append("   0 1 2 3 4 5 6 7 8 9 0 1 2 3 4 5 6 7 8 9 0 1 2 3 4 5 6 7 8 9\n"); 
		for (int i = 0; i < tablero.length; i++) {
			if(i>=20) sb.append("0");
			sb.append((tablero.length - 1 - i)+ " ");
			for (int j = 0; j < tablero.length; j++) {
				sb.append(tablero[i][j]);
				if(j<tablero.length - 1){
				    sb.append(" ");
				}
			}
			sb = sb.replace(sb.length(), sb.length(), "\n");
		}
		System.out.println(sb.toString());
	}
	
	public boolean sigue() {
		for (int i = 0; i < tablero.length; i++) {
			for (int j = 0; j < tablero.length; j++) {
				if(tablero[i][j].equals(".") == false && tablero [i][j].equals("X") == false) {
					return true;
				}	
			}	
		}
		return false;
	}
	
	public void simula() {
		do {
			for (int i = 0; i < tren.getNumTrenes(); i++) {
				String direccion = tren.getDireccion(i);
				int numVagones = tren.getNumVagones(i);
				int cabezaFila = 29 - tren.getValorY(i);
				int cabezaColumna = tren.getValorX(i);
				movimiento(direccion, numVagones, cabezaFila, cabezaColumna, i);
			}
		}while(sigue());
	}
	
	public void movimiento (String direccion, int numVagones, int fila, int columna, int id) {
		for (int i = 0; i < numVagones; i++) {
			if(direccion.equals("A")) {
				if(tablero[fila+i][columna].equals(String.valueOf(id))) {
					if(fila+i-1>=0) {
						if(tablero[fila+i-1][columna].equals(".")==false) {
							tablero[fila+i-1][columna]="X";
							tablero[fila+i][columna]=".";
						}else {
							tablero[fila+i-1][columna]=String.valueOf(id);
							tablero[fila+i][columna]=".";
							if(i==0){
								tren.setValorX(columna, id);
								tren.setValorY(29-(fila-1), id);
							}
						}
					}else {
						tablero[0][columna]=".";
					}
				}
			}else if(direccion.equals("B")) {
				if(tablero[fila-i][columna].equals(String.valueOf(id))) {
					if(fila-i+1<=tablero.length-1) {
						if(tablero[fila-i+1][columna].equals(".")==false) {
							tablero[fila-i+1][columna]="X";
							tablero[fila-i][columna]=".";
						}else {
							tablero[fila-i+1][columna]=String.valueOf(id);
							tablero[fila-i][columna]=".";
							if(i==0){
								tren.setValorX(columna, id);
								tren.setValorY(29-(fila+1), id);
							}
						}
					}else {
						tablero[tablero.length-1][columna]=".";

					}
				}
			}else if(direccion.equals("D")) {
				if(tablero[fila][columna-i].equals(String.valueOf(id))) {
					if(columna-i+1<=tablero.length-1) {
						if(tablero[fila][columna-i+1].equals(".")==false) {
							tablero[fila][columna-i+1]="X";
							tablero[fila][columna-i]=".";
						}else {
							tablero[fila][columna-i+1]=String.valueOf(id);
							tablero[fila][columna-i]=".";
							if(i==0){
								tren.setValorX(columna+1, id);
								tren.setValorY(29-fila, id);
							}
						}
					}else {
						tablero[fila][tablero.length-1]=".";

					}
				}
			}else {
				if(tablero[fila][columna+i].equals(String.valueOf(id))) {
					if(columna+i-1>=0) {
						if(tablero[fila][columna+i-1].equals(".")==false) {
							tablero[fila][columna+i-1]="X";
							tablero[fila][columna+i]=".";
						}else {
							tablero[fila][columna+i-1]=String.valueOf(id);
							tablero[fila][columna+i]=".";
							if(i==0){
								tren.setValorX(columna-1, id);
								tren.setValorY(29-fila, id);
							}
						}
					}else {
						tablero[fila][0]=".";

					}
				}			
			}
		}
	}
	
	public void choques() {
		System.out.println("choque");
		for (int i = 0; i < tren.getNumChoques(); i++) {
			int fila = filas - 1 - tren.getValorF(i);
			int columna = tren.getValorC(i);
			pintaChoque(fila, columna);
		}
	}
	
	public void pintaChoque(int fila, int columna) {
		if(tablero[fila][columna].equals(".")) {
			tablero[fila][columna]= "X";
		}else {
			System.out.println("Conjunto de trenes incorrecto.");
		}
	}
}
