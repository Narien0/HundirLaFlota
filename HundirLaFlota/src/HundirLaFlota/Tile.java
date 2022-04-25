package HundirLaFlota;

import java.util.Observable;

import javax.swing.JPanel;

public class Tile extends Observable{

	protected int coordX;
	protected int coordY;
	protected boolean oculto;

	public Tile(int pCoordX, int pCoordY, boolean pOc) {
		// TODO - implement Tile.Tile
		this.coordX=pCoordX;
		this.coordY=pCoordY;
		this.oculto=pOc;
		this.addObserver(Vista.getVista());
	}

	public void revelar() {
		// TODO - implement Tile.revelar
		this.oculto = false;
		setChanged();
		notifyObservers(0);
	}
	
	public void seleccionar() {
		setChanged();
		notifyObservers(-1);
	}

	public void ejecutar(Accion a) {
		// TODO - implement Tile.ejecutar
		//throw new UnsupportedOperationException();
		a.ejecutarse(this);
	}
	
	public int getCoordX() {
		return this.coordX;
	}
	
	public int getCoordY() {
		return this.coordY;
	}

}