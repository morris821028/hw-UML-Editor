package UMLComponent;

import java.awt.Graphics;
import java.awt.Point;

import UMLComponent.Port.PortInterface;

public interface UMLBasicInterface {

	public void addPort(PortInterface e);

	public PortInterface getClosestPort(Point e);

	public void removeAllPortInterface();

	public void removePortInterface(PortInterface e);

	public void setSelected(boolean b);

	public boolean getSelected();

	public boolean containsPoint(Point p);

	public void draw(Graphics g);

	public String getObjectName();

	public void setObjectName(String s);

	public void paint(Graphics g);

	public void highlight();
	
	public int getX();
	
	public int getY();
	
	public int getWidth();
	
	public int getHeight();
	
	public void setBounds(int x, int y, int width, int height);
	
	public void updateUI();
}
