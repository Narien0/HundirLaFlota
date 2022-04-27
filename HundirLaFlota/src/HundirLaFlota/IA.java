package HundirLaFlota;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.Objects;
import java.util.Random;
import java.util.stream.Collectors;

public class IA extends Jugador {

	private boolean [][] probados;
	private Random randomgenerator;

	public IA() {
		this.probados = new boolean[10][10];
		for (int i = 0; i < 10;i++) {
			for (int j=0; j<10;j++) {
				probados[i][j]=false;
			}
		}
	}

	public void ponerBarcosInteligente() {
		boolean[][] buscar = new boolean[10][10];
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
		for (int i=0;i<10;i++) {
			for (int j=0;j<10;j++) {
				buscar[i][j] = true; //True indica en q pos no se ha buscado
			}
		}
		int intentos = 0;
		while(!lista.isEmpty() && intentos <100){
			intentos++;
			boolean puesto = false;
			int cont = 0;
			int x = (int)(Math.random()*10);
			int y = (int)(Math.random()*10);
			int pCodDir = (int)(Math.random()*4);
			int pTam = lista.get(0);
			while (!puesto && cont < 4) {		
				puesto = 1==this.ponerBarco(x, y, pTam, pCodDir);
				if (!puesto) {
				pCodDir = this.rotar(pCodDir);
				}
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
				if(indx>=0&&indx<10) {
					indy=yinic-1;
					while(indy<=yfin+1) {
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
		
		int accion = (int) (Math.random()*5); //Quinta accion cambiar posicion/poner radar
		int x;
		int y;
		randomgenerator = new Random();
		if(this.lArmas.get(accion)>0) {
			if(accion!=4) this.lArmas.set(accion,this.lArmas.get(accion)-1); //Se resta a menos que sea cambiar pa posicion del radar 
		}
		else {
			accion = 0;
			this.lArmas.set(accion,this.lArmas.get(accion)-1);
		}
		
		if(accion==2) {//Usar escudo
			List<Barco> delta = Arrays.stream(this.lBarcos)
						.filter(Objects::nonNull)
							.flatMap(Collection::stream)
								.filter(e -> !e.estaHundido() && !e.estaProtegido())
									.collect(Collectors.toList());
			if (delta.size() != 0) {
				delta.get(randomgenerator.nextInt(delta.size())).setProtegido(2);
			}
		}else if(accion==3) {
			if(this.radar==null) {//Comprobación de si se ha generado una radar ya
				this.ponerRadar();
			}
			Actuador.getActuador().obtenerPosRadarAlmacenada(1);
			Actuador.getActuador().almacenarAccion(accion);
			Actuador.getActuador().actuarContra(0);
		}else if(accion==4){//Cambiar posicion del radar
			this.ponerRadar();
		}else {//Usar bomba o misil
			Actuador.getActuador().almacenarAccion(accion);
			x = (int) (Math.random()*10);
			y = (int) (Math.random()*10);
			Actuador.getActuador().almacenarPos(x, y, 0);
			int cont = 0;
			while (this.probados[x][y]==true && cont<=1000) {
				x = (int) (Math.random()*10);
				y = (int) (Math.random()*10);
				cont++;
			}
			
			if(this.probados[x][y]==true) { //Para que nunca repita posiciones aunque tras las iteraciones de forma aleatoria se encuentre con una que ya ha usado
				cont = 0;
				while(this.probados[x][y]==true&&cont<=100) {
					cont++;
					x++;
					if(x>9) {
						y++;
						x=0;
						}
					if(y>9) {
						y=0;
					}
				}
			}
			probados[x][y]=true;
			Actuador.getActuador().actuarContra(0);
		}
	}
	
	@Override
	public int comprobarFinAnadirBarcos() { //Comprueba si se ha añadido el máximo de cada tipo de barco y si es así cambia el turno
		boolean lleno = true;
		int res = 1;
		for(int i = 1; i < this.lBarcos.length; i++) {
			lleno = lleno && (this.lBarcos[i].size() == 5-i);
		}
		if(lleno) {
			res = 2;
			setChanged();
			notifyObservers(true);
			setChanged();
			notifyObservers(false);
		}
		return res;
	}
	
	
	@Override //METODO PARA QUE NO SE MUESTREN LOS BARCOS PUESTOS POR LA IA
	protected void ponerTBPanel(int x, int y, TBarco tb) {
		this.panel.ponerTileEnPos(x, y, tb);
	}

	private void ponerRadar() {
		GestorTurno.getGestorTurno().ponerRadarEnTablero(0);
		Actuador.getActuador().almacenarAccion(-1);
		Actuador.getActuador().obtenerPosRadarAlmacenada(1);
		Actuador.getActuador().actuarContra(0);
	}
	
}