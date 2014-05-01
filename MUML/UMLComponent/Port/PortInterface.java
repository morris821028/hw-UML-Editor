package UMLComponent.Port;

import java.awt.Graphics;
import java.awt.Point;

import UMLComponent.UMLShapeContainer;
import UMLComponent.Line.DirectLine;

public interface PortInterface {
	public Point getLocation();

	public UMLShapeContainer getUMLShapeContainer();

	public void addDirectLine(DirectLine d);

	public void draw(Graphics g);
	
	public void highlight();
	
	public void removeDirectLine(DirectLine e);
	
	public void immediateFinalize();
}
