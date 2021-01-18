package progra2.trenecitos;
import java.awt.EventQueue;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JTextField;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JComboBox;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JButton;

public class Disenio extends JFrame{
	
	static final long serialVersionUID = 1L;
	JPanel panel;
	JTextField texto;
	JComboBox<String> box;
	JTextField valorH;
	JTextField valorV;
	JButton button;
	int filas;
	int columnas;
	String direccion;
	int numVagones;

	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() { //Tal cual de internet
			public void run() {
				try {
					Disenio frame = new Disenio();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}
	
	public Disenio() {
		setResizable(false);
		setTitle("Disenio");
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setBounds(100, 100, 341, 272);
		panel = new JPanel();
		panel.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(panel);
		panel.setLayout(null);

		texto = new JTextField();
		texto.setBounds(10, 48, 149, 24);
		panel.add(texto);
		texto.setColumns(10);

		JLabel vagones = new JLabel("Cuántos vagones");
		vagones.setBounds(10, 26, 149, 13);
		panel.add(vagones);

		box = new JComboBox<String>();
		box.setModel(new DefaultComboBoxModel<String>(new String[] {"Arriba", "Abajo", "Derecha", "Izquierda"}));
		box.setBounds(10, 118, 149, 24);
		panel.add(box);				

		JLabel direccion = new JLabel("Qué direccion");
		direccion.setBounds(10, 96, 149, 13);
		panel.add(direccion);

		JLabel valorX = new JLabel("ValorX de la cabeza");
		valorX.setBounds(169, 26, 149, 13);
		panel.add(valorX);

		valorH = new JTextField(); //solo edita una linea
		valorH.setColumns(10);
		valorH.setBounds(169, 48, 149, 24);
		panel.add(valorH);

		JLabel valorY = new JLabel("ValorY de la cabeza");
		valorY.setBounds(169, 96, 149, 13);
		panel.add(valorY);

		valorV = new JTextField();
		valorV.setColumns(10);
		valorV.setBounds(169, 118, 149, 24);
		panel.add(valorV);

		button = new JButton("Add");
		button.setBounds(10, 164, 308, 60);
		panel.add(button);
	}
	
	public void setFilas(int filas) {
		this.filas = filas;
	}

	
	public void setColumnas(int columnas) {
		this.columnas = columnas;
	}

	
	public JButton getBoton() {
		return button;
	}

	
	public boolean correcto() {
		direccion = (String) box.getSelectedItem();
		numVagones = getNumeroVagones();
		int valorXCabeza = getValorXCabeza();
		int valorYCabeza = getValorYCabeza();
		boolean valido = true;

		if(valorXCabeza<0 || valorXCabeza>columnas-1) {
			JOptionPane.showMessageDialog(null, "Error, está fuera");
			valido = false;
		}
		if(valorYCabeza<0 || valorYCabeza>filas-1) {
			JOptionPane.showMessageDialog(null, "Error, está fuera");
			valido=false;
		}

		if(valido) {
			switch(direccion) {
			case "Arriba":
				if(numVagones>filas || numVagones<=0) {
					JOptionPane.showMessageDialog(null, "Error");
					valido = false;
				}else if(valorYCabeza>(filas-numVagones)) {
					JOptionPane.showMessageDialog(null, "Error");
					valido = false;
				}
				break;
			case "Derecha":
				if(numVagones>columnas || numVagones<=0) {
					JOptionPane.showMessageDialog(null, "Error");
					valido = false;
				}else if(valorXCabeza<(numVagones-1)) {
					JOptionPane.showMessageDialog(null, "Error");
					valido = false;
				}
				break;
			case "Abajo":
				if(numVagones>filas || numVagones<=0) {
					JOptionPane.showMessageDialog(null, "Error");
					valido = false;
				}else if(valorYCabeza<(numVagones-1)) {
					JOptionPane.showMessageDialog(null, "Error");
					valido = false;
				}
				break;
			case "Izquierda":
				if(numVagones>columnas || numVagones<=0) {
					JOptionPane.showMessageDialog(null, "Error");
					valido = false;
				}else if(valorXCabeza>(columnas-numVagones)) {
					JOptionPane.showMessageDialog(null, "Error");
					valido = false;
				}
				break;
			}
		}
		return valido;
	}

	
	public int getValorXCabeza() {
		try {
			return Integer.valueOf(valorH.getText());
		}catch(Exception e) {
			JOptionPane.showMessageDialog(null, "VALOR X ERRONEO");
		}
		return -1;
	}

	
	public int getValorYCabeza() {
		try {
			return filas - 1 - Integer.valueOf(valorV.getText());
		}catch(Exception e) {
			JOptionPane.showMessageDialog(null, "VALOR Y ERRONEO");
		}
		return -1;
	}

	
	private int getNumeroVagones() {
		try {
			return Integer.valueOf(texto.getText());
		}catch(Exception e) {
			JOptionPane.showMessageDialog(null, "ERROR");
		}
		return 0;
	}
	
	
	public String getDireccion() {
		return direccion;
	}
	
	
	public int getLongTren() {
		return numVagones;
	}
}
