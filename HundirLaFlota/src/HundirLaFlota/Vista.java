package HundirLaFlota;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import java.awt.GridLayout;
import java.awt.event.MouseListener;
import java.util.Observable;
import java.util.Observer;

import javax.swing.JLabel;
import java.awt.GridBagLayout;
import javax.swing.JButton;
import java.awt.FlowLayout;
import javax.swing.JSplitPane;

public class Vista extends JFrame implements Observer {

	private JPanel contentPane;
	private static Vista mVista;
	
	private JLabel[][] gDer;	//De la IA
	private JLabel[][] gIzq;	//Del usuario

	
	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Vista frame = Vista.getVista();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 */
	
	private boolean turnoUsr;
	private int estado;
	
	private JLabel antSeleccion;
	
	private JPanel menuAcciones;
	private JPanel menuPosicionar;
	private JPanel menuPonerBarcos;
	private JPanel panelFin;
	private JPanel panelInfo;
	
	private JLabel lblBarcosusr;
	private JLabel lblBarcosia;
	
	private JLabel lblSeleccion;
	
	private JLabel lblGana;
	
	//######################Colores
	private Color cBarco;
	private Color cAgua;
	private Color cAguaTocada;
	private Color cSeleccion;
	private Color cOculto;
	private Color cTocado;
	
	public Vista() {
		this.estado = 0;
		
		this.gDer = new JLabel[10][10];
		this.gIzq = new JLabel[10][10];
		
		this.cBarco = Color.black;
		this.cAgua = Color.blue;
		this.cAguaTocada = new Color(0,0,139);
		this.cSeleccion = Color.gray;
		this.cOculto = Color.darkGray;
		this.cTocado = Color.red;
		
		/// Creacion general
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 850, 550);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		contentPane.setLayout(new BorderLayout(0, 0));
		setContentPane(contentPane);
		
		/// Creacion de paneles
		JPanel pnlCentro = new JPanel();
		contentPane.add(pnlCentro, BorderLayout.CENTER);
		pnlCentro.setLayout(new GridLayout(0, 2, 0, 0));
		
		JPanel gridIzq = new JPanel();
		gridIzq.setBorder(new EmptyBorder(10, 10, 10, 10));
		pnlCentro.add(gridIzq);
		gridIzq.setLayout(new GridLayout(10, 10, 5, 5));
		
		
		JLabel aux;
		for (int i=0;i<10;i++) {
			for (int j=0;j<10;j++) {
				aux = new JLabel(j+""+i+"I");
				aux.setOpaque(true);
				this.cambiarColorCasilla(aux, Color.DARK_GRAY);
				aux.addMouseListener(Controlador.getControlador());
				gridIzq.add(aux);
				gIzq[j][i] = aux;
			}
		}
		
		JPanel gridDer = new JPanel();
		gridDer.setBorder(new EmptyBorder(10, 10, 10, 10));
		pnlCentro.add(gridDer);
		gridDer.setLayout(new GridLayout(10, 10, 5, 5));
		
		for (int i=0;i<10;i++) {
			for (int j=0;j<10;j++) {
				aux = new JLabel(j+""+i+"D");
				aux.setOpaque(true);
				this.cambiarColorCasilla(aux, Color.DARK_GRAY);
				aux.addMouseListener(Controlador.getControlador());
				gridDer.add(aux);
				gDer[j][i] = aux;
			}
		}
		
		JPanel pnlBajo = new JPanel();
		contentPane.add(pnlBajo, BorderLayout.SOUTH);
		pnlBajo.setLayout(new FlowLayout(FlowLayout.CENTER, 5, 5));
		
		menuAcciones = new JPanel();
		pnlBajo.add(menuAcciones);
		menuAcciones.setLayout(new FlowLayout(FlowLayout.CENTER, 5, 5));
		menuAcciones.hide();
		
		JButton btnBomba = new JButton("Bomba");
		menuAcciones.add(btnBomba);
		btnBomba.addActionListener(Controlador.getControlador());

		JButton btnMisil = new JButton("Misil");
		menuAcciones.add(btnMisil);
		btnMisil.addActionListener(Controlador.getControlador());
		
		menuPosicionar = new JPanel();
		pnlBajo.add(menuPosicionar);
				
				JButton btnLimpiarTablero = new JButton("Limpiar Tablero");
				menuPosicionar.add(btnLimpiarTablero);
				btnLimpiarTablero.addActionListener(Controlador.getControlador());
		
				menuPonerBarcos = new JPanel();
				menuPosicionar.add(menuPonerBarcos);
				menuPonerBarcos.setLayout(new GridLayout(2, 1, 0, 0));
				
				JPanel barcos = new JPanel();
				menuPonerBarcos.add(barcos);
				barcos.setLayout(new FlowLayout(FlowLayout.CENTER, 5, 5));
				
				JButton btnPortaviones = new JButton("Portaviones");
				barcos.add(btnPortaviones);
				btnPortaviones.addActionListener(Controlador.getControlador());
				
				JButton btnSubmarino = new JButton("Submarino");
				barcos.add(btnSubmarino);
				btnSubmarino.addActionListener(Controlador.getControlador());
				
				JButton btnDestructor = new JButton("Destructor");
				barcos.add(btnDestructor);
				btnDestructor.addActionListener(Controlador.getControlador());
				
				JButton btnFragata = new JButton("Fragata");
				barcos.add(btnFragata);
				btnFragata.addActionListener(Controlador.getControlador());
				
				JPanel direcciones = new JPanel();
				menuPonerBarcos.add(direcciones);
				
				JButton btnArriba = new JButton("Arriba");
				direcciones.add(btnArriba);
				btnArriba.addActionListener(Controlador.getControlador());
				
				JButton btnAbajo = new JButton("Abajo");
				direcciones.add(btnAbajo);
				btnAbajo.addActionListener(Controlador.getControlador());
				
				JButton btnDerecha = new JButton("Derecha");
				direcciones.add(btnDerecha);
				btnDerecha.addActionListener(Controlador.getControlador());
				
				JButton btnIzquierda = new JButton("Izquierda");
				direcciones.add(btnIzquierda);
				btnIzquierda.addActionListener(Controlador.getControlador());
		
		
		
		////###############################################################
		panelInfo = new JPanel();
		pnlBajo.add(panelInfo);
		panelInfo.setLayout(new GridLayout(3, 1, 0, 0));
		
		lblBarcosusr = new JLabel("BarcosUsr:");
		panelInfo.add(lblBarcosusr);
				
		lblBarcosia = new JLabel("BarcosIA:");
		panelInfo.add(lblBarcosia);
		
		lblSeleccion = new JLabel("Barco Seleccionado: ");
		panelInfo.add(lblSeleccion);
		
		
		this.panelFin = new JPanel();
		pnlBajo.add(panelFin);
		panelFin.setVisible(false);
		
		this.lblGana = new JLabel("Gana el");
		panelFin.add(lblGana);
		
		
	}
	
	public static Vista getVista() {
		if(mVista==null) {
			mVista=new Vista();
		}
		return (mVista);
	}

	@Override
	public void update(Observable o, Object arg) {
		// TODO Auto-generated method stub
		if (o instanceof Tile) {
			JLabel aux = this.buscarLabel(((Tile) o).getCoordX(),((Tile) o).getCoordY());
			if((Integer)arg==0) {
				if(o instanceof Agua) {
					if(aux.getBackground().equals(cAgua) && estado ==1) {
						this.cambiarColorCasilla(aux, cAguaTocada);
					}else {
						this.cambiarColorCasilla(aux, this.cAgua);	
					}
				}
				else if(o instanceof TBarco)	this.cambiarColorCasilla(aux, this.cBarco);
			}else if ((Integer)arg==1) {
				this.cambiarColorCasilla(aux, this.cTocado);
			}else if ((Integer)arg==-1) {
				if(this.antSeleccion!=null && this.antSeleccion.getBackground().equals(this.cSeleccion)) {
					this.cambiarColorCasilla(this.antSeleccion, this.cAgua); 
				}
				if(!aux.getBackground().equals(this.cBarco)) {
					this.cambiarColorCasilla(aux,this.cSeleccion);
					this.antSeleccion = aux; 
				}
			}
			
			
		}else if (o instanceof Jugador) {
			String aux;
			if(((Integer)arg)==1) {
				aux="F";
			}else if(((Integer)arg)==2) {
				aux="D";
			}else if(((Integer)arg)==3) {
				aux="S";
			}else {
				aux="P";
			}
			JLabel union;
			if(o instanceof IA) {
				union = lblBarcosia;
			}else {
				union =lblBarcosusr;
			}
			union.setText(union.getText() +" "+ aux);
			
			
		}else if (o instanceof Modelo) {
			//Cambio de estado
			if(arg instanceof Integer) {
				if((int)arg==1) {
					this.menuPosicionar.setVisible(false);
					this.menuAcciones.setVisible(true);
					this.lblSeleccion.setText("Accion Seleccionada: ");
					this.estado=(int) arg;
				}else if((int)arg==2) {
					this.menuAcciones.setVisible(false);
					this.panelInfo.setVisible(false);
					this.panelFin.setVisible(true);
					this.estado=(int) arg;
				}
			}else if(arg instanceof String) {
				if(estado == 0 /*&& (((String)arg).equals("Fragata")||((String)arg).equals("Destructor")||((String)arg).equals("Submarino")||((String)arg).equals("Portaviones"))*/) {	
					String selec = "Barco Seleccionado: "  + arg;
					lblSeleccion.setText(selec);
				}else if(estado == 1 /*&&(((String)arg).equals("Bomba")||((String)arg).equals("Misil"))*/) {
					String selec = "Accion Seleccionada: "+ arg;
					lblSeleccion.setText(selec);
				}else {
					this.lblGana.setText((String)arg);
				}
			}else /*if(arg instanceof Boolean)*/{
				this.turnoUsr = (boolean) arg;
			}
		}
	}
	
	private JLabel buscarLabel(int x, int y) {
		JLabel[][] matriz;
		if(this.estado==0) {
			if(this.turnoUsr) matriz = this.gIzq;
			else matriz = this.gDer;
		}else /*if (estado==1)*/ {
			if(this.turnoUsr) matriz = this.gDer;
			else matriz = this.gIzq;
		}
		return matriz[x][y];
	}
	
	private void cambiarColorCasilla(JLabel jl, Color cl) {
		jl.setBackground(cl);
		jl.setForeground(cl);
	}
	
	
//	public void TESTOCUPADO(int x, int y) { //Usado para visualizar el espacio de agua ocupado al poner barcos en testeo
//		gIzq[x][y].setBackground(Color.green);;
//	}
}
