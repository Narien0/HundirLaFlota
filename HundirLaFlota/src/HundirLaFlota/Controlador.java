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
			Actuador.getActuador().almacenarAccion(0);
			GestorTurno.getGestorTurno().actuar();
		}else if(((JButton) e.getSource()).getText().equals("Misil")) {
			Actuador.getActuador().almacenarAccion(1);
			GestorTurno.getGestorTurno().actuar();
		}else if(((JButton) e.getSource()).getText().equals("Escudo")) {
			Actuador.getActuador().almacenarAccion(2);
			GestorTurno.getGestorTurno().actuar();
		}else if(((JButton) e.getSource()).getText().equals("Radar")) {
			Actuador.getActuador().almacenarAccion(3);
			GestorTurno.getGestorTurno().actuar();
		}else if(((JButton) e.getSource()).getText().equals("Poner Radar")) {
			GestorTurno.getGestorTurno().ponerRadarEnTablero(1);
			Actuador.getActuador().obtenerPosRadarAlmacenada(0);
			Actuador.getActuador().seleccionarPos(1);
			Actuador.getActuador().resetCoordsAcc();
		}
		else if(((JButton) e.getSource()).getText().equals("Portaviones")) {
			Actuador.getActuador().almacenarTamBarco(4);
			GestorTurno.getGestorTurno().actuar();
			//Modelo.getModelo().recibirTamano(4);
		}else if(((JButton) e.getSource()).getText().equals("Submarino")) {
			Actuador.getActuador().almacenarTamBarco(3);
			GestorTurno.getGestorTurno().actuar();
			//Modelo.getModelo().recibirTamano(3);
		}else if(((JButton) e.getSource()).getText().equals("Destructor")) {
			Actuador.getActuador().almacenarTamBarco(2);
			GestorTurno.getGestorTurno().actuar();
			//Modelo.getModelo().recibirTamano(2);
		}else if(((JButton) e.getSource()).getText().equals("Fragata")) {
			Actuador.getActuador().almacenarTamBarco(1);
			GestorTurno.getGestorTurno().actuar();
			//Modelo.getModelo().recibirTamano(1);
		}else if(((JButton) e.getSource()).getText().equals("Arriba")) {
			Actuador.getActuador().almacenarDireccion(0);
			GestorTurno.getGestorTurno().actuar();
			//Modelo.getModelo().recibirDir(0);
		}else if(((JButton) e.getSource()).getText().equals("Abajo")) {
			Actuador.getActuador().almacenarDireccion(2);
			GestorTurno.getGestorTurno().actuar();
			//Modelo.getModelo().recibirDir(2);
		}else if(((JButton) e.getSource()).getText().equals("Derecha")) {
			Actuador.getActuador().almacenarDireccion(1);
			GestorTurno.getGestorTurno().actuar();
			//Modelo.getModelo().recibirDir(1);
		}else if(((JButton) e.getSource()).getText().equals("Izquierda")) {
			Actuador.getActuador().almacenarDireccion(3);
			GestorTurno.getGestorTurno().actuar();
			//Modelo.getModelo().recibirDir(3);
		}else if(((JButton) e.getSource()).getText().equals("Limpiar Tablero")) {
			GestorTurno.getGestorTurno().limpiarTableroTurno();
			//Modelo.getModelo().limpiarTableroUsuario();
		}
	}

	
	///################# Listeners de Mouse  ####################
	@Override
	public void mouseClicked(MouseEvent e) {
		// TODO Auto-generated method stub
		int x = ((JLabel)e.getSource()).getText().charAt(0) - '0';
		int y = ((JLabel)e.getSource()).getText().charAt(1) - '0';
		int tab = ((JLabel)e.getSource()).getText().charAt(2) - '0';
		Actuador.getActuador().almacenarPos(x, y, tab);
		GestorTurno.getGestorTurno().tableroApropiado(tab);
		GestorTurno.getGestorTurno().actuar();
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
