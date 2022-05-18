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
		this.addObserver(IA.getmIA());
	}

	public boolean getTocado() {
		return this.tocado;
	}

	public void tocar() {
		// TODO - implement TBarco.tocar
		if (this.barco.estaProtegido()) {
			this.marcarProtegido(true);
			this.barco.setProtegido(1);
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
			this.barco.setProtegido(0);
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
	
	public void protegerse(){
		this.barco.setProtegido(2);
	}
	
	public boolean estaProtegido() {
		return this.barco.estaProtegido();
	}
	
	public void marcarProtegido(boolean porSerRevelado) { //el parametro sirve para poder distinguir en que tablero buscar la casilla en la vista no tiene funcionalidad en el modelo
		if(!this.oculto) {
			int param = 2;
			if(porSerRevelado) param = 12;
			setChanged();
			notifyObservers(param);
		}
	}
	
	public void desmarcarProtegido() {
		if(!this.oculto) {
			if(!this.tocado) {
				setChanged();
				notifyObservers(0);
			}else {
			setChanged();
			notifyObservers(1);
			}
		}
	}
	
	@Override
	public void revelar() {
		this.oculto = false;
		if(this.barco.estaProtegido()) {
			this.marcarProtegido(true);
		}else {
			super.revelar();
		}
	}
	
	public void selfReparar(){
		System.out.println("Parte reparada");
		this.tocado = false;
		setChanged();
		this.notifyObservers(4);
	}
	public void reparar(){
		System.out.println("Parte reparadora");
		this.tocado = false;
		this.barco.reparar();
		setChanged();
		this.notifyObservers(4);
	}

}