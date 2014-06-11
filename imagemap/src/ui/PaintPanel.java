package ui;


import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionAdapter;
import java.awt.image.BufferedImage;
import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;

import javax.imageio.ImageIO;
import javax.swing.JPanel;

import draw.GraphObject;
import draw.Oval;
import draw.Poly;
import draw.Rectangle;


public class PaintPanel extends JPanel {
	/**
	 * 
	 */
	private static final long serialVersionUID = 6315814006322347966L;
	private ArrayList<GraphObject> paintList = new ArrayList<GraphObject>();
	GraphObject currentObject;
	SelectedItem sItem;
	private File file;
	private File fImage;
	private BufferedImage bImage;
	boolean drawingPoly = false;
	final MainFrame mainFrame;

	public PaintPanel(MainFrame mf) {
		super();
		sItem = SelectedItem.oval;
		this.mainFrame = mf;

	}

	public void paintElement() {
		this.addMouseListener(new MouseAdapter() {

			public void mousePressed(MouseEvent e) {
				switch (sItem) {
				case rect:
					currentObject = new Rectangle();
					break;
				case oval:
					currentObject = new Oval();
					break;
				case polygon:
					drawPolygon(e);
					return;
				}

				currentObject.setStartX(e.getX());
				currentObject.setStartY(e.getY());

			}

			public void mouseReleased(MouseEvent e) {
				if (sItem == SelectedItem.oval || sItem == SelectedItem.rect) {
					currentObject.setEndX(e.getX());
					currentObject.setEndY(e.getY());
					repaint();
					paintList.add(currentObject);
					currentObject = null;
					mainFrame.itemDrawn(sItem);
				}
			}
		});

		this.addMouseMotionListener(new MouseMotionAdapter() {
			public void mouseDragged(MouseEvent e) {
				if (sItem == SelectedItem.oval || sItem == SelectedItem.rect) {
					currentObject.setEndX(e.getX());
					currentObject.setEndY(e.getY());
					repaint();
				}
			}
		});

	}

	public void drawPolygon(MouseEvent e) {
		if (e.getButton() == 1) {
			if (drawingPoly == false) {
				currentObject = new Poly();
				drawingPoly = true;
			} else {
				currentObject.setEndX(e.getX());
				currentObject.setEndY(e.getY());
				((Poly) currentObject).setDot();
				repaint();
			}

		} else {
			repaint();
			paintList.add(currentObject);
			currentObject = null;
			drawingPoly = false;
			mainFrame.itemDrawn(sItem);
		}
	}

	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		g.drawImage(bImage, 0, 0, null);
		Graphics2D g2D = (Graphics2D) g;
		if (currentObject != null) {
			currentObject.paint(g2D, false);
		}
		for (GraphObject gObject : paintList) {
			gObject.paint(g2D, true);
		}
	}

	public void openImage() {
		try {
			bImage = ImageIO.read(fImage);
			repaint();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public void saveFile() {
		FileOutputStream fOutStream;
		BufferedOutputStream bOutStream;
		ObjectOutputStream objOutStream;
		try {
			fOutStream = new FileOutputStream(file);
			bOutStream = new BufferedOutputStream(fOutStream);

			try {
				objOutStream = new ObjectOutputStream(bOutStream);

				objOutStream.writeObject(paintList);
				objOutStream.close();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public void openFile() throws ClassNotFoundException {
		FileInputStream fInStream;
		BufferedInputStream bInStream;
		ObjectInputStream objInStream;
		try {
			fInStream = new FileInputStream(file);
			bInStream = new BufferedInputStream(fInStream);

			try {
				objInStream = new ObjectInputStream(bInStream);

				paintList = (ArrayList<GraphObject>) objInStream.readObject();
				objInStream.close();
				repaint();

			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	public void emptyList() {
		paintList.clear();
		repaint();
	}

	public void setsItem(SelectedItem sItem) {
		this.sItem = sItem;
	}

	public ArrayList<GraphObject> getPaintList() {
		return paintList;
	}

	public File getFile() {
		return file;
	}

	public void setFile(File file) {
		this.file = file;
	}

	public void setImage(File img) {
		this.fImage = img;
	}

}
