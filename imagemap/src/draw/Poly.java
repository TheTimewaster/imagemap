package draw;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Polygon;
import java.io.Serializable;

public class Poly implements GraphObject, Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 2075175270982188237L;
	private int x;
	private int y;
	private int length = 0;
	private int[] xCoords;
	private int[] yCoords;
	private Color fillColor;
	private Color strokeColor;
	private int strokeThickness;
	private Polygon poly;

	@Override
	public void paint(Graphics2D g2, boolean tmp) {
		xCoords = new int[500];
		yCoords = new int[500];
		poly = new Polygon();
		// TODO Auto-generated method stub
		if (tmp == false) {
			g2.setStroke(new BasicStroke(1, BasicStroke.CAP_SQUARE,
					BasicStroke.JOIN_MITER, 10.0f, new float[] { 8.0f, 6.0f },
					0.0f));
			g2.drawPolyline(xCoords, yCoords, length);
		} else {
			g2.setStroke(new BasicStroke(strokeThickness));
			g2.setColor(strokeColor);
			g2.drawPolygon(xCoords, yCoords, length);
			g2.setColor(fillColor);
			g2.drawPolygon(xCoords, yCoords, length);
		}
	}

	@Override
	public void setStartX(int sx) {
		// TODO Auto-generated method stub
		this.x = sx;
	}

	@Override
	public void setStartY(int sy) {
		// TODO Auto-generated method stub
		this.y = sy;
	}

	@Override
	public void setEndX(int ex) {
		// TODO Auto-generated method stub
		this.x = ex;
	}

	@Override
	public void setEndY(int ey) {
		// TODO Auto-generated method stub
		this.y = ey;
	}

	@Override
	public void setFillColor(Color c) {
		// TODO Auto-generated method stub
		this.fillColor = c;
	}

	@Override
	public void setStrokeColor(Color c) {
		// TODO Auto-generated method stub
		this.strokeColor = c;
	}

	@Override
	public void setStroke(int s) {
		// TODO Auto-generated method stub
		this.strokeThickness = s;
	}

	public void addCoords() {
		xCoords[length] = x;
		yCoords[length] = y;
		length++;
	}

	@Override
	public int[] getCoords() {
		// TODO Auto-generated method stub
		return null;
	}

}
