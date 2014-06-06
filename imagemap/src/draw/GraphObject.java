package draw;


import java.awt.Graphics2D;


public interface GraphObject {

	public abstract void paint(Graphics2D g2, boolean tmp);

	public abstract void setStartX(int sx);

	public abstract void setStartY(int sy);

	public abstract void setEndX(int ex);

	public abstract void setEndY(int ey);

	public abstract int[] getCoords();
	
}
