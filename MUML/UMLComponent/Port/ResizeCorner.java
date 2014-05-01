package UMLComponent.Port;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.event.*;

import javax.swing.SwingUtilities;
import java.awt.*;
import javax.swing.event.*;

import UMLComponent.UMLShapeContainer;
import UMLComponent.UMLUtilities;
import UMLComponent.Line.DirectLine;

import frame.MainFrame;
import include.*;

import java.awt.Cursor;
import java.awt.event.MouseEvent;
import java.util.ArrayList;

public class ResizeCorner extends OvalButton implements PortInterface,
		MouseInputListener {

	Port port;
	private Point prevMouseAt;

	public ResizeCorner(int layout, UMLShapeContainer p) {
		port = new Port(layout, p);
		this.setSize(10, 10);
		this.setVisible(false);
		if (port.getPortLayout() == Port.TOP_LEFT
				|| port.getPortLayout() == Port.BOTTOM_RIGHT)
			this.setCursor(Cursor.getPredefinedCursor(Cursor.NW_RESIZE_CURSOR));
		else if (port.getPortLayout() == Port.TOP_RIGHT
				|| port.getPortLayout() == Port.BOTTOM_LEFT)
			this.setCursor(Cursor.getPredefinedCursor(Cursor.SW_RESIZE_CURSOR));
		else if (port.getPortLayout() == Port.NORTH
				|| port.getPortLayout() == Port.SOUTH)
			this.setCursor(Cursor.getPredefinedCursor(Cursor.N_RESIZE_CURSOR));
		else if (port.getPortLayout() == Port.EAST
				|| port.getPortLayout() == Port.WEST)
			this.setCursor(Cursor.getPredefinedCursor(Cursor.W_RESIZE_CURSOR));
		this.addMouseListener(this);
		this.addMouseMotionListener(this);
	}

	public Point getLocation() {
		return port.getLocation();
	}

	public UMLShapeContainer getUMLShapeContainer() {
		return port.getUMLShapeContainer();
	}

	public void addDirectLine(DirectLine d) {
		port.addDirectLine(d);
	}

	@Override
	public void draw(Graphics g) {
		if (port.getUMLShapeContainer().getSelected() == false) {
			this.setVisible(false);
			return;
		} else {
			this.setVisible(true);
		}
		Point p = this.port.getDrawLocation();
		p = UMLUtilities.convertPoint(port.getUMLShapeContainer(), p,
				UMLUtilities.getConainterAncestor(port.getUMLShapeContainer()));
		this.setLocation(p);
	}
	@Override 
	public void paint(Graphics g) {
		super.paint(g);
	}

	@Override
	public void highlight() {
		port.highlight();
	}

	public void mouseMoved(MouseEvent e) {
	}

	public void mouseDragged(MouseEvent e) {
		Point p = e.getPoint();
		Point[] XYweight = { new Point(1, 1), new Point(0, 1), new Point(1, 0),
				new Point(0, 0), new Point(0, 1), new Point(0, 0),
				new Point(0, 0), new Point(1, 0) };
		Point[] WHweight = { new Point(-1, -1), new Point(1, -1),
				new Point(-1, 1), new Point(1, 1), new Point(0, -1),
				new Point(0, 1), new Point(1, 0), new Point(-1, 0) };
		int[] layout = { Port.TOP_LEFT, Port.TOP_RIGHT, Port.BOTTOM_LEFT,
				Port.BOTTOM_RIGHT, Port.NORTH, Port.SOUTH, Port.EAST, Port.WEST };
		for (int i = 0; i < layout.length; i++) {
			if (layout[i] == port.getPortLayout()) {
				int vx = p.x - prevMouseAt.x;
				int vy = p.y - prevMouseAt.y;
				UMLShapeContainer bo = port.getUMLShapeContainer();
				bo.setBounds(bo.getX() + vx * XYweight[i].x, bo.getY() + vy
						* XYweight[i].y, bo.getWidth() + vx * WHweight[i].x,
						bo.getHeight() + vy * WHweight[i].y);
				bo.updateUI();
			}
		}
	}

	public void mouseReleased(MouseEvent e) {
		prevMouseAt = null;
	}

	public void mouseEntered(MouseEvent e) {
	}

	public void mouseExited(MouseEvent e) {
	}

	public void mouseClicked(MouseEvent e) {
	}

	public void mousePressed(MouseEvent e) {
		prevMouseAt = e.getPoint();
	}

	@Override
	public void removeDirectLine(DirectLine e) {
		port.removeDirectLine(e);
	}

	@Override
	public void immediateFinalize() {
		DirectLine d[] = port.getDirectLines();
		for (int i = 0; i < d.length; i++) {
			d[i].immediateFinalize();
		}
		this.getParent().remove(this);
	}
}
