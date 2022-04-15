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
		this.lJugadores = new ArrayList<Jugador>();
		this.lJugadores.add(0,new Jugador());
		this.lJugadores.add(1,new IA());
		
		this.resetAlmacenado();
		
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
		}else if(pCodAcc==3) {
			this.accionAlmacenada = new Radar();
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
//		this.posDeRadarCargada = false;
	}
	
	public void seleccionarPos(int posJug) {
		Jugador jaux = this.lJugadores.get(posJug);
//		System.out.println("Preselec  "+this.posXAlmacenada+"    "+this.posYAlmacenada);
		if(/*!(jaux instanceof IA) &&*/ this.posXAlmacenada >= 0 && this.posYAlmacenada >= 0) {
//			System.out.println("Postselec");
			jaux.actuarSobre(new Seleccion(), this.posXAlmacenada, this.posYAlmacenada);
		}
	}
	
	public void generarPosRadar(int pAct ,int pObj) {
		Jugador jaux = this.lJugadores.get(pAct);
		this.posTablero = pObj;
		int x = (int)(Math.random()*10);
		int y = (int)(Math.random()*10);
		jaux.setRadX(x);
		jaux.setRadY(y);
		jaux.setRadTab(pObj);
//		System.out.print(pAct);
//		System.out.println(" generarPosRadar   "+jaux.getRadX()+"  "+ jaux.getRadY());
	}
	
	public void obtenerPosRadarAlmacenada(int pAct) {
		Jugador jaux = this.lJugadores.get(pAct);
//		if(jaux.getRadX()==-1) {
//			this.generarPosRadar(posJ);
//		}
		this.posXAlmacenada = jaux.getRadX();
		this.posYAlmacenada = jaux.getRadY();
		this.posTablero = jaux.getRadTab();
		
//		System.out.print(pAct);
//		System.out.println(" obtenerPosRadarAlmacenado    "+jaux.getRadX()+"    "+jaux.getRadY()+"        "+jaux.getRadTab());
	}
	
	public void ponerBarco(int posJug){
		boolean puestoCorrectamente = false;
//		System.out.println("iiiiiEEE    "+this.posXAlmacenada + "     "+this.posYAlmacenada+ "   "+ posJug);
		if (this.almacenNecesarioPoner()){
			Jugador jaux = this.lJugadores.get(posJug);
			if(jaux instanceof IA) {
				((IA) jaux).ponerBarcosInteligente();
			}
			else puestoCorrectamente = jaux.ponerBarco(this.posXAlmacenada, this.posYAlmacenada, this.tamanoAlmacenado, this.direccionAlmacenada);
			if (puestoCorrectamente)
				this.resetAlmacenado();
				setChanged();
				notifyObservers("");
		}
		
	}
	
	public boolean actuar(int posJug){
		boolean res = false;
//		if(this.accionAlmacenada instanceof Radar && !this.posDeRadarCargada) this.obtenerPosRadarAlmacenada(posJug);
//		else if(!(this.accionAlmacenada instanceof Radar) && this.posDeRadarCargada) this.resetAlmacenado();
		if(this.accionAlmacenada instanceof Radar) {
//			System.out.println(". O .");
			this.obtenerPosRadarAlmacenada(posJug);
		}
		int posObj = this.posTablero;
//		System.out.println("\n actuar "+posJug+"   objetivo: "+posObj);
		if(this.accionSobreSiMismo()) posObj = posJug;
		res = this.almacenNecesarioAccion() && 0 < posObj && posObj < this.lJugadores.size();
//		System.out.println(res);
		Jugador jaux = this.lJugadores.get(posJug);
		if(jaux instanceof IA) {
			res = true;
			((IA) jaux).realizarAccionInteligente();
		}
		else if(res) {
			if(jaux.consumirRecuro(accionAlmacenada)){
				this.actuarContra(posObj);
			}
		}
		
		Accion aux = this.accionAlmacenada; //Probando cosas
		
//		if(res) {
//			this.resetAlmacenado();
//		}
		
		return res;
	}
	
	public void actuarContra(int posOp) {
		Jugador jOponente = this.lJugadores.get(posOp);
		jOponente.actuarSobre(this.accionAlmacenada, this.posXAlmacenada, this.posYAlmacenada);
		this.resetAlmacenado();
		setChanged();
		notifyObservers("");
	}
	
	private boolean accionSobreSiMismo() {
		return(this.accionAlmacenada instanceof Seleccion /*|| this.accionAlmacenada instanceof Escudo*/);
	}
	
	public boolean tableroApropiado(int c){
		boolean res;
		if((this.accionSobreSiMismo()&& this.posTablero == c)||(!this.accionSobreSiMismo()&&this.posTablero!= c)) {
			//Caso en el qué si es el tablero apropiado
			res = true;
		}else {
			this.resetAlmacenado();
			res = false;
		}
		return res;
	}
	
	private void resetAlmacenado(){
		this.accionAlmacenada=new Seleccion();
		this.direccionAlmacenada=-1;
		this.tamanoAlmacenado=-1;
		this.posXAlmacenada=-1;
		this.posYAlmacenada=-1;
		this.posTablero = -1;
		this.posDeRadarCargada = false;
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
	
	public void resetCoords() {
		this.posXAlmacenada=-1;
		this.posYAlmacenada=-1;
	}
	
	public void limpiarTableroUsuario(int turn) {
		this.lJugadores.add(turn,new Jugador());
	}

}
