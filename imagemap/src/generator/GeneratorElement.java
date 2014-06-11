package generator;


import java.util.ArrayList;

import draw.GraphObject;
import draw.Oval;
import draw.Rectangle;


public class GeneratorElement {

	private GraphObject gObject;
	private ArrayList<Integer> coords;
	private String href;
	private String alt;
	private String title;

	public GeneratorElement(GraphObject gObj, ArrayList<Integer> c, String h,
			String a, String t) {
		this.gObject = gObj;
		this.coords = c;
		this.href = ("".equals(h)) ? "#" : h;
		this.alt = ("".equals(a)) ? "no Alt" : a;
		this.title = ("".equals(t)) ? "no Title" : t;
	}

	public String printGObject() {
		if (gObject instanceof Rectangle) {
			return "rect";
		} else {
			if (gObject instanceof Oval) {
				return "oval";
			} else {
				return "poly";
			}
		}
	}

	public GraphObject getGObjClass() {
		return gObject;
	}

	public String printCoords() {
		String coordsString = "";
		for (Integer c : coords) {
			coordsString += c;
			if (coords.indexOf(c) < coords.size() - 1) {
				coordsString += ",";
			}
		}

		return coordsString;
	}

	public String printhref() {
		return href;
	}

	public String printalt() {
		return alt;
	}

	public String printtitle() {
		return title;
	}
}
