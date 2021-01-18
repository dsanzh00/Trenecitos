package progra2.trenecitos;

import javax.swing.JFrame;
import javax.swing.JLabel;

public class Historial extends JFrame{

	FormaTren prueba;
	JLabel historial;
	
	public void PruebaLeyenda(FormaTren prueba, String id) {
		historial = new JLabel(id);
		this.prueba = prueba;
	}
}
