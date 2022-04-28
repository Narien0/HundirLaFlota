package HundirLaFlota;

public class ponerBarcosAuto {
	private static ponerBarcosAuto mPonerBarcosAuto;
	
	private ponerBarcosAuto() {}
	public static ponerBarcosAuto getPonerBarcosAuto() {
		if(mPonerBarcosAuto==null) {
			mPonerBarcosAuto = new ponerBarcosAuto();
		}
		return mPonerBarcosAuto;
	}
	
	public void usar() {
		Actuador.getActuador().almacenarTamBarco(4);
		Actuador.getActuador().almacenarDireccion(0);
		Actuador.getActuador().almacenarPos(8, 8, 0);
		GestorTurno.getGestorTurno().actuar();
		

		Actuador.getActuador().almacenarTamBarco(3);
		Actuador.getActuador().almacenarDireccion(0);
		Actuador.getActuador().almacenarPos(6, 8, 0);
		GestorTurno.getGestorTurno().actuar();
		
		Actuador.getActuador().almacenarTamBarco(3);
		Actuador.getActuador().almacenarDireccion(0);
		Actuador.getActuador().almacenarPos(4, 8, 0);
		GestorTurno.getGestorTurno().actuar();

		
		Actuador.getActuador().almacenarTamBarco(2);
		Actuador.getActuador().almacenarDireccion(0);
		Actuador.getActuador().almacenarPos(2, 8, 0);
		GestorTurno.getGestorTurno().actuar();
		
		Actuador.getActuador().almacenarTamBarco(2);
		Actuador.getActuador().almacenarDireccion(0);
		Actuador.getActuador().almacenarPos(0, 8, 0);
		GestorTurno.getGestorTurno().actuar();
		
		Actuador.getActuador().almacenarTamBarco(2);
		Actuador.getActuador().almacenarDireccion(0);
		Actuador.getActuador().almacenarPos(0, 5, 0);
		GestorTurno.getGestorTurno().actuar();
		
		
		Actuador.getActuador().almacenarTamBarco(1);
		Actuador.getActuador().almacenarDireccion(0);
		Actuador.getActuador().almacenarPos(0, 0, 0);
		GestorTurno.getGestorTurno().actuar();
		
		Actuador.getActuador().almacenarTamBarco(1);
		Actuador.getActuador().almacenarDireccion(0);
		Actuador.getActuador().almacenarPos(2, 0, 0);
		GestorTurno.getGestorTurno().actuar();
		
		Actuador.getActuador().almacenarTamBarco(1);
		Actuador.getActuador().almacenarDireccion(0);
		Actuador.getActuador().almacenarPos(4, 0, 0);
		GestorTurno.getGestorTurno().actuar();
		
		Actuador.getActuador().almacenarTamBarco(1);
		Actuador.getActuador().almacenarDireccion(0);
		Actuador.getActuador().almacenarPos(6, 0, 0);
		GestorTurno.getGestorTurno().actuar();

	}
}
