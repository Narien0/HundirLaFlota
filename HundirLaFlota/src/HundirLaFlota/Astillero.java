package HundirLaFlota;

public class Astillero {
	private static Astillero mAstillero = null;
	private Astillero() {	}
	public static Astillero getAstillero() {
		if (mAstillero == null) {
			mAstillero = new Astillero();
		}
		return mAstillero;
	}
	public Barco construirBarco(int pTam) {
		Barco mBarco = null;
		mBarco = new Barco(pTam);
		return mBarco;
	}
}
