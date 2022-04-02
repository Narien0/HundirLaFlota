package HundirLaFlota;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;

public class Barco {

	private Collection<TBarco> lTBarcos;
	private boolean hundido;
	private boolean protegido;

	/**
	 * 
	 * @param pTam
	 */
	public Barco(int pTam) {
		this.hundido = false;
		this.protegido = false;
		this.lTBarcos = new ArrayList<TBarco>();
	}

	public boolean estaProtegido() {
		return this.protegido;
	}

	public void anadirTBarco(TBarco tB) {
		this.lTBarcos.add(tB);
		tB.anadirABarco(this);
	}

	public boolean estaHundido() {
		return this.hundido;
	}

	public void setProtegido(boolean b) {
		// TODO Auto-generated method stub
		this.protegido = b;
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

}