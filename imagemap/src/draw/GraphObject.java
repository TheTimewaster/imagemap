package draw;


import java.awt.Graphics2D;
import java.util.ArrayList;


public interface GraphObject {

	public abstract void paint(Graphics2D g2, boolean tmp);

	public abstract void setStartX(int sx);

	public abstract void setStartY(int sy);

	public abstract void setEndX(int ex);

	public abstract void setEndY(int ey);

	public abstract ArrayList<Integer> getCoords();
	
}
