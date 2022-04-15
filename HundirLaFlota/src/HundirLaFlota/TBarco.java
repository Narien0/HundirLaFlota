package HundirLaFlota;

public class TBarco extends Tile {

	private Barco barco;
	private boolean tocado;
	private boolean mostrado;

	public TBarco(int pCoordX, int pCoordY, boolean pOc) {
		// TODO - implement TBarco.TBarco
		super(pCoordX,pCoordY,pOc);
		this.tocado = false;
		this.mostrado = false;
//		System.out.println(this.coordX+"  "+this.coordY);
	}

	public boolean getTocado() {
		return this.tocado;
	}

	public void tocar() {
		// TODO - implement TBarco.tocar
		if (this.barco.estaProtegido()) {
			this.barco.setProtegido(false);
		}else {
			this.tocado = true;
			this.barco.tocado();
			setChanged();
			notifyObservers(1);
		}
	}
	
	public void anadirABarco(Barco ba) {
			this.barco=ba;
	}
	
	public void tocarEntero() {
		if (this.barco.estaProtegido()) {
			this.barco.setProtegido(false);
		}else {
			this.tocado = true;
			this.barco.hundir();
			setChanged();
			notifyObservers(1);
		}
	}

	public void mostrar() {
		this.mostrado = true;
		setChanged();
		notifyObservers(3);
	}
	
	public boolean getMostrado() {
		return this.mostrado&&(!this.tocado);
	}

}