package progra2.trenecitos;
import java.awt.Color;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;
import javax.swing.border.EtchedBorder;
import javax.swing.border.TitledBorder;
import java.awt.Font;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import javax.swing.JMenuBar;
import javax.swing.JMenu;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.ActionEvent;

public class PintaTablero extends JFrame implements Runnable {
	
	static final long serialVersionUID = 1L;
	
	int id = 0;
	int filas = 10;
	int columnas = 10;
	
	
	FormaTren[][] tablero;
	boolean results[] = new boolean[]{true,false,true}; 

	ArrayList<String> total = new ArrayList<String>();
	ArrayList<ArrayList<String>> totalDeshacer = new ArrayList<ArrayList<String>>();
	ArrayList<ArrayList<String>> totalRehacer = new ArrayList<ArrayList<String>>();
	ArrayList<FormaTren[][]> deshacer = new ArrayList<FormaTren[][]>();
	ArrayList<FormaTren[][]> again = new ArrayList<FormaTren[][]>();
	ArrayList<EntLetrero> trenesLet = new ArrayList<>();
	
	
	JPanel panel;
	JPanel letrero;
	JMenuItem menuAgain;
	JMenuItem menuDeshacer;
	
	int rojo=100, verde=100, azul=0;
	int movimiento;
	
	
	Thread hilo = new Thread(this);

	
	public PintaTablero(int filas, int columnas) {
		this.filas = filas;
		this.columnas = columnas;
		tablero = new FormaTren[filas][columnas];
		
		setTitle("Trenes");
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setBounds(100, 100, 20*columnas+250, 20*filas+140);
		setLocationRelativeTo(null);
		panel = new JPanel();

		letrero = new JPanel();	
		letrero.setBorder(new TitledBorder(new EtchedBorder(EtchedBorder.LOWERED, new Color(120, 120, 120), new Color(225, 225, 225)), "letrero", TitledBorder.LEFT, TitledBorder.TOP, null, new Color(0, 0, 0)));
		letrero.setBounds(20*columnas + 70, 10, 150, (filas+2)*20-5);
		letrero.setLayout(null);
		panel.add(letrero);	

		JMenuBar barra = new JMenuBar();
		setJMenuBar(barra);

		JMenu opciones = new JMenu("Opciones");	
		barra.add(opciones);

		JMenuItem opcion1 = new JMenuItem("Guardar");
		opcion1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					guardar();
				} catch (IOException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}			
			}
		});
		opciones.add(opcion1);

		JMenuItem opcion2 = new JMenuItem("Cerrar");
		opcion2.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				dispose();
			}
		});
		opciones.add(opcion2);

		JMenu editar = new JMenu("Editar");
		barra.add(editar);

		menuDeshacer = new JMenuItem("Deshacer"); 
		menuDeshacer.setEnabled(false);
		menuDeshacer.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				atras();
			}
		});
		editar.add(menuDeshacer);

		menuAgain = new JMenuItem("Rehacer");
		menuAgain.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				adelante();
			}
		});
		menuAgain.setEnabled(false);
		editar.add(menuAgain);

		JMenuItem newTren = new JMenuItem("Nuevo tren");
		newTren.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				Disenio tren = new Disenio();
				tren.setVisible(true);
				tren.setFilas(filas);
				tren.setColumnas(columnas);
				tren.getBoton().addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent e) {
						if(tren.correcto()) {
								String direccion=" ";
								int longTren = tren.getLongTren();
								int x = tren.getValorXCabeza();
								int y = tren.getValorYCabeza();
								String newTren;

								switch(tren.getDireccion()) {
								case "Arriba":
									direccion="A";
									break;
								case "Derecha":
									direccion="D";
									break;
								case "Abajo":
									direccion="B";
									break;
								case "Izquierda":
									direccion="I";
									break;
								}
								newTren = direccion +" " + longTren + " " + x + " " + y + " " + id;
								comprobacion(direccion, longTren, x, y);
								tren.dispose();
								configuracion(direccion, longTren, x, y);
								total.add(newTren);
								id++;
								panel.repaint();

							}else {
								System.out.println("Mal posicionamiento de Trenes");
							}
						}
				});
			}
		});
		editar.add(newTren);

		JMenuItem borrar = new JMenuItem("Borrar");
		borrar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				ArrayList<Integer> validados = new ArrayList<Integer>();
				for (int i = 0; i < filas; i++) {
					for (int j = 0; j < columnas; j++) {
						int idTren = tablero[i][j].getIdTren();
						if(idTren!=-1 && validados.contains(idTren)==false) {
							validados.add(tablero[i][j].getIdTren());
						}
					}
				}
				if(validados.size()==0) {
					System.out.println("No hay trenes validados");
				}else {
					Eliminar delete = new Eliminar(validados);
					delete.setVisible(true);
					delete.getBoton().addActionListener(new ActionListener() {

						@Override
						public void actionPerformed(ActionEvent e) {
							int trenABorrar = delete.getTrenEliminar();
							delete(trenABorrar);
							panel.repaint();
							delete.dispose();
						}
					});
				}
			}
		});
		editar.add(borrar);

		JMenu simula = new JMenu("Simulacion");
		barra.add(simula);

		JMenuItem simula2 = new JMenuItem("Simulando");	
		simula2.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				hilo.start();
			}
		});
		simula.add(simula2);
		
		JMenu ayuda = new JMenu("Ayuda");	
		ayuda.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
			}
		});
		barra.add(ayuda);
		
		JMenuItem nueva = new JMenuItem("Nueva");
		nueva.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				Help help = new Help();
				help.setVisible(true);
			}
		});
		ayuda.add(nueva);

		panel.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(panel);
		panel.setBackground(new Color(120, 120, 120));
		panel.setLayout(null);


		newTab();
		setResizable(false);
		setFocusable(true);
		addKeyListener(new KeyAdapter() {

			public void keyReleased(KeyEvent e) {
				totalDeshacer.add(total);
				FormaTren[][] guardado = new FormaTren[filas][columnas];
				for (int i = 0; i < filas; i++) {
					for (int j = 0; j < columnas; j++) {
						FormaTren valores = new FormaTren();
						valores.setFormaTren(tablero[i][j]);
						guardado[i][j]=valores;
					}
				}
				deshacer.add(guardado);
				menuDeshacer.setEnabled(true);
				menuAgain.setEnabled(false);
				totalRehacer.clear();
				again.clear();

				
				if(e.getKeyCode()==KeyEvent.VK_UP) {
					flechaArriba();
				}
				if(e.getKeyCode()==KeyEvent.VK_RIGHT) {
					flechaDerecha();
				}
				if(e.getKeyCode()==KeyEvent.VK_DOWN) {
					flechaIzquierda();
				}
				if(e.getKeyCode()==KeyEvent.VK_LEFT) {
					flechaAbajo();
				}
			}
		});
	}
	
	@Override
	public void run() {
		while (finVagones()) {
			for (int i = 0; i < total.size(); i++) {
				String tren = total.get(i);
				movimiento = i;
				char direccion = tren.charAt(0);
				if (direccion == 'A') {
					flechaArriba();
				} else if (direccion == 'B') {
					flechaAbajo();
				} else if (direccion == 'D') {
					flechaDerecha();
				} else if (direccion == 'I') {
					flechaIzquierda();
				}
			}
			try {
				Thread.sleep(150);
			} catch (InterruptedException e) {
			}
		}
	}

public void comprobacion(String direccion, int longTren, int columna, int fila) {
	
	for (int i = 0; i < longTren; i++) {
		
		if(direccion.equals("A")) {
			if(fila+i>filas || fila+i<0 || columna>columnas || columna<0 || tablero[fila+i][columna].getIdTren()!=-1) {
				System.out.println("Error al colocar");
			}else {
				System.out.println("Colocación correcta");
			}
		}else if(direccion.equals("B")) {
			if(fila-i>29 || fila-i<0 || columna>29 || columna<0 || tablero[fila-i][columna].getIdTren()!=-1) {
				System.out.println("Error al colocar");			
			}else {
			System.out.println("Colocación correcta");
			}
		}else if(direccion.equals("D")) {
			if(fila>29 || fila<0 || columna-i>29 || columna-i<0 || tablero[fila][columna-i].getIdTren()!=-1) {
				System.out.println("Error al colocar");					
				}else {
					System.out.println("Colocación correcta");
				}
		}else {
			if(fila>29 || fila<0 || columna+i>29 || columna+i<0 || tablero[fila][columna+i].getIdTren()!=-1) {
				System.out.println("Error al colocar");			
				}else {
					System.out.println("Colocación correcta");
				}		
		}
	}
}


public void configuracion(String direccion, int longTren, int columna, int fila) {
	Color color = new Color(rojo, verde, azul);
	Color flecha = new Color(120, 120, 120);
	rojo = rojo + 52;
	
	if(rojo%255<50) {
		verde = verde +52;
	}
	
	for (int i = 0; i < longTren; i++) {
		if(direccion.equals("A")) {
			tablero[fila+i][columna].setIdTren(id, color, flecha);
			tablero[fila+i][columna].setDireccion(direccion);
		}else if(direccion.equals("B")) {
			tablero[fila-i][columna].setIdTren(id, color, flecha);
			tablero[fila-i][columna].setDireccion(direccion);
		}else if(direccion.equals("D")) {
			tablero[fila][columna-i].setIdTren(id, color, flecha);
			tablero[fila][columna-i].setDireccion(direccion);
		}else {
			tablero[fila][columna+i].setIdTren(id, color, flecha);	
			tablero[fila][columna+i].setDireccion(direccion);
		}
	}

	FormaTren imprime = new FormaTren();
	imprime.setIdTren(id,  color, flecha);
	imprime.setDireccion(direccion);

	EntLetrero entrada = new EntLetrero(imprime, String.valueOf(id));
	trenesLet.add(entrada);
	addLetrero();
}

private void guardar() throws IOException{
	File file = new File(" ");
	JFileChooser fileChooser = new JFileChooser();
	if(fileChooser.showSaveDialog(null)==JFileChooser.APPROVE_OPTION) {
		file = fileChooser.getSelectedFile();
	}

	FileWriter datosPasados = new FileWriter(file);
	String salida = "";
	String otraLinea = "\r\n";

	salida = salida + filas + " " + columnas+otraLinea;


	salida = salida + trenesLet.size()+otraLinea;

	//Sumo las lÃ­neas con los trenes
	for (int i = 0; i < total.size(); i++) {
		if(total.get(i).equals("---")==false) {
			String[] datos = total.get(i).split(" ");
			salida = salida +datos[0] + " " + datos[1] + " " + datos[2]+ " " + datos[3] +otraLinea;
		}
	}

	//Sumo el nÃºmero de colisiones
	int nColisiones = 0;
	for (int i = 0; i < filas; i++) {
		for (int j = 0; j < columnas; j++) {
			if(tablero[i][j].choque()) nColisiones++;
		}
	}
	salida= salida + nColisiones + otraLinea;

	//Sumo las colisiones.
	for (int i = 0; i < filas; i++) {
		for (int j = 0; j < columnas; j++) {
			if(tablero[i][j].choque()) {
				salida = salida + (filas-1-i) + " " + j + otraLinea; 
			}
		}
	}
	datosPasados.write(salida);
	datosPasados.close();
}

public void atras() {

	totalRehacer.add(total);
	FormaTren[][] recuperacion = new FormaTren[filas][columnas];
	for (int i = 0; i < filas; i++) {
		for (int j = 0; j < columnas; j++) {
			FormaTren valores = new FormaTren();
			valores.setFormaTren(tablero[i][j]);
			recuperacion[i][j] = valores;
		}
	}
	again.add(recuperacion);
	
	
	
	total = totalDeshacer.get(totalDeshacer.size()-1);
	recuperacion = deshacer.get(deshacer.size()-1);
	totalDeshacer.remove(totalDeshacer.size()-1);
	deshacer.remove(deshacer.size()-1);
	for (int i = 0; i < filas; i++) {
		for (int j = 0; j < columnas; j++) {
			FormaTren valores = new FormaTren();
			valores.setFormaTren(recuperacion[i][j]);
			tablero[i][j].setFormaTren(valores);
			panel.repaint();
		}
	}

	if(deshacer.size()==0) menuDeshacer.setEnabled(false);
	menuDeshacer.setEnabled(true);

}


private void adelante() {
	
	totalDeshacer.add(total);
	FormaTren[][] salvado = new FormaTren[filas][columnas];
	for (int i = 0; i < filas; i++) {
		for (int j = 0; j < columnas; j++) {
			FormaTren c = new FormaTren();
			c.setFormaTren(tablero[i][j]);
			salvado[i][j] = c;
		}
	}
	deshacer.add(salvado);

	
	total = totalRehacer.get(totalRehacer.size()-1);
	salvado = again.get(again.size()-1);
	totalRehacer.remove(totalRehacer.size()-1);
	again.remove(again.size()-1);
	for (int i = 0; i < filas; i++) {
		for (int j = 0; j < columnas; j++) {
			//tablero[i][j].setVacia();
			FormaTren c = new FormaTren();
			c.setFormaTren(salvado[i][j]);
			tablero[i][j].setFormaTren(c);
			panel.repaint();
		}
	}

	if(again.size()==0) menuAgain.setEnabled(false);
	menuDeshacer.setEnabled(true);

}

public void setInformacion(Tren tren) {
	for (int i = 0; i < tren.getNumTrenes(); i++) {
		String direccion = tren.getDireccion(i);
		int longTren = tren.getNumVagones(i);
		int x = tren.getValorX(i);
		int y = tren.getValorY(i);
		String trenNuevo;
		trenNuevo = direccion +" " + longTren + " " + x + " " + y + " " + id;
		configuracion(direccion, longTren, x, filas-y-1);
		total.add(trenNuevo);
		id++;
	}

	for (int i = 0; i < tren.getNumChoques(); i++) {
		int f = tren.getValorF(i);
		int c = tren.getValorC(i);
		configChoque(c, filas-f-1);
	}
	panel.repaint();
}

private void configChoque(int columna, int fila) {
	Color color = new Color(0, 0, 0);
	Color flecha = new Color(255, 255, 255);
	tablero[fila][columna].setIdTren(-1, color, flecha);
	tablero[fila][columna].setDireccion("C");
}

public void addLetrero() {
	
	for (int i = 0; i < trenesLet.size(); i++) {
		
		int valorF = i%filas;
		int valorC = i/filas;
		
		FormaTren imprime = trenesLet.get(i).imprime;
		JLabel nombre = trenesLet.get(i).nombre;
		
		imprime.setBounds(10+valorC*50, 30+valorF*21, 20, 20);
		nombre.setBounds(35+valorC*50, 30+valorF*21, 20, 20);
			
		letrero.add(imprime);
		letrero.add(nombre);
	}
	
	letrero.repaint();
}

public void delete(int trenDel) {
	for (int i = 0; i < filas; i++) {
		for (int j = 0; j < columnas; j++) {
			if(tablero[i][j].getIdTren()==trenDel) {
				tablero[i][j].setCasillaVacia();
			}
		}
	}

	//Borrado de la entrada en leyenda
	for (int i = 0; i < trenesLet.size(); i++) {
		if(trenesLet.get(i).imprime.getIdTren()==trenDel) {
			trenesLet.remove(i);
		}
	}
	letrero.removeAll();
	addLetrero();

	//Borrado del arraylist totaltrenes
	for (int i = 0; i < total.size(); i++) {
		String[] tren = total.get(i).split(" ");
		if(Integer.parseInt(tren[4])==trenDel) {
			total.set(i, "  ");
		}
	}
}

public void newTab() {
	for (int i = 0; i < filas; i++) {
		int fila = filas - i - 1;
		JLabel numFilas = new JLabel(String.valueOf(fila));
		numFilas.setBounds(30, 48+i*20, 20, 20);
		panel.add(numFilas);
		for (int j = 0; j < columnas; j++) {
			
			if(i==0) {
				JTextArea numColumnas = new JTextArea(String.valueOf(j/10)+"\n"+String.valueOf(j%10));
				numColumnas.setEditable(false);
				numColumnas.setBounds(55+j*20, 15, 20, 30);
				numColumnas.setFont(new Font("Arial", Font.BOLD, 12));
				panel.add(numColumnas);

			}
			
			FormaTren valores = new FormaTren();
			valores.setValorX(j);
			valores.setValorY(i);
			panel.add(valores);
			
			valores.addMouseListener(new MouseAdapter() {
				@Override
				public void mouseEntered(MouseEvent e) {
					String lugar = valores.getValorX() + ", " + (filas - valores.getValorY()-1);
					setTitle("Nuevo tablero    " + lugar);
				}
				@Override
				public void mouseClicked(MouseEvent e) {
					if(valores.getIdTren()==-1 || valores.choque()) {
						System.out.println("No pasa nada");
					}else {
						System.out.println("Moviendo el tren " + valores.getIdTren());
						movimiento = valores.getIdTren();
					}
				}
			});

			tablero[i][j]= valores;
		}
	}
	panel.repaint();
}


private boolean finVagones() {
	for (int i = 0; i < tablero.length; i++) {
		for (int j = 0; j < tablero[i].length; j++) {
			if(tablero[i][j].getIdTren()!=-1 && tablero[i][j].choque()==false) {
				return true;
			}
		}
	}
	return false;
}

private void flechaArriba() {
	String datos = total.get(movimiento);
	String direccion = datos.split(" ")[0];

	if(direccion.equals("A")) {
		int numVagones = Integer.parseInt(datos.split(" ")[1]);
		int fila = filas-1 - Integer.parseInt(datos.split(" ")[3]);
		int columna = Integer.parseInt(datos.split(" ")[2]);

		for (int i = 0; i < numVagones; i++) {
			if(tablero[fila+i][columna].getIdTren()==movimiento && tablero[fila+i][columna].choque()==false) {
				if(fila+i-1>=0) {
					if(tablero[fila+i-1][columna].getIdTren()!=-1) {
						tablero[fila+i-1][columna].setDireccion("C");
						tablero[fila+i-1][columna].setChoque(true);
						tablero[fila+i][columna].setCasillaVacia();

					}else {
						Color color = tablero[fila+i][columna].getColor();
						Color flecha = tablero[fila+i][columna].getFlecha();
						tablero[fila+i-1][columna].setIdTren(movimiento, color, flecha);
						tablero[fila+i-1][columna].setDireccion("A");
						tablero[fila+i][columna].setCasillaVacia();
						if(i==0){
							String nuevaPosicion = direccion + " " + numVagones + " " +columna+" " + (filas-1-(fila-1));
							total.set(movimiento, nuevaPosicion);
						}
					}
				}else {
					tablero[0][columna].setCasillaVacia();
				}		
			}
			panel.repaint();
		}
	}
}

private void flechaAbajo() {
	String datos = total.get(movimiento);
	String direccion = datos.split(" ")[0];
	if(direccion.equals("B")) {
		int nVag = Integer.parseInt(datos.split(" ")[1]);
		int fila = filas-1 - Integer.parseInt(datos.split(" ")[3]);
		int columna = Integer.parseInt(datos.split(" ")[2]);

		for (int i = 0; i < nVag; i++) {

			if(tablero[fila-i][columna].getIdTren()==movimiento && tablero[fila-i][columna].choque()==false) {
				if(fila-i+1<filas) {
					if(tablero[fila-i+1][columna].getIdTren()!=-1) {
						tablero[fila-i+1][columna].setDireccion("C");
						tablero[fila-i][columna].setCasillaVacia();
						tablero[fila-i+1][columna].setChoque(true);
					}else {
						Color color = tablero[fila-i][columna].getColor();
						Color flecha = tablero[fila-i][columna].getFlecha();
						tablero[fila-i+1][columna].setIdTren(movimiento, color, flecha);
						tablero[fila-i+1][columna].setDireccion("B");
						tablero[fila-i][columna].setCasillaVacia();
						if(i==0){
							String nuevaPosicion = direccion + " " + nVag + " " +columna+" " + (filas-1-(fila+1));
							total.set(movimiento, nuevaPosicion);
						}
					}
				}else {
					tablero[filas-1][columna].setCasillaVacia();
				}

			}
			panel.repaint();
		}
	}
}

private void flechaDerecha() {
	String datos = total.get(movimiento);
	String direccion = datos.split(" ")[0];
	if(direccion.equals("D")) {
		int nVag = Integer.parseInt(datos.split(" ")[1]);
		int fila = filas-1 - Integer.parseInt(datos.split(" ")[3]);
		int columna = Integer.parseInt(datos.split(" ")[2]);


		for (int i = 0; i < nVag; i++) {

			if(tablero[fila][columna-i].getIdTren()==movimiento && tablero[fila][columna-i].choque()==false) {
				if(columna-i+1<=columnas-1) {
					if(tablero[fila][columna-i+1].getIdTren()!=-1) {
						tablero[fila][columna-i+1].setDireccion("C");
						tablero[fila][columna-i].setCasillaVacia();
						tablero[fila][columna-i+1].setChoque(true);

					}else {
						//System.out.println(fila);
						Color color = tablero[fila][columna-i].getColor();
						Color flecha = tablero[fila][columna-i].getFlecha();
						tablero[fila][columna-i+1].setIdTren(movimiento, color, flecha);
						tablero[fila][columna-i+1].setDireccion("D");
						tablero[fila][columna-i].setCasillaVacia();
						if(i==0){
							String nuevaPosicion = direccion + " " + nVag + " " +(columna+1)+" " + (filas-1-fila);
							total.set(movimiento, nuevaPosicion);
						}
					}
				}else {
					tablero[fila][columnas-1].setCasillaVacia();
				}

			}
			panel.repaint();
		}
	}
}


private void flechaIzquierda() {
	String datos = total.get(movimiento);
	String direccion = datos.split(" ")[0];
	if(direccion.equals("I")) {
		int nVag = Integer.parseInt(datos.split(" ")[1]);
		int fila = filas-1 - Integer.parseInt(datos.split(" ")[3]);
		int columna = Integer.parseInt(datos.split(" ")[2]);


		for (int i = 0; i < nVag; i++) {

			if(tablero[fila][columna+i].getIdTren()==movimiento && tablero[fila][columna+i].choque()==false) {
				if(columna+i-1>=0) {
					if(tablero[fila][columna+i-1].getIdTren()!=-1) {
						tablero[fila][columna+i-1].setDireccion("C");
						tablero[fila][columna].setCasillaVacia();
						tablero[fila][columna+i-1].setChoque(true);

					}else {
						Color color = tablero[fila][columna+i].getColor();
						Color flecha = tablero[fila][columna+i].getFlecha();
						tablero[fila][columna+i-1].setIdTren(movimiento, color, flecha);
						tablero[fila][columna+i-1].setDireccion("I");
						tablero[fila][columna+i].setCasillaVacia();
						if(i==0){
							String nuevaPosicion = direccion + " " + nVag + " " +(columna-1)+" " + (filas-1-fila);
							total.set(movimiento, nuevaPosicion);
						}
					}
				}else {
					tablero[fila][0].setCasillaVacia();
				}

			}
			panel.repaint();
		}
	}
}
	
}