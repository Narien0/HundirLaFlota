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
		int turnoPreAccion = this.turno;
		
		if(estado==0) {
			if(!this.esTurnoIA()) {
				Actuador.getActuador().seleccionarPos(turno);
			}
			Actuador.getActuador().ponerBarco(turno);
		}
		else if(estado==1) Actuador.getActuador().actuar(turno);
		
		if(this.esTurnoIA()&&turnoPreAccion==turno) {
			this.actuar();
		}
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
		
//		System.out.println("\n###Cambio de turno\n\n");
		
	}
	
	public void cambioEstado(){
//		System.out.println("\n###Cambio de estado\n");
		this.estado++;
		setChanged();
		notifyObservers(this.estado);
	}
	
	public void tableroApropiado(int c){
		Actuador.getActuador().tableroApropiado(turno,estado);
	}
	
	public void limpiarTableroTurno() {
		Actuador.getActuador().limpiarTableroUsuario(turno);
	}
	
	private boolean esTurnoIA() {
		return this.turnosIAs.get(turno);
	}

	public void ponerRadarEnTablero(int pObjetivo) {
		Actuador.getActuador().generarPosRadar(turno, pObjetivo);	
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
