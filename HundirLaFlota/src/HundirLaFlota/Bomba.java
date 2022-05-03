package HundirLaFlota;

public class Bomba implements Accion{

	public Bomba() {
		// TODO - implement Bomba.Bomba
	}
	
	@Override
	public void ejecutarse(Tile pT) {
		pT.revelar();
		if(pT instanceof TBarco) {
			((TBarco) pT).tocar();
		}
	}

}