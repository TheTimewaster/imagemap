package generator;


import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.List;

import javax.imageio.ImageIO;

import draw.GraphObject;
import draw.Oval;
import draw.Poly;
import draw.Rectangle;


public class HtmlGenerator {

	private final String htmlHeader = "<!DOCTYPE HTML PUBLIC '-//W3C//DTD HTML 4.01//EN'\n'http://www.w3.org/TR/html4/strict.dtd'>";
	private final String htmlTag = "\n<html>\n<head>\n<meta http-equiv='content-type' content='text/html; charset=ISO-8859-1'>\n<title>Verweis-sensitive Grafiken definieren - map, area, usemap</title>\n</head>\n<body>";
	private final String header = "\n<h1>ImageMap</h1>";
	private String areas = "";

	public String generateHTMLCode(File image, List<GeneratorElement> gElementList) {
		// TODO Auto-generated method stub
		BufferedImage realImage = null;
		int i = 0;
		try {
			realImage = ImageIO.read(image);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		String img = "\n<img src='" + image.getName() + "' width='"
				+ realImage.getWidth() + "' height='" + realImage.getHeight()
				+ "' alt='Karte' usemap='#imgmap'/>";

		String mapTag = null;

		for (GeneratorElement gElem : gElementList) {
			String temp = "";
			
			temp = "\n<area shape = '" + gElem.printGObject() + "' coords='" + gElem.printCoords() + "' href='" + gElem.printhref() + "' alt='"
					+ gElem.printalt() + "' title = '" + gElem.printtitle()
					+ "' />";;			
			
			areas += temp;
		}

		mapTag = "\n<map name='imgmap'>" + areas + "\n</map>\n";

		return htmlHeader + htmlTag + header + img + mapTag
				+ "</body>\n</html>";
	}

}
