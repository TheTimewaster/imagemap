package draw;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Stroke;
import java.io.Serializable;

public class Rectangle implements GraphObject, Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 6266248648024696224L;
	private int sx;
	private int sy;
	private int ex;
	private int ey;
	private Color fillColor;
	private Color strokeColor;
	private int strokeThickness;

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
		int diffX = sx-ex;
		int diffY = sy-ey;
		int tempx = ex;
		int tempy = ey;
		if (tmp == false){
			//float[] dash = { 10, 2 };
			g2.setStroke(new BasicStroke( 1,
					  BasicStroke.CAP_SQUARE, BasicStroke.JOIN_MITER,
					  10.0f, new float[]{ 8.0f,6.0f }, 0.0f ));
			if (diffX < 0 && diffY < 0) {
				ex = sx;
				ey = sy;
			}
			g2.drawRect(sx - Math.abs(sx-ex), sy - Math.abs(sy-ey), Math.abs(diffX), Math.abs(diffY));
		}else{
			if (diffX < 0 && diffY < 0) {
				ex = sx;
				ey = sy;
			} 
			g2.setStroke(new BasicStroke(strokeThickness));
			g2.setColor(strokeColor);
			g2.drawRect(sx - Math.abs(sx-ex), sy - Math.abs(sy-ey), Math.abs(diffX), Math.abs(diffY));
			g2.setColor(fillColor);
			g2.fillRect(sx - Math.abs(sx-ex), sy - Math.abs(sy-ey), Math.abs(diffX), Math.abs(diffY));
			
			ex = tempx;
			ey = tempy;
			
		}
		
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

	@Override
	public int[] getCoords() {
		// TODO Auto-generated method stub
		int[] coords = new int[4];
		coords[0] = sx;
		coords[1] = sy;
		coords[2] = ex;
		coords[3] = ey;
		return coords;
	}

}
