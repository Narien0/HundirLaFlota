package HundirLaFlota;

import java.util.Observable;

public class Modelo extends Observable {

	private static Modelo mModelo;
	private Accion accionCargada;
	private boolean turnoUsuario;  //Indica si quien realiza la acción es el usuario
	private Jugador usuario;
	private IA ia;
	private int bapCoordX;
	private int bapCoordY;
	private int bapTamano;
	private int estado;				//Estado 0: colocoar barcos, estado 1: bombardear

	private Modelo() {
		this.turnoUsuario = true;
		this.estado = 0;

		this.addObserver(Vista.getVista());
		setChanged();
		notifyObservers(this.turnoUsuario);
		
		this.usuario=new Jugador();
		this.ia = new IA();
		
		this.resetBAP();
	}

	public static Modelo getModelo() {
		// TODO - implement Modelo.getModelo
		if (mModelo == null) {
			mModelo = new Modelo();
		}
		return mModelo;
	}

	public void cargarAccion(int pCodAcc) {
		// TODO - implement Modelo.cargarAccion
		String notificacion="";
		if (pCodAcc==0) {
			this.accionCargada = new Bomba();
			notificacion = "Bomba";
		}else if(pCodAcc==1) {
			this.accionCargada = new Misil();
			notificacion = "Misil";
		}
		if(this.turnoUsuario) {
			setChanged();
			notifyObservers(notificacion);
		}
	}

	private void actuarSobreTile(int x, int y) {
		// Dependiendo del turno uno realiza y el otro consume
		Jugador j1, j2;
		if (turnoUsuario) {
			j1 = this.usuario;
			j2 = this.ia;
		}else {
			j1 = this.ia;
			j2 = this.usuario;
		}
		j1.consumirRecuro(this.accionCargada);
		j2.actuarSobre(this.accionCargada, x, y);
		if(estado==1)this.cambioTurno();
		if(j2.todosHundidos()&&estado==1) {
			this.cambioEstado();
			this.pierde(j2);
			this.accionCargada=null;
		}
		this.accionCargada = null;
	}

	public void recibirPos(int x, int y, char tablero) {
		// Si estamos en estado de colocar barcos guardamos las coordenadas, sino mandamos la acción 
		// a cada jugador para que la traten segun les toque.
		// Además recibimos en que tablero se 
		if(this.tableroApropiadoAlTurno(tablero)) {
			if (estado ==0) {
				this.bapCoordX=x;
				this.bapCoordY=y;
				this.accionCargada = new Seleccion();
				this.actuarSobreTile(x, y);
				this.accionCargada = null;
			}else if (estado == 1) {
				if(this.accionCargada!=null) {
					setChanged();
					notifyObservers("");
					this.actuarSobreTile(x, y);
				}
			}
		}
	}

	public void recibirDir(int pCodDir) {
		Jugador u;
		if (turnoUsuario) {
			u = this.usuario;
		}else {
			u = this.ia;
		}
		//En comparacion con el resto de los Recibires este no se alamacena, sino que pasa directamente al jugador
		// 0 = Norte
		// 1 = Este
		// 2 = Sur
		// 3 = Oeste
		if(this.bapCoordX !=-1 && this.bapCoordY!=-1 && this.bapTamano!=0) {
			if (estado == 0){
				boolean seHaPuesto = u.ponerBarco(this.bapCoordX,this.bapCoordY,this.bapTamano, pCodDir);
				if(seHaPuesto) {
					setChanged();
					notifyObservers("");
					this.resetBAP();
				}
			}
		}
	}

	public void recibirTamano(int pTam) {
		//Almacenamos el Tamaño, el tipo, de barco para posterior uso en la colocacion
		if(estado == 0){
			this.bapTamano = pTam;
			
			String cod;
			if(this.bapTamano==1)cod="Fragata";
			else if(this.bapTamano==2)cod="Destructor";
			else if(this.bapTamano==3)cod="Submarino";
			else /*if(this.bapTamaño==4)*/cod="Portviones";
			setChanged();
			notifyObservers(cod);
		}
	}

	public void cambioTurno() {
//		Simplemente cambia el booleano de turno falta hacer que el usuario no pueda hacer nada desde vista
		this.turnoUsuario = !this.turnoUsuario;
		setChanged();
		notifyObservers(this.turnoUsuario);
		if(!this.turnoUsuario) {
		if(estado == 0 && !this.turnoUsuario) this.ia.ponerBarcosInteligente(); //Faltan implementar en IA
		else if (estado ==1 && this.turnoUsuario==false) this.ia.realizarAccionInteligente();
		}
	}
	
	public void cambioEstado() {
		this.estado++;
		setChanged();
		notifyObservers(estado);
		if(estado==1)this.resetBAP();
	}
	
	private void resetBAP() {
		//Valores almacenados cuando no se ha seleccionado nada
		this.bapCoordX=-1;
		this.bapCoordY=-1;
		this.bapTamano=0;
	}
	
	private void pierde(Jugador j) {
		String msg;
		if(j.equals(this.usuario))msg="Gana el ordenador. Pierde el jugador";
		else msg="Gana el jugador. Pierde el ordenador";
		setChanged();
		notifyObservers(msg);
		System.out.println(msg);
	}
	
	private boolean tableroApropiadoAlTurno(char c) {
		boolean res;
		if(estado == 0) {
			if(c == 'I' && this.turnoUsuario) res = true;
			else if(c == 'D' && !this.turnoUsuario) res = true;
			else res=false;
		}else /*if(estado==1)*/{
			if(c == 'D' && this.turnoUsuario) res = true;
			else if(c == 'I' && !this.turnoUsuario) res = true;
			else res=false;
		}
		return res;
	}
	
	public void limpiarTableroUsuario() {
		this.usuario= new Jugador();
	}
}