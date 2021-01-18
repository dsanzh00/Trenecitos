package progra2.trenecitos;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;

import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;
import javax.swing.border.LineBorder;

public class FormaTren extends JPanel{

	static final long serialVersionUID = 1L;
	int idTren = 0;
	String direccion;
	int valorX = 0;
	int valorY = 0;
	boolean choque = false;
	final String[] flechas;
	JLabel tren = new JLabel();
	Color color = new Color(120, 120, 120); 
	Color flecha;
	
	public FormaTren() {
		setBorder(new LineBorder(new Color(0, 0, 0)));
		flechas = new String[5];
		flechas[0] = "0";
		flechas[1] = "1";
		flechas[2] = "2";
		flechas[3] = "3";
		flechas[4] = "4 ";
		setLayout(new BorderLayout(0, 0));
		tren.setHorizontalAlignment(SwingConstants.CENTER);
		tren.setFont(new Font("Arial", Font.PLAIN, 18));
		add(tren);
		setBounds(50+20*valorX, 50+20*valorY, 20, 20);
		tren.setForeground(new Color(120, 120, 120));
	}
	
	public int getIdTren() {
		return idTren;
	}
	
	public void setIdTren(int idTren, Color color, Color flecha) {
		this.color = color;
		this.flecha = flecha;
		this.idTren = idTren;
		setBackground(color);
	}
	
	
	public String getDireccion() {
		return direccion;
	}

	
	public void setDireccion(String direccion) {
		this.direccion = direccion;
		switch(direccion) {
		case "A":
			tren.setText(flechas[0]);
			break;
		case "D":
			tren.setText(flechas[1]);
			break;
		case "B":
			tren.setText(flechas[2]);
			break;
		case "I":
			tren.setText(flechas[3]);
			break;
		case "C":
			tren.setText(flechas[4]);
			break;
		case "":
			tren.setText("");
			break;
		}
	}

	
	public int getValorX() {
		return valorX;
	}

	public void setValorX(int valorX) {
		this.valorX = valorX;
		setBounds(50+20*valorX, 50+20*valorY, 20, 20);
	}

	
	public int getValorY() {
		return valorY;
	}

	
	public void setValorY(int valorY) {
		this.valorY = valorY;
		setBounds(50+20*valorX, 50+20*valorY, 20, 20);
	}

	
	public boolean choque() {
		return choque;
	}

	
	public void setChoque(boolean choque) {
		setBackground(new Color(0,0,0));
		this.choque = choque;
	}

	
	public void setCasillaVacia() {
		tren.setText(" ");
		setBackground(new Color(120, 120, 120));
		setIdTren(-1, new Color(120, 120, 120), new Color(0, 0, 0));
		repaint();
	}

	
	public Color getColor() {
		return color;
	}

	
	public Color getFlecha() {
		return flecha;
	}

	
	public String getTexto() {
		return tren.getText();
	}

	
	public void setFormaTren(FormaTren new1) {
		color = new1.getColor();
		flecha = new Color(120, 120, 120);
		setBackground(color);
		setForeground(new Color(120, 120, 120));
		tren.setText(new1.getTexto());
		idTren = new1.getIdTren();
		direccion = new1.getDireccion();
		choque = new1.choque();
	}
}
