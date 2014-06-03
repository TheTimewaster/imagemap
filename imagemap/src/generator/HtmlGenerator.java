package generator;

import java.awt.image.BufferedImage;
import java.io.File;
import java.util.List;

import draw.GraphObject;

public interface HtmlGenerator {
	
	public String generateHTMLCode(File img,List<GraphObject> paintlist);

}
