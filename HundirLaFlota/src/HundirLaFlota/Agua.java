package HundirLaFlota;

public class Agua extends Tile {

	private boolean ocupado; //indica si ya hay un barco adyacente a esta agua

	public Agua(int pCoordX, int pCoordY, boolean pOc) {
		// TODO - implement Agua.Agua
		super(pCoordX,pCoordY,pOc);
		this.ocupado = false;
	}

	public boolean getOcupado() {
		return this.ocupado;
	}
	
	public void setOcupado(boolean b) {
		this.ocupado=b;
//		System.out.println(""+this.coordX+this.coordY);
//		Vista.getVista().TESTOCUPADO(coordX, coordY);
	}

}