package progra2.trenecitos;

public class Tren {

	int numTrenes;
	String[] direccion;
	int[] numVagones;
	int[] valorX;
	int[] valorY;
	int fila;
	int columna;
	int[] valorF;
	int[] valorC;
	int numChoques; // no es array
	
	
	
	public Tren (int fila, int columna) {
		this.fila = fila;
		this.columna = columna;
	}
	
	public void nuevo(String nextLine, int i) {
		String[] hueco = nextLine.split(" ");
		if(hueco.length != 4) {
			System.out.println("Conjunto de trenes incorrecto.");
		}
		direccion[i] = hueco[0];
		numVagones[i] = Integer.parseInt(hueco[1]);
		valorX[i] = Integer.parseInt(hueco[2]);
		valorY[i] = Integer.parseInt(hueco[3]);
		errores(i);
	}
	
	public void inicializacion() {
		direccion = new String[numTrenes];
		numVagones = new int [numTrenes];
		valorX = new int[numTrenes];
		valorY = new int[numTrenes];
		valorF = new int[numChoques];
		valorC = new int[numChoques];
	}
	
	
	public void setNumTrenes(int numTrenes) {
		if(numTrenes < 1){
			System.out.println("Conjunto de trenes incorrecto.");
			System.exit(0);
		}
		this.numTrenes = numTrenes;
		inicializacion();
	}
	
	public int getNumTrenes() {
		return numTrenes;
	}
	
	public String getDireccion(int i) {
		return direccion[i];
	}
	
	public int getNumVagones(int i) {
		return numVagones[i];
	}
	
	public int getValorX(int i) {
		return valorX[i];
	}
	
	public int getValorY(int i) {
		return valorY[i];
	}
	
	public int getValorF(int i) {
		return valorF[i];
	}
	
	public int getValorC(int i) {
		return valorC[i];
	}
	
	public void setValorX(int x, int i) {
		valorX[i] = x;
	}
	
	public void setValorY(int y, int i) {
		valorY[i] = y;
	}
	
	public void desaparece(int id) {
		numVagones[id]--;
	}
	
	public void errores(int i) {
		
		if(!direccion[i].equals("A") && !direccion[i].equals("B") && !direccion[i].equals("D")  && !direccion[i].equals("I")){
			System.out.println("Error2");
			System.exit(0);
		}
		if(numVagones[i]<1 || numVagones[i]>30) {
			System.out.println("Error3");
			System.exit(0);
		}
		if(valorX[i]<0 || valorX[i]>30) {
			System.out.println("Error4");
			System.exit(0);
		}
		if(valorY[i]<0 || valorY[i]>30) {
			System.out.println("Error5");
			System.exit(0);
		}
		
		int contador = 0;
		for (int j = 0; j < direccion.length; j++) {
			contador = numVagones[j] + contador;
		}
		if(contador>100) {
			System.out.println("Error6");
			System.exit(0);
		}
	}
	
	public void nuevoChoque(String[] choque, int i){
		if(choque.length != 2) {	
			System.out.println("Conjunto de trenes incorrecto.");
		}
		valorF[i] = Integer.parseInt(choque[0]);
		valorC[i] = Integer.parseInt(choque[1]);
		if(valorF[i]>=fila || valorF[i]<0 || valorC[i]>=columna || valorC[i]<0) {
			System.out.println("Conjunto de trenes incorrecto");
		}
	}
	
	public void setNumChoques(int numChoques) {
		this.numChoques = numChoques;
		inicializacion();
	}

	public int getNumChoques() {
		return numChoques;
	}
	

	
	
}
