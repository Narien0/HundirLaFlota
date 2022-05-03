package HundirLaFlota;

public class Seleccion implements Accion{
	
	public Seleccion() {
		
	}
	
	@Override
	public void ejecutarse(Tile pT) {
		pT.seleccionar();
	}
	
}
