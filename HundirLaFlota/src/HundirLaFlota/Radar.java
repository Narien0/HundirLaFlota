package HundirLaFlota;

public class Radar{
	private int posX;
	private int posY;
	private int posTab;
	
	public Radar(int x, int y, int tab) {
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
