package progra2.trenecitos;

import javax.swing.JLabel;

class EntLetrero {
	
	public FormaTren imprime;
	public JLabel nombre;

	
	public EntLetrero(FormaTren imprime, String id) {
		nombre = new JLabel(id);
		this.imprime = imprime;
	}
}
