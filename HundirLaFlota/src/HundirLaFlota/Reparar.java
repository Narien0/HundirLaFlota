package HundirLaFlota;

public class Reparar implements Accion{
    public Reparar(){
    }
    public void ejecutarse(Tile pT) {
        if (pT instanceof TBarco){
            ((TBarco) pT).reparar();
        }
    }
}
