package draw;


import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Polygon;
import java.io.Serializable;
import java.util.ArrayList;


public class Poly implements GraphObject, Serializable {

	private final Color strokeColor = Color.black;
	private final int strokeThickness = 2;
	private static final long serialVersionUID = 2075175270982188237L;
	private int firstX;
	private int firstY;
	private int newX;
	private int newY;
	private int lastX;
	private int lastY;
	private int length = 0;
	private int[] xCoords;
	private int[] yCoords;
	ArrayList<Integer> coordYList;
	ArrayList<Integer> coordXList;

	@Override
	public void paint(Graphics2D g2, boolean tmp) {
		coordYList = new ArrayList<Integer>();
		coordXList = new ArrayList<Integer>();
		// TODO Auto-generated method stub
		if (tmp == false) {
			boolean initPoly = true;
			
			g2.setStroke(new BasicStroke(1, BasicStroke.CAP_SQUARE,
					BasicStroke.JOIN_MITER, 10.0f, new float[] { 8.0f, 6.0f },
					0.0f));
			if(initPoly){
				g2.drawLine(firstX, firstY, newX, newY);
				initPoly = false;
				coordXList.add(firstX);
				coordYList.add(firstY);
			}else{
				g2.drawLine(lastX, lastY, newX, newY);
				g2.drawLine(newX, newY, firstX, firstY);
			}
			
			coordXList.add(newX);
			coordYList.add(newY);
			lastX = newX;
			lastY = newY;
			
			g2.drawPolygon(integerListtoArray(coordXList), integerListtoArray(coordYList), coordXList.size());
		} else {
			g2.setStroke(new BasicStroke(strokeThickness));
		}
	}

	@Override
	public void setStartX(int sx) {
		// TODO Auto-generated method stub
		this.firstX = sx;
	}

	@Override
	public void setStartY(int sy) {
		// TODO Auto-generated method stub
		this.firstY = sy;
	}

	@Override
	public void setEndX(int ex) {
		// TODO Auto-generated method stub
		this.newX = ex;
	}

	@Override
	public void setEndY(int ey) {
		// TODO Auto-generated method stub
		this.newY = ey;
	}

	@Override
	public int[] getCoords() {
		// TODO Auto-generated method stub
		return null;
	}
	
	public int[] integerListtoArray(ArrayList<Integer> list){
		int[] coords = new int[list.size()];
		int i = 0;
		for(Integer el : list){		
			coords[i] = el;
			i++;
		}
		return coords;
	}

}
