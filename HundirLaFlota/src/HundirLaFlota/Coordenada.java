package HundirLaFlota;

public class Coordenada{
	private int posX;
	private int posY;
	private int posTab;
	
	public Coordenada(int x, int y, int tab) {
		this.posX = x;
		this.posY = y;
		this.posTab = tab;
	}
	
	public int getX() {
		return this.posX;
	}
	
	public int getY() {
		return this.posY;
	}
	
	public int getTab() {
		return this.posTab;
	}
	
}
