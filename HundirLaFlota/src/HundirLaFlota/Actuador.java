package HundirLaFlota;

import java.util.ArrayList;
import java.util.Observable;

public class Actuador extends Observable{
	private static Actuador mActuador;
	
	private ArrayList<Jugador> lJugadores;
	
	private Accion accionAlmacenada;
	private int direccionAlmacenada;
	private int tamanoAlmacenado;
	private int posXAlmacenada;
	private int posYAlmacenada;
	private int posTablero;
	
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
//		System.out.println(this.posXAlmacenada + ""+ this.posYAlmacenada + posJug);
		Jugador jaux = this.lJugadores.get(posJug);
		if(!(jaux instanceof IA) && this.posXAlmacenada >= 0 && this.posYAlmacenada >= 0) jaux.actuarSobre(new Seleccion(), this.posXAlmacenada, this.posYAlmacenada);
	}
	
	public void ponerBarco(int posJug){
		boolean puestoCorrectamente = false;
		if (this.almacenNecesarioPoner()){
			Jugador jaux = this.lJugadores.get(posJug);
			if(jaux instanceof IA) {
				((IA) jaux).ponerBarcosInteligente();
			}
			else puestoCorrectamente = jaux.ponerBarco(this.posXAlmacenada, this.posYAlmacenada, this.tamanoAlmacenado, this.direccionAlmacenada);
			if (puestoCorrectamente) this.resetAlmacenado();
		}
	}
	
	public boolean actuar(int posJug){
		int posObj = this.posTablero;
		if(this.accionSobreSiMismo()) posObj = posJug;
		boolean res = false;
		res = this.almacenNecesarioAccion();
		Jugador jaux = this.lJugadores.get(posJug);
		System.out.println("yy"+jaux);
		if(jaux instanceof IA) {
			res = true;
			((IA) jaux).realizarAccionInteligente();
		}
		else if(res) {
			if(jaux.consumirRecuro(accionAlmacenada)){
				this.actuarContra(posObj);
			}
		}
		
		if(res) {
			this.resetAlmacenado();
		}
		return res;
	}
	
	public void actuarContra(int posOp) {
		Jugador jOponente = this.lJugadores.get(posOp);
		jOponente.actuarSobre(this.accionAlmacenada, this.posXAlmacenada, this.posYAlmacenada);
	}
	
	private boolean accionSobreSiMismo() {
		return(this.accionAlmacenada instanceof Seleccion /*|| this.accionAlmacenada instanceof Escudo*/);
	}
	
	public void tableroApropiado(int c){
		if((this.accionSobreSiMismo()&& this.posTablero == c)||(!this.accionSobreSiMismo()&&this.posTablero!= c)) {
			//Caso en el qué si es el tablero apropiado
		}else {
			this.resetAlmacenado();
		}
	}
	
	private void resetAlmacenado(){
		this.accionAlmacenada=new Seleccion();
		this.direccionAlmacenada=-1;
		this.tamanoAlmacenado=-1;
		this.posXAlmacenada=-1;
		this.posYAlmacenada=-1;
		this.posTablero = -1;
	}
	
	private boolean almacenNecesarioAccion(){
		boolean res = true;
		res = res && this.accionAlmacenada!=null;
		res = res && this.posXAlmacenada!=-1;
		res = res && this.posYAlmacenada!=-1;
		this.posTablero = -1;
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
	
	public void limpiarTableroUsuario(int turn) {
		this.lJugadores.add(turn,new Jugador());
	}

}
