package draw;


import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics2D;
import java.io.Serializable;
import java.util.ArrayList;


public class Rectangle implements GraphObject, Serializable {

	private Color strokeColor = Color.black;
	private final int strokeThickness = 2;
	private static final long serialVersionUID = 6266248648024696224L;
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
		int diffX = sx - ex;
		int diffY = sy - ey;
		int tempx = ex;
		int tempy = ey;
		if (tmp == false) {
			// float[] dash = { 10, 2 };
			g2.setStroke(new BasicStroke(1, BasicStroke.CAP_SQUARE,
					BasicStroke.JOIN_MITER, 10.0f, new float[] { 8.0f, 6.0f },
					0.0f));
			if (diffX < 0 && diffY < 0) {
				ex = sx;
				ey = sy;
			}
			g2.drawRect(sx - Math.abs(sx - ex), sy - Math.abs(sy - ey),
					Math.abs(diffX), Math.abs(diffY));
		} else {
			if (diffX < 0 && diffY < 0) {
				ex = sx;
				ey = sy;
			}
			g2.setStroke(new BasicStroke(strokeThickness));
			g2.setColor(strokeColor);
			g2.drawRect(sx - Math.abs(sx - ex), sy - Math.abs(sy - ey),
					Math.abs(diffX), Math.abs(diffY));
			ex = tempx;
			ey = tempy;

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
