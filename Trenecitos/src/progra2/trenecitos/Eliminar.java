package progra2.trenecitos;
import java.util.ArrayList;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

public class Eliminar extends JFrame {
	
	static final long serialVersionUID = 1L;	
	JComboBox<Integer> box;
	JButton elimina;
	
	public Eliminar(ArrayList<Integer> trenes) {
		setResizable(false);
		setTitle("Eliminar");
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setBounds(100, 100, 185, 186);
		JPanel panel = new JPanel();
		panel.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(panel);
		panel.setLayout(null);
		
		box = new JComboBox<Integer>();	
		for (int i = 0; i < trenes.size(); i++) {
			box.addItem(trenes.get(i));	
		}
		box.setBounds(10, 32, 149, 24);
		panel.add(box);				

		JLabel selecciona = new JLabel("Selecciona tren a eliminar");
		selecciona.setBounds(10, 10, 149, 13);
		panel.add(selecciona);	

		elimina = new JButton("Eliminar");
		elimina.setBounds(10, 76, 149, 60);
		panel.add(elimina);
	}
	
	public int getTrenEliminar() {
		return (int) box.getSelectedItem();
	}
	
	public JButton getBoton() {
		return elimina;
	}

}
