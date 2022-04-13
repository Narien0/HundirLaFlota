package HundirLaFlota;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.Observable;

public class Jugador extends Observable{

	private double dinero;
	protected Collection<Barco>[] lBarcos;
//	protected int cantBomb;
//	protected int cantMisil;
	protected ArrayList<Integer> lArmas;
	protected Panel panel;

	public Jugador() {
		this.dinero = 50;
		this.lArmas = new ArrayList<Integer>();
		this.lArmas.add(0,100); //Bombas
		this.lArmas.add(1,5); //Misiles
		this.lArmas.add(2,2); //Escudos
		this.lArmas.add(2,2); //Radares
		this.panel=new Panel();
		this.lBarcos = new ArrayList[5];
		for (int i = 1; i<5;i++) {
			this.lBarcos[i]= new ArrayList<Barco>();			
		}
		this.addObserver(Vista.getVista());
		this.addObserver(GestorTurno.getGestorTurno());
	}

	/**
	 * 
	 * @param a
	 */
	public boolean consumirRecuro(Accion a) {
		boolean sePuede = false;
		int pos = 14;
		if (a instanceof Bomba) {
			pos = 0;
		}else if (a instanceof Misil) {
			pos = 1;
		}else if (a instanceof Seleccion) {
			pos = -1;
			sePuede = true;
		}
		if(!sePuede) {
			sePuede = (this.lArmas.get(pos)>0);
			this.lArmas.add(pos,this.lArmas.get(pos)-1);
		}
		return sePuede;
	}

	/**
	 * 
	 * @param a
	 * @param x
	 * @param y
	 */
	public void actuarSobre(Accion a, int x, int y) {
		this.panel.accionarTile(x, y, a);
		if(!(a instanceof Seleccion)) { //Se puede usar esto o la linea comentada en actuar de gestor turno
			setChanged();
			notifyObservers(true);
			this.todosHundidos();
		}
	}

	/**
	 * 
	 * @param x
	 * @param y
	 * @param pTam
	 * @param pCodDir
	 */
	public boolean ponerBarco(int x, int y, int pTam, int pCodDir) {
		boolean res;
		if (this.lBarcos[pTam].size()<5-pTam && panel.sePuedePoner(x,y,pTam,pCodDir)) { 
			res = true;
			int i = 0;
			Barco b = new Barco(pTam);
			this.anadirBarco(b, pTam);
			notifyObservers(pTam);
			if(pCodDir == 0){
				while (i < pTam){
					TBarco tb =  new TBarco(x,y-i,false);
					b.anadirTBarco(tb);
					this.ponerTBPanel(x,y-i,tb);
					i++;
				}
			}
			else if(pCodDir == 1){
				while (i < pTam){
					TBarco tb =  new TBarco(x+i,y,false);
					b.anadirTBarco(tb);
					this.ponerTBPanel(x+i,y,tb);
					i++;
				}
			}
			else if(pCodDir == 2){
				while (i < pTam){
					TBarco tb =  new TBarco(x,y+i,false);
					b.anadirTBarco(tb);
					this.ponerTBPanel(x,y+i,tb);
					i++;
				}
			}
			else{
				while (i < pTam){
					TBarco tb =  new TBarco(x-i,y,false);
					b.anadirTBarco(tb);
					this.ponerTBPanel(x-i,y,tb);
					i++;
				}
			}
			this.comprobarFinAnadirBarcos();
			this.rodearBarco(x, y, pTam, pCodDir);
		}
		
		else{
			res = false;
			//hacer una excepcion oh algo para comunicar al jugador, oh hacer el checkeo de manera previa
		}
		return res;
	}

	/**
	 * 
	 * @param pB
	 */
	public void anadirBarco(Barco pB,int pTam) {
		lBarcos[pTam].add(pB);
		setChanged();
		notifyObservers(pTam);
	}
	
	public void comprobarFinAnadirBarcos() { //Comprueba si se ha añadido el máximo de cada tipo de barco y si es así cambia el turno
		boolean lleno = true;
		for(int i = 1; i < this.lBarcos.length; i++) {
			lleno = lleno && (this.lBarcos[i].size() == 5-i);
		}
		if(lleno) {
			setChanged();
			notifyObservers(true);
		}
	}
	
	private void rodearBarco(int x, int y, int pTam, int pCodDir) {//Rodear barco de agua ocupada
		this.panel.rodearBarco(x, y, pTam, pCodDir);
	}
	
	public boolean todosHundidos() {
		boolean b = true;
		Barco aux;
		Iterator<Barco> itr;
		for(int i=1; i<this.lBarcos.length;i++) {
			itr = this.lBarcos[i].iterator();
			while(itr.hasNext()) {
				aux = itr.next();
				b = b && aux.estaHundido();
			}
		}
		if (b) {
			setChanged();
			notifyObservers(false);
		}
		return b;
	}
	
	protected void ponerTBPanel(int x, int y, TBarco tb) {
		this.panel.ponerTileEnPos(x, y, tb);
		tb.revelar();
	}

}