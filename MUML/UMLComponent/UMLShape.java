package UMLComponent;

import javax.swing.*;

import UMLComponent.Port.PortInterface;

import java.awt.*;
import java.awt.event.*;

public interface UMLShape {
	public void setSelected(boolean b);

	public boolean getSelected();

	public boolean containsPoint(Point p);

	public Rectangle getBounds();

	public void setBounds(int x, int y, int width, int height);

	public String getName();

	public void setName(String s);

	public String getTypeName();

	public void highlight();

	public void paint(Graphics g);

	public int getX();

	public int getY();

	public int getWidth();

	public int getHeight();

	public void immediateFinalize();

	public UMLShapeContainer getContainerParent();

	public void setContainerParent(UMLShapeContainer p);

	public void adjustByMouse(MouseEvent e);

	public PortInterface getClosestPort(Point e);
	
}
