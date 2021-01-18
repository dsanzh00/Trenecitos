package progra2.trenecitos;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.Scanner;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

public class Completo {
	/*
	 * Comentario de prueba para probar gitHub
	 */
	JFrame frame;
	JPanel panel;
	
	Infor obj = null;
	PintaTablero tablero;
	
	JMenuBar menu;
	JMenu nuevo;
	JMenuItem objsMenu;
	JMenu archivo;
	JMenuItem crear;
	JMenuItem abrir;
	JMenuItem salir;
	
	int fila;
	int columna;
	
	public Completo() {
		frame = new JFrame();
		frame.setVisible(true);
		frame.setBounds(500, 500, 600, 600);
		frame.setLocationRelativeTo(null);
		frame.setTitle("Jugando con Trenecitos");
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		menu = new JMenuBar();
		frame.setJMenuBar(menu);

		archivo = new JMenu("Archivo");
		menu.add(archivo);

		crear = new JMenuItem("Crear");
		archivo.add(crear);

		salir = new JMenuItem("Exit");
		salir.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) { // por defecto, buscado en internet para que salga
				frame.dispose();
			}
		});

		abrir = new JMenuItem("Abrir");
		archivo.add(abrir);
		archivo.add(salir);
		
		
		panel = new JPanel();
		frame.setContentPane(panel);
		panel.setLayout(null);

		panel.repaint();
		//asociaListeners();
	}


	public static void main(String[] args) {
		@SuppressWarnings("unused")
		Completo completo = new Completo();
	}


	public void imprimeTablero() {
		tablero = new PintaTablero(fila, columna);
		tablero.setVisible(true);
	}

	
	@SuppressWarnings("unused")
	private void asociaListeners() {
		crear.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				if(obj == null) {	
					obj = new Infor();
					obj.setVisible(true);

					obj.getBotonAceptar().addActionListener(new ActionListener() {

						@Override
						public void actionPerformed(ActionEvent e) {
							fila = obj.getFilas();
							columna = obj.getColumnas();
							obj.dispose();
							imprimeTablero();
						}
					});

					obj.addWindowListener(new WindowListener() {

						@Override
						public void windowOpened(WindowEvent e) {}

						@Override
						public void windowIconified(WindowEvent e) {}

						@Override
						public void windowDeiconified(WindowEvent e) {}

						@Override
						public void windowDeactivated(WindowEvent e) {}

						@Override
						public void windowClosing(WindowEvent e) {}

						@Override
						public void windowClosed(WindowEvent e) {
							obj=null;
						}

						@Override
						public void windowActivated(WindowEvent e) {}
					});
				}
			}
		});

		abrir.addActionListener(new ActionListener() {	
			public void actionPerformed(ActionEvent e) {
				try {
					abrir();
				}catch(Exception p) {

				}
			}
		});
	}
	
	private void abrir() throws Exception {
		File file = new File("");
		JFileChooser chooser = new JFileChooser();
		if(chooser.showOpenDialog(null)==JFileChooser.APPROVE_OPTION) { //tal cual de internet
			file = chooser.getSelectedFile();
		}
		String path = file.getAbsolutePath();
		
		
		if(path.substring(path.length()-4).equals(".txt")) {
			FileReader detectado = new FileReader(file);
			
			
			Scanner teclado = new Scanner(detectado);
			
			
			do {
				String[] coordenadas = teclado.nextLine().split(" ");
				fila = Integer.parseInt(coordenadas[0]);
				columna = Integer.parseInt(coordenadas[0]);

				if(fila<31 && fila>9 && columna<31 && columna>9) {
					int numTrenes = Integer.parseInt(teclado.nextLine());
					String[] leidos = new String[numTrenes];
					for (int i = 0; i < leidos.length; i++) {
						leidos[i] = teclado.nextLine();
					}

					int numChoques = Integer.parseInt(teclado.nextLine());
					String[][] choques = new String[numChoques][2];
					
					for (int i = 0; i < numChoques; i++) {
						choques[i] = teclado.nextLine().split(" ");
					}

					Trenecitos prac2 = new Trenecitos(leidos, choques, fila, columna);
					imprimeTablero();
					tablero.setInformacion(prac2.getTren());	
				}
			}while(teclado.hasNext());

			detectado.close();
		}
	}
}
