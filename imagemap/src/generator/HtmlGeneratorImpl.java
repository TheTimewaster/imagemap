package generator;


import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.List;

import javax.imageio.ImageIO;

import draw.GraphObject;
import draw.Oval;
import draw.Rectangle;


public class HtmlGeneratorImpl implements HtmlGenerator {

	private final String htmlHeader = "<!DOCTYPE HTML PUBLIC '-//W3C//DTD HTML 4.01//EN'\n'http://www.w3.org/TR/html4/strict.dtd'>";
	private final String htmlTag = "\n<html>\n<head>\n<meta http-equiv='content-type' content='text/html; charset=ISO-8859-1'>\n<title>Verweis-sensitive Grafiken definieren - map, area, usemap</title>\n</head>\n<body>";
	private final String header = "\n<h1>ImageMap</h1>";
	private String areas = null;

	@Override
	public String generateHTMLCode(File image, List<GraphObject> paintlist) {
		// TODO Auto-generated method stub
		BufferedImage realImage = null;
		try {
			realImage = ImageIO.read(image);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		String img = "\n<img src='" + image.getAbsolutePath() + "' width='"
				+ realImage.getWidth() + "' height='"
				+ realImage.getHeight()
				+ "' border='0' alt='Karte' usemap='#imgmap'>";

		String mapTag = null;

		for (GraphObject gObj : paintlist) {
			String temp = null;
			if (gObj instanceof Oval) {
				temp = "\n<area shape = 'oval' coords='";
			} else {
				if (gObj instanceof Rectangle) {
					temp = "\n<area shape = 'rect' coords='";
				}
			}

			temp += gObj.getCoords()[0]
					+ " , "
					+ gObj.getCoords()[1]
					+ " , "
					+ gObj.getCoords()[2]
					+ " , "
					+ gObj.getCoords()[3]
					+ "' href='http://somelink.somewhere' alt='some Alt' title = 'some Title' />";

			areas += temp;
		}

		mapTag = "\n<map name='imgmap'>" + areas + "\n</map>\n";

		return htmlHeader + htmlTag + header + img + mapTag
				+ "</body>\n</html>";
	}

}
