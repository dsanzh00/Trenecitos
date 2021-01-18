package progra2.trenecitos;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JLabel;
import javax.swing.JButton;
import javax.swing.JComboBox;
import java.awt.event.ActionListener;
import java.awt.Window.Type;
import java.awt.event.ActionEvent;
import java.awt.event.ItemListener;
import java.awt.event.ItemEvent;

public class Infor extends JFrame {
	
	static final long serialVersionUID = 1L;
	JPanel panel;
	JButton aceptar;
	int fila = 0 ;
	int columna = 0;
	
	public Infor() {
		setResizable(false);
		setTitle("Tablero nuevo");
		setType(Type.UTILITY);
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setBounds(100, 100, 370, 180);
		panel = new JPanel();
		panel.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(panel);
		panel.setLayout(null);
		
		JLabel opcion = new JLabel("Seleccione filas");
		opcion.setBounds(47, 29, 200, 13);
		panel.add(opcion);
		
		JLabel opcion2 = new JLabel("Seleccione columnas");
		opcion2.setBounds(47, 64, 200, 13);
		panel.add(opcion2);
		
		JComboBox<Integer> box = new JComboBox<>();
		box.addItemListener(new ItemListener() {
			public void itemStateChanged(ItemEvent e) {
				fila = (int)box.getSelectedItem();
			}
		});
		
		box.setBounds(257, 25, 59, 21);
		for(int i=10; i<=30; i++) {
			box.addItem(i);
		}
		panel.add(box);
		
		JComboBox<Integer> box2 = new JComboBox<>();
		box2.addItemListener(new ItemListener() {
			public void itemStateChanged(ItemEvent e) {
				columna = (int) box2.getSelectedItem();
			}
		});
		box2.setBounds(257, 60, 59, 21);
		for(int i=10; i<=30; i++) {
			box2.addItem(i);
		}
		panel.add(box2);
		
		aceptar = new JButton("Aceptar");
		aceptar.setBounds(47, 101, 85, 21);
		panel.add(aceptar);
		
		JButton cancelar = new JButton("Cancelar");
		cancelar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				dispose();
			}
		});
		cancelar.setBounds(231, 101, 85, 21);
		panel.add(cancelar);
	}
	
	public JButton getBotonAceptar() {
		return aceptar;
	}
	
	public int getFilas() {
		return fila;
	}
	
	public int getColumnas() {
		return columna;
	}
}
