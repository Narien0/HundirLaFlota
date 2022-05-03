package HundirLaFlota;

import java.util.ArrayList;

public class GestorJugadores {
	private static GestorJugadores mGestorJugadores;
	private ArrayList<Jugador> lJugadores;
	
	private GestorJugadores() {
		this.lJugadores = new ArrayList<Jugador>();
		this.lJugadores.add(0,new Jugador());
		this.lJugadores.add(1,new IA());
	}
	
	public static GestorJugadores getGestorJugadores() {
		if(mGestorJugadores == null) {
			mGestorJugadores = new GestorJugadores();
		}
		return mGestorJugadores;
	}
	
	public boolean actuarUnoContraOtro(int pJugAct,int pJugObj, Accion pAccion, int posX, int posY) {
		System.out.print("accion: "+pAccion+"   ");
		if(pAccion instanceof Seleccion)System.out.println("Actua "+pJugAct+" contra "+pJugObj);
		boolean accionEjecutadaCorrectamente=false;
		Jugador jact = this.lJugadores.get(pJugAct);
		if(jact instanceof IA) {
			accionEjecutadaCorrectamente = true;
			((IA)jact).realizarAccionInteligente();
		}else if(pJugAct>=0&&pJugAct<this.lJugadores.size()&&pJugObj>=0&&pJugObj<this.lJugadores.size()&&pAccion!=null){
			Jugador jobj = this.lJugadores.get(pJugObj);
			accionEjecutadaCorrectamente = jact.consumirRecuro(pAccion);
			jobj.actuarSobre(pAccion, posX, posY);
		}
		return accionEjecutadaCorrectamente;
	}
	
	public void actuarContra(int pJugObj, Accion pAccion, int posX, int posY) {
		if(pJugObj>=0&&pJugObj<this.lJugadores.size()) {
			Jugador jobj = this.lJugadores.get(pJugObj);
			jobj.actuarSobre(pAccion, posX, posY);
		}
	}
	
	public boolean ponerBarcoEn(int pJug, int pX, int pY, int pTam, int pDir){
		boolean puestoCorrectamente = false;
		Jugador jAux = this.lJugadores.get(pJug);
		if(jAux instanceof IA) {
			((IA)jAux).ponerBarcosInteligente();
			puestoCorrectamente = true;
		}else {
			puestoCorrectamente = (jAux.ponerBarco(pX, pY, pTam, pDir)==1||jAux.ponerBarco(pX, pY, pTam, pDir)==2);
		}
		return puestoCorrectamente;
	}
	
	public void generarPosRadar(int pAct ,int pObj) {
		Jugador jaux = this.lJugadores.get(pAct);
		int x = (int)(Math.random()*10);
		int y = (int)(Math.random()*10);
		jaux.setRadar(x, y, pObj);
	}
	
	public void limpiarTableroUsuario(int turn) {
		this.lJugadores.add(turn,new Jugador());
	}
	
	public Coordenada getPorRadarDe(int pJug) {
		return (this.lJugadores.get(pJug).getRadar());
	}
	
	public boolean esTurnoIA(int pos) {
		return (this.lJugadores.get(pos) instanceof IA);
	}

}
