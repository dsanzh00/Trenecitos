package progra2.trenecitos;

import javax.swing.JFrame;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;

class Help extends JFrame{
	static final long serialVersionUID = 1L;
	
	private JScrollPane scrollPanel = new JScrollPane();
	private JTextArea texto;

	
	public Help() {
		setBounds(100, 100, 400, 500);
		getContentPane().setLayout(null);
		setLocationRelativeTo(null);
		scrollPanel.setBounds(20, 20, 560, 760);
		texto = new JTextArea("¿Ayuda?");
		scrollPanel.setViewportView(texto);;
		texto.setEditable(false);
		getContentPane().add(scrollPanel);
	}
}

