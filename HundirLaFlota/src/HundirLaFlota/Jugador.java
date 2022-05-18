package HundirLaFlota;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Observable;

public class Jugador extends Observable{

	private double dinero;
	protected ArrayList<Barco>[] lBarcos;
	protected Map<String, Integer> lArmas;
	protected Panel panel;
	protected Coordenada radar;

	public Jugador() {
		this.dinero = 50;
		this.lArmas = new HashMap<>();
		this.lArmas.put("Bomba", 100); //Bombas
		this.lArmas.put("Misil", 6); //Misiles
		this.lArmas.put("Escudo", 4); //Escudos (en 0 hasta que se implementen)
		this.lArmas.put("ConsultaR", 5); //Consultas de Radares
		this.lArmas.put("MoverR", 0); //Movimientos de radar
		this.lArmas.put("Reparar", 1);
		this.panel=new Panel();
		this.lBarcos = new ArrayList[5];
		for (int i = 1; i<5;i++) {
			this.lBarcos[i]= new ArrayList<Barco>();			
		}
		
		this.radar = null;
		this.addObserver(Vista.getVista());
		this.addObserver(GestorTurno.getGestorTurno());
		
		setChanged();
		notifyObservers("100B");
		setChanged();
		notifyObservers("6M");
		setChanged();
		notifyObservers("4E");
		setChanged();
		notifyObservers("5C");
		setChanged();
		notifyObservers("1R");
		setChanged();
		notifyObservers("50.0D");
	}

	/**
	 * 
	 * @param a
	 */
	public boolean consumirRecuro(Accion a) {
		boolean sePuede = false;
		String pos = "Seleccion";
		int cantidad;
		char codNotif = 'A';
		if (a instanceof Bomba) {
			pos = "Bomba";
			codNotif = 'B';
		} else if (a instanceof Misil) {
			pos = "Misil";
			codNotif = 'M';
		} else if (a instanceof Escudo) {
			pos = "Escudo";
			codNotif = 'E';
		} else if (a instanceof ConsultaRadar) {
			pos = "ConsultaR";
			codNotif = 'C';
		} else if (a instanceof Reparar) {
			pos = "Reparar";
			codNotif = 'R';
			//}else if(a instanceof MoverRadar){pos = "MoverR"}
		} else if (a instanceof Seleccion) {
			pos = "Seleccion";
			sePuede = true;
		}
		if (!sePuede) sePuede = (this.lArmas.get(pos) > 0);
		
		if (sePuede) {
			this.lArmas.put(pos, lArmas.get(pos) - 1);
			System.out.println(this.lArmas.get(pos));
			setChanged();
			notifyObservers(this.lArmas.get(pos)+"" + pos.charAt(0));
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
//		if(a instanceof Reparar)System.out.println("Accion en jugador: "+a);
		this.panel.accionarTile(x, y, a);
		if(!(a instanceof Seleccion || a instanceof ConsultaRadar || a instanceof Escudo|| a instanceof Reparar)) { //Se puede usar esto o la linea comentada en actuar de gestor turno
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
	public int ponerBarco(int x, int y, int pTam, int pCodDir) {
		int res;
		if (this.lBarcos[pTam].size()<5-pTam && panel.sePuedePoner(x,y,pTam,pCodDir)) { 
			res = 1;
			int i = 0;
			Barco b = Astillero.getAstillero().construirBarco(pTam);
			this.anadirBarco(b, pTam);
			notifyObservers(pTam);
			if(pCodDir == 0){
				while (i < pTam){
					TBarco tb =  new TBarco(x,y-i,/*false*/true);
					b.anadirTBarco(tb);
					this.ponerTBPanel(x,y-i,tb);
					i++;
				}
			}
			else if(pCodDir == 1){
				while (i < pTam){
					TBarco tb =  new TBarco(x+i,y,/*false*/true);
					b.anadirTBarco(tb);
					this.ponerTBPanel(x+i,y,tb);
					i++;
				}
			}
			else if(pCodDir == 2){
				while (i < pTam){
					TBarco tb =  new TBarco(x,y+i,/*false*/true);
					b.anadirTBarco(tb);
					this.ponerTBPanel(x,y+i,tb);
					i++;
				}
			}
			else{
				while (i < pTam){
					TBarco tb =  new TBarco(x-i,y,/*false*/true);
					b.anadirTBarco(tb);
					this.ponerTBPanel(x-i,y,tb);
					i++;
				}
			}
			res = this.comprobarFinAnadirBarcos();
			this.rodearBarco(x, y, pTam, pCodDir);
		}
		
		else{
			res = 0;
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
	
	public int comprobarFinAnadirBarcos() { //Comprueba si se ha añadido el máximo de cada tipo de barco y si es así cambia el turno
		int res = 1;
		boolean lleno = true;
		for(int i = 1; i < this.lBarcos.length; i++) {
			lleno = lleno && (this.lBarcos[i].size() == 5-i);
		}
		if(lleno) {
			res=2;
			setChanged();
			notifyObservers(true);
		}
		return res;
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
	
	
	public void setRadar(int x, int y, int tab) {
		this.radar = new Coordenada(x,y,tab);
	}
	public Coordenada getRadar() {
		return this.radar;	
	}
	
	public void comprar(String delta) {
		Tienda f = Tienda.getTienda();
		if (f.checkearPrecio(delta, dinero)) {
			int x = f.comprar(delta);
			this.lArmas.put(delta, lArmas.get(delta) + 1);
			setChanged();
			notifyObservers(this.lArmas.get(delta)+"" + delta.charAt(0));
			reducirDinero(x);
		}
	}
	private void reducirDinero(Integer x){
		this.dinero= this.dinero - x;
		setChanged();
		notifyObservers(this.dinero+""+'D');
	}

}