package HundirLaFlota;

import java.util.ArrayList;

public class IA extends Jugador {

	public IA() {
		// TODO - implement IA.IA
	}

	public void ponerBarcosInteligente() {
		boolean[][] buscar = new boolean[10][10];
//		System.out.println("eses");
		ArrayList<Integer> lista = new ArrayList<Integer>();
		int cont1=1;
		int tamMax = 4;

		int tam = tamMax;
		while(tam>=1) {
			cont1=1;
			while(cont1<=tamMax-tam+1) {
				lista.add(tam);
				cont1++;
			}
			tam--;
		}
//		System.out.println(lista.size());
//		System.out.println("pasa");
		for (int i=0;i<10;i++) {
			for (int j=0;j<10;j++) {
				buscar[i][j] = true; //True indica en q pos no se ha buscado
			}
		}
//		System.out.println("Boolean a true");
		int intentos = 0;
		while(!lista.isEmpty() && intentos <90){
			intentos++;
//			System.out.println("Largo");
//			System.out.println(intentos);
			boolean puesto = false;
			int cont = 0;
			int x = (int)(Math.random()*10);
			int y = (int)(Math.random()*10);	
//			System.out.println(x + " " + y);
			int pCodDir = (int)(Math.random()*4);
			int pTam = lista.get(0);
			while (!puesto && cont < 4) {		
				puesto = this.ponerBarco(x, y, pTam, pCodDir);
				if (!puesto) {
				pCodDir = this.rotar(pCodDir);
				}
//				System.out.println("Posicion");
				cont++;
			}
		if (puesto) {
			lista.remove(0);
			int indx;
			int indy;
			int xinic;
			int xfin;
			int yinic;
			int yfin;
			pTam--;
			if(pCodDir==3) {
				xinic=x-pTam; 
				xfin=x;
				yinic=y;
				yfin=y;
			}
			else if (pCodDir==1) {
				xinic=x;
				xfin=x+pTam;
				yinic=y;
				yfin=y;
			}
			else if (pCodDir==0){
				xinic=x;
				xfin=x;
				yinic=y-pTam;
				yfin=y;
			}
			else {
				xinic=x;
				xfin=x;
				yinic=y;
				yfin=y+pTam;
			}
			indx=xinic-1;
			while(indx<=xfin+1) {
//				System.out.println("patata");
//				System.out.println(indx);
//				System.out.println(xfin);
				if(indx>=0&&indx<10) {
					indy=yinic-1;
					while(indy<=yfin+1) {
//						System.out.println("lechuga");
//						System.out.println(indy);
//						System.out.println(yfin);
						if(indy>=0 && indy<10) {
							buscar[indx][indy] = false;							
							}
						
						indy++;
					}
					}
				
				indx++;
			}
		}
		}
	}
	
	private int rotar(int x) {
		if (x == 3) {
			return 0;
		}else {
			return x+1;
		}
	}
	

	public void realizarAccionInteligente() {
		// TODO - implement IA.realizarAccionInteligente
		throw new UnsupportedOperationException();
	}
	
	@Override
	public void comprobarFinAnadirBarcos() { //Comprueba si se ha añadido el máximo de cada tipo de barco y si es así cambia el turno
		boolean lleno = true;
		for(int i = 1; i < this.lBarcos.length; i++) {
			lleno = lleno && (this.lBarcos[i].size() == 5-i);
		}
		if(lleno) {
			Modelo.getModelo().cambioTurno();
			Modelo.getModelo().cambioEstado();
		}
	}
	
	/* 
	@Override //METODO PARA QUE NO SE MUESTREN LOS BARCOS PUESTOS POR LA IA
	protected void ponerTBPanel(int x, int y, TBarco tb) {
		this.panel.ponerTileEnPos(x, y, tb);
	}*/

}