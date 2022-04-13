package HundirLaFlota;

import java.util.ArrayList;
import java.util.Observable;
import java.util.Observer;

public class GestorTurno extends Observable implements Observer{
	private static GestorTurno mGestor;
	private boolean accionNormal;
	private int turno;
	private int estado;
	
	private int numJug; //Un poco por escalabilidad máxima
	private ArrayList<Boolean> turnosIAs;
	
	private GestorTurno(){
		this.turno = 0;
		this.estado = 0;
		this.accionNormal = false;
		
		addObserver(Vista.getVista());
		
		this.numJug = 2;
		this.turnosIAs = new ArrayList<Boolean>();
		this.turnosIAs.add(0,false);
		this.turnosIAs.add(1,true);
	}
	
	public static GestorTurno getGestorTurno(){
		if(mGestor == null){
			mGestor = new GestorTurno();
		}
		return mGestor;
	}
	
	public void actuar(){
		boolean accionExitosa = false;
		if(estado==0) {
			Actuador.getActuador().seleccionarPos(turno);
			Actuador.getActuador().ponerBarco(turno);
		}
		else if(estado==1) accionExitosa = Actuador.getActuador().actuar(turno);
		
//		if(accionExitosa)this.cambioTurno();
	}
	
	public void cambioTurno(){
		this.turno++;
		if(!(turno<numJug)) {
			turno = 0;
		}
		boolean param = (this.turno==0);
		setChanged();
		notifyObservers(param);
		
		if(this.esTurnoIA()){
			this.actuar();
		}
		
	}
	
	public void cambioEstado(){
		this.estado++;
		setChanged();
		notifyObservers(this.estado);
	}
	
	public void tableroApropiado(int c){
		Actuador.getActuador().tableroApropiado(turno);
	}
	
	public void limpiarTableroTurno() {
		Actuador.getActuador().limpiarTableroUsuario(turno);
	}
	
	private boolean esTurnoIA() {
		return this.turnosIAs.get(turno);
	}

	
	
	@Override
	public void update(Observable o, Object arg) {
		if (o instanceof Jugador) {
			if(arg instanceof Boolean) {
				if ((Boolean)arg == true) {
					this.cambioTurno();
				}else {
					this.cambioEstado();
				}
			}
		}
	}
}
