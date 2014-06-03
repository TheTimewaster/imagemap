package draw;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Stroke;

public interface GraphObject {
	
	public abstract void paint(Graphics2D g2, boolean tmp);
	public abstract void setStartX(int sx);
	public abstract void setStartY(int sy);
	public abstract void setEndX(int ex);
	public abstract void setEndY(int ey);
	public abstract int[] getCoords();
	public abstract void setFillColor(Color c);
	public abstract void setStrokeColor(Color c);
	public abstract void setStroke(int s);
}
