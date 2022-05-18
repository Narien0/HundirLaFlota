package HundirLaFlota;

import java.util.HashMap;
import java.util.Map;

public class Tienda {
    private Map<String,Integer> Objetos;
    private static Tienda mTienda;

    private Tienda(){
        Objetos = new HashMap<>();
        Objetos.put("Bomba", 2);
        Objetos.put("Misil", 2);
        Objetos.put("Escudo",2);
        Objetos.put("Reparar",2);
        Objetos.put("ConsultaR",3);

    }

    public static Tienda getTienda(){
        if (mTienda ==  null){
            mTienda = new Tienda();
        }
        return mTienda;
    }
    public boolean checkearPrecio(String delta, double x){
            return x >= Objetos.get(delta);
    }
    public Integer comprar(String delta){
        return Objetos.get(delta);
    }
}
