package HundirLaFlota;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.AbstractButton;
import javax.swing.JButton;
import javax.swing.JLabel;

public class Controlador implements ActionListener, MouseListener {

	private static Controlador mControlador;
	
	private Controlador() {
	}
	
	public static Controlador getControlador() {
		if(mControlador==null) {
			mControlador = new Controlador();
		}
		return mControlador;
	}

	///################# Listeners de Accion  ####################
	
	@Override
	public void actionPerformed(ActionEvent e) {
		if(((JButton) e.getSource()).getText().equals("Bomba")) {
			Modelo.getModelo().cargarAccion(0);
		}else if(((JButton) e.getSource()).getText().equals("Misil")) {
			Modelo.getModelo().cargarAccion(1);
		}else if(((JButton) e.getSource()).getText().equals("Portaviones")) {
			Modelo.getModelo().recibirTamano(4);
		}else if(((JButton) e.getSource()).getText().equals("Submarino")) {
			Modelo.getModelo().recibirTamano(3);
		}else if(((JButton) e.getSource()).getText().equals("Destructor")) {
			Modelo.getModelo().recibirTamano(2);
		}else if(((JButton) e.getSource()).getText().equals("Fragata")) {
			Modelo.getModelo().recibirTamano(1);
		}else if(((JButton) e.getSource()).getText().equals("Arriba")) {
			Modelo.getModelo().recibirDir(0);
		}else if(((JButton) e.getSource()).getText().equals("Abajo")) {
			Modelo.getModelo().recibirDir(2);
		}else if(((JButton) e.getSource()).getText().equals("Derecha")) {
			Modelo.getModelo().recibirDir(1);
		}else if(((JButton) e.getSource()).getText().equals("Izquierda")) {
			Modelo.getModelo().recibirDir(3);
		}else if(((JButton) e.getSource()).getText().equals("Limpiar Tablero")) {
			Modelo.getModelo().limpiarTableroUsuario();
		}
	}

	
	///################# Listeners de Mouse  ####################
	@Override
	public void mouseClicked(MouseEvent e) {
		// TODO Auto-generated method stub
		int x = ((JLabel)e.getSource()).getText().charAt(0) - '0';
		int y = ((JLabel)e.getSource()).getText().charAt(1) - '0';
		char tab = ((JLabel)e.getSource()).getText().charAt(2);
		Modelo.getModelo().recibirPos(x, y,tab);
	}

	@Override
	public void mousePressed(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseReleased(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseEntered(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseExited(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

}