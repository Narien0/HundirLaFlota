package HundirLaFlota;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;

public class Barco {

	private ArrayList<TBarco> lTBarcos;
	private boolean hundido;
	private int protegido;

	/**
	 * 
	 * @param pTam
	 */
	public Barco(int pTam) {
		this.hundido = false;
		this.protegido = 0;
		this.lTBarcos = new ArrayList<TBarco>();
	}

	public boolean estaProtegido() {
		return this.protegido!=0;
	}

	public boolean estatocado(){
		return this.lTBarcos.stream().anyMatch(e -> e.getTocado());
	}
	
	public void anadirTBarco(TBarco tB) {
		this.lTBarcos.add(tB);
		tB.anadirABarco(this);
	}

	public boolean estaHundido() {
		return this.hundido;
	}
	
	public void tocado() {//Comprueba si el barco se ha undido despues de ser tocado cada vez
		boolean b = true;
		TBarco aux;
		Iterator<TBarco> itr = this.lTBarcos.iterator();
		while(itr.hasNext()) {
			aux = itr.next();
			b = b && aux.getTocado();
		}
		this.hundido = b;
	}
	
	public void hundir() {
		TBarco aux;
		Iterator<TBarco> itr = this.lTBarcos.iterator();
		while(itr.hasNext()) {
			aux = itr.next();
			aux.tocar();
		}
	}
	
	public void setProtegido(int x) {
		// TODO Auto-generated method stub
		if (x == 1){
			this.protegido = this.protegido - x;
		}
		else{
			if(this.protegido==0) {
				this.marcarProtegidos();
			}
			this.protegido = x;
		}
		if(this.protegido==0) {
			this.desmarcarProtegidos();
		}
	}
	
	public void marcarProtegidos() {
		for(int i=0;i<this.lTBarcos.size();i++) {
			this.lTBarcos.get(i).marcarProtegido(false);
		}
	}
	
	public void desmarcarProtegidos() {
		for(int i=0;i<this.lTBarcos.size();i++) {
			this.lTBarcos.get(i).desmarcarProtegido();
		}
	}
	
	public void reparar(){
		lTBarcos.stream().filter(e -> e.getTocado() == true).forEach(e -> e.selfReparar());
	}
}