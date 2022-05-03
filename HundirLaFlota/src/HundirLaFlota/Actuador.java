package HundirLaFlota;

import java.util.ArrayList;
import java.util.Observable;
import java.math.*;

public class Actuador extends Observable{
	private static Actuador mActuador;
	
	private ArrayList<Jugador> lJugadores;
	
	private Accion accionAlmacenada;
	private int direccionAlmacenada;
	private int tamanoAlmacenado;
	private int posXAlmacenada;
	private int posYAlmacenada;
	private int posTablero;
	
	private boolean posDeRadarCargada;
	
	private Actuador(){		
		this.resetAlmacenadoDePoner();
		
		addObserver(Vista.getVista());
	}
	
	public static Actuador getActuador(){
		if(mActuador == null){
			mActuador = new Actuador();
		}
		return mActuador;
	}
	
	public void almacenarAccion(int pCodAcc){
		String notificacion="";
		if (pCodAcc==0) {
			this.accionAlmacenada = new Bomba();
			notificacion = "Bomba";
		}else if(pCodAcc==1) {
			this.accionAlmacenada = new Misil();
			notificacion = "Misil";
		}else if(pCodAcc==2) {
			this.accionAlmacenada = new Escudo();
			notificacion = "Escudo";
		}else if(pCodAcc==3) {
			this.accionAlmacenada = new ConsultaRadar();
			notificacion = "Radar";
		}else if(pCodAcc==-1) {
			this.accionAlmacenada = new Seleccion();
		}
			setChanged();
			notifyObservers(notificacion);
	}
	
	public void almacenarTamBarco(int pCodTam){
		this.tamanoAlmacenado=pCodTam;
		
		String cod;
		if(this.tamanoAlmacenado==1)cod="Fragata";
		else if(this.tamanoAlmacenado==2)cod="Destructor";
		else if(this.tamanoAlmacenado==3)cod="Submarino";
		else cod="Portaviones";
		setChanged();
		notifyObservers(cod);
	}
	
	public void almacenarDireccion(int pCodDir){
		this.direccionAlmacenada=pCodDir;
	}
	
	public void almacenarPos(int x, int y, int tab){
		this.posXAlmacenada = x;
		this.posYAlmacenada = y;
		this.posTablero = tab;
	}
	
	public void seleccionarPos(int posJug) {
		if(/*!(jaux instanceof IA) &&*/ this.posXAlmacenada >= 0 && this.posYAlmacenada >= 0) {
			GestorJugadores.getGestorJugadores().actuarContra(posJug, new Seleccion(), posXAlmacenada, posYAlmacenada);
		}
	}
	
	public void generarPosRadar(int pAct ,int pObj) {
		GestorJugadores.getGestorJugadores().generarPosRadar(pAct, pObj);
	}
	
	public boolean obtenerPosRadarAlmacenada(int pAct) {
		Coordenada rAux = GestorJugadores.getGestorJugadores().getPorRadarDe(pAct);
		boolean res = rAux != null;
		if(res) {
			this.posXAlmacenada = rAux.getX();
			this.posYAlmacenada = rAux.getY();
			this.posTablero = rAux.getTab();
		}
		return res;
	}
	
	public int ponerBarco(int posJug){
		boolean puestoCorrectamente = false;
		int codPoner = 0;
		
		if (this.almacenNecesarioPoner()){
			puestoCorrectamente = GestorJugadores.getGestorJugadores().ponerBarcoEn(posJug, posXAlmacenada, posYAlmacenada, tamanoAlmacenado, direccionAlmacenada);
			if (puestoCorrectamente) {
				this.resetAlmacenadoDePoner();
				setChanged();
				notifyObservers("");
			}
		}
		return codPoner;
		
	}
	
	public int actuar(int posJug){
		int res = 0;
		boolean accionRealizadaCorrectamente = false;
		boolean recursosNecesarios = true;
		boolean accionNoFinTurno = this.accionSobreSiMismo() || this.accionAlmacenada instanceof ConsultaRadar;
		if(this.accionAlmacenada instanceof ConsultaRadar) {
			recursosNecesarios =  this.obtenerPosRadarAlmacenada(posJug);
		}
		int posObj = this.posTablero;
		if(this.accionSobreSiMismo()) posObj = posJug;
		recursosNecesarios = recursosNecesarios && (this.almacenNecesarioAccion() && 0 <= posObj);
		if(recursosNecesarios || GestorJugadores.getGestorJugadores().esTurnoIA(posJug)) {
			accionRealizadaCorrectamente = GestorJugadores.getGestorJugadores().actuarUnoContraOtro(posJug, posObj, accionAlmacenada, posXAlmacenada, posYAlmacenada);
		}
		
		if(recursosNecesarios) {
			this.resetAlmacenadoDeActuar();
		}
		
		return res;
	}
	
	public void actuarContra(int posOp) {
		GestorJugadores.getGestorJugadores().actuarContra(posOp, accionAlmacenada, posXAlmacenada, posYAlmacenada);
	}
	
	private boolean accionSobreSiMismo() {
		return(this.accionAlmacenada instanceof Seleccion || this.accionAlmacenada instanceof Escudo);
	}
	
	public boolean tableroApropiado(int turnoActuando,int estadoAct){
		boolean res;
		if((this.accionSobreSiMismo()&& this.posTablero == turnoActuando)||(!this.accionSobreSiMismo()&&this.posTablero!= turnoActuando)) {
			//Caso en el qué si es el tablero apropiado
			res = true;
		}else {
			res = false;
		}
		return res;
	}
	
	private void resetAlmacenadoDePoner(){
		this.accionAlmacenada=new Seleccion();
		this.direccionAlmacenada=-1;
		this.tamanoAlmacenado=-1;
		this.posXAlmacenada=-1;
		this.posYAlmacenada=-1;
		this.posTablero = -1;
		this.posDeRadarCargada = false;
	}
	
	private void resetAlmacenadoDeActuar() {
		this.resetAlmacenadoDePoner();
		this.accionAlmacenada = null;
	}
	
	private boolean almacenNecesarioAccion(){
		boolean res = true;
		res = res && this.accionAlmacenada!=null;
		res = res && this.posXAlmacenada!=-1;
		res = res && this.posYAlmacenada!=-1;
		res = res && this.posTablero != -1;
		return res;
	}
	
	private boolean almacenNecesarioPoner(){
		boolean res = true;
		res = res && this.direccionAlmacenada!=-1;
		res = res && this.tamanoAlmacenado!=-1;
		res = res && this.posXAlmacenada!=-1;
		res = res && this.posYAlmacenada!=-1;
		return res;
	}
	
	public void resetCoordsAcc() {
		this.posXAlmacenada=-1;
		this.posYAlmacenada=-1;
		this.accionAlmacenada = null;
	}

}
