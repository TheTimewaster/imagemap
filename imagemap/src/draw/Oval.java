package draw;


import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics2D;
import java.io.Serializable;
import java.util.ArrayList;


public class Oval implements GraphObject, Serializable {
	private Color strokeColor = Color.black;
	private final int strokeThickness = 2;
	private static final long serialVersionUID = -8290256134052754277L;
	private int sx;
	private int sy;
	private int ex;
	private int ey;

	@Override
	public void setStartX(int sx) {
		// TODO Auto-generated method stub
		this.sx = sx;
	}

	@Override
	public void setStartY(int sy) {
		// TODO Auto-generated method stub
		this.sy = sy;
	}

	@Override
	public void setEndX(int ex) {
		// TODO Auto-generated method stub
		this.ex = ex;
	}

	@Override
	public void setEndY(int ey) {
		// TODO Auto-generated method stub
		this.ey = ey;
	}

	@Override
	public void paint(Graphics2D g2, boolean tmp) {
		// TODO Auto-generated method stub

		if (tmp == false) {
			// float[] dash = { 10, 2 };
			g2.setStroke(new BasicStroke(1, BasicStroke.CAP_SQUARE,
					BasicStroke.JOIN_MITER, 10.0f, new float[] { 8.0f, 6.0f },
					0.0f));
			g2.drawOval(sx, sy, Math.abs(sx - ex), Math.abs(sy - ey));
			g2.drawRect(sx, sy, Math.abs(sx - ex), Math.abs(sy - ey));
		} else {
			g2.setStroke(new BasicStroke(strokeThickness));
			g2.setColor(strokeColor);
			g2.drawOval(sx, sy, Math.abs(sx - ex), Math.abs(sy - ey));
		}
	}

	@Override
	public ArrayList<Integer> getCoords() {
		ArrayList<Integer> coords = new ArrayList<Integer>();
		// TODO Auto-generated method stub
		coords.add(sx);
		coords.add(sy);
		coords.add(ex);
		coords.add(ey);
		return coords;
	}

	@Override
	public void selectGObject(boolean selected) {
		strokeColor = (selected)? Color.red : Color.black;	
	}

}
