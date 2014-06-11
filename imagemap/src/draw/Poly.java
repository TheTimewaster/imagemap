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
	
	public Poly(){
		coordYList = new ArrayList<Integer>();
		coordXList = new ArrayList<Integer>();
	}

	@Override
	public void paint(Graphics2D g2, boolean tmp) {
		
		// TODO Auto-generated method stub
		if (tmp == false) {		
			g2.setStroke(new BasicStroke(strokeThickness));
			if(coordXList.size() < 2){
				g2.drawLine(coordXList.get(0), coordYList.get(0), coordXList.get(0), coordYList.get(0));
			}else{
				for(int i = 0 ; i < coordXList.size()-1 ; i++ ){
					g2.drawLine(coordXList.get(i), coordYList.get(i), coordXList.get(i+1), coordYList.get(i+1));
					g2.drawPolygon(integerListtoArray(coordXList), integerListtoArray(coordYList), coordXList.size());
				}
			}
			
		} else {
			g2.setStroke(new BasicStroke(strokeThickness));
			g2.drawPolygon(integerListtoArray(coordXList), integerListtoArray(coordYList), coordXList.size());
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
	public ArrayList<Integer> getCoords() {
		// TODO Auto-generated method stub
		ArrayList<Integer> coords = new ArrayList<Integer>();
		for(int i = 0 ; i < coordXList.size() ; i++){
			coords.add(coordXList.get(i));
			coords.add(coordYList.get(i));
		}		
		return coords;
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
	
	public void setDot(){
		this.coordXList.add(newX);
		this.coordYList.add(newY);
	}
	
}
