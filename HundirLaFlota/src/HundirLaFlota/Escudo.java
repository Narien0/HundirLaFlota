package HundirLaFlota;

public class Escudo implements Accion {

    public Escudo(){

    }

    public void ejecutarse(Tile pT) {
        if (pT instanceof TBarco){
            ((TBarco) pT).protegerse();
        }
    }
}
