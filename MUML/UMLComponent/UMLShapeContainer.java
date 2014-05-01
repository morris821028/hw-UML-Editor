package UMLComponent;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Component;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.RenderingHints;
import java.util.ArrayList;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.geom.Ellipse2D;
import java.awt.geom.Line2D;

import javax.swing.*;

import UMLComponent.Port.PortInterface;

import frame.MainFrame;

public abstract class UMLShapeContainer extends JPanel implements UMLShape {
	protected ArrayList<PortInterface> ports;
	protected ArrayList<UMLShape> umlShapes;
	protected ArrayList<UMLShapeContainer> umlContainers;
	protected UMLShapeContainer parent;
	protected boolean selected = false;

	public UMLShapeContainer() {
		ports = new ArrayList<PortInterface>();
		umlShapes = new ArrayList<UMLShape>();
		umlContainers = new ArrayList<UMLShapeContainer>();
		parent = null;
	}

	public void adjustByMouse(MouseEvent e) {

	}

	public void setSelected(boolean b) {
		this.selected = b;
	}

	public boolean getSelected() {
		return selected;
	}

	public void addPort(PortInterface e) {
		this.ports.add(e);
	}

	public PortInterface getClosestPort(Point e) {
		if (ports.size() == 0)
			return null;
		PortInterface ret = ports.get(0);
		Point p = ret.getLocation();
		int mndist = (e.x - p.x) * (e.x - p.x) + (e.y - p.y) * (e.y - p.y);
		for (int i = 1; i < ports.size(); i++) {
			p = ports.get(i).getLocation();
			int dist = (e.x - p.x) * (e.x - p.x) + (e.y - p.y) * (e.y - p.y);
			if (dist < mndist) {
				mndist = dist;
				ret = ports.get(i);
			}
		}
		return ret;
	}

	public void removeAllPortInterface() {
		ports.clear();
	}

	public void removePortInterface(PortInterface e) {
		ports.remove(e);
	}

	public void addUMLShapeContainers(UMLShapeContainer[] shapes) {
		if (shapes == null || shapes.length == 0)
			return;
		int lx = 0, ly = 0, rx = 0, ry = 0;
		if (umlShapes.size() == 0) {
			UMLShape us = shapes[0];
			Rectangle rt = us.getBounds();
			lx = rt.x;
			ly = rt.y;
			rx = lx + rt.width;
			ry = ly + rt.height;
		} else {
			lx = this.getX();
			ly = this.getY();
			rx = lx + this.getWidth();
			ry = ly + this.getHeight();
			this.setLocation(lx, ly);
		}
		for (int i = 0; i < shapes.length; i++) {
			UMLShape us = shapes[i];
			Rectangle rt = us.getBounds();
			lx = Math.min(lx, rt.x);
			ly = Math.min(ly, rt.y);
			rx = Math.max(rx, rt.x + rt.width);
			ry = Math.max(ry, rt.y + rt.height);
		}
		lx -= 20;
		ly -= 20;
		int vx = this.getX() - lx, vy = this.getY() - ly;
		Component[] c = this.getComponents();
		for (int i = 0; i < c.length; i++) {
			int ox = c[i].getX(), oy = c[i].getY();
			c[i].setLocation(ox + vx, oy + vy);
		}
		for (int i = 0; i < shapes.length; i++) {
			UMLShapeContainer us = shapes[i];
			this.add(us);
			int ox = us.getX(), oy = us.getY();
			// UMLUtilities.getConainterAncestor(this).remove(us);
			us.setLocation(ox - lx, oy - ly);
			us.setContainerParent(this);
			this.umlContainers.add(us);
			us.setSelected(false);
		}
		this.setBounds(lx, ly, rx - lx + 20, ry - ly + 20);
		this.updateUI();
	}

	public UMLShape[] getShapes() {
		ArrayList<UMLShape> buffer = new ArrayList<UMLShape>();
		for (int i = 0; i < umlContainers.size(); i++) {
			UMLShape g[] = umlContainers.get(i).getShapes();
			for (int j = 0; j < g.length; j++)
				buffer.add(g[j]);
		}
		UMLShape[] ret = new UMLShape[umlShapes.size() + umlContainers.size()
				+ buffer.size()];
		for (int i = 0; i < umlShapes.size(); i++)
			ret[i] = umlShapes.get(i);
		for (int i = 0; i < umlContainers.size(); i++)
			ret[i + umlShapes.size()] = umlContainers.get(i);
		for (int i = 0; i < buffer.size(); i++)
			ret[i + umlShapes.size() + umlContainers.size()] = buffer.get(i);
		return ret;
	}

	public void addUMLShape(UMLShape e) {
		removeUMLShape(e);
		umlShapes.add(e);
		e.setContainerParent(this);
	}

	public void removeUMLShape(UMLShape e) {
		for (int i = 0; i < umlShapes.size(); i++) {
			if (umlShapes.get(i) == e) {
				umlShapes.remove(i);
			}
		}
	}

	public void addUMLShapeContainer(UMLShapeContainer e) {
		removeUMLShapeContainer(e);
		umlContainers.add(e);
		e.setContainerParent(this);
	}

	public UMLShapeContainer[] getShapeContainers() {
		UMLShapeContainer[] ret = new UMLShapeContainer[umlContainers.size()];
		for (int i = 0; i < umlContainers.size(); i++)
			ret[i] = umlContainers.get(i);
		return ret;
	}

	public void removeUMLShapeContainer(UMLShapeContainer e) {
		for (int i = 0; i < umlContainers.size(); i++) {
			if (umlContainers.get(i) == e) {
				umlContainers.remove(i);
				e.setContainerParent(null);
			}
		}
	}

	public UMLShape getShapeAt(Point p) {
		UMLShape select = null;
		for (int i = 0; i < umlShapes.size(); i++) {
			UMLShape us = umlShapes.get(i);
			Point rp = p;
			if (us.containsPoint(rp) && select == null) {
				select = us;
				break;
			}
		}
		for (int i = 0; i < umlContainers.size(); i++) {
			UMLShapeContainer us = umlContainers.get(i);
			Point rp = p;
			rp = SwingUtilities.convertPoint(this, p, us);
			if (us.containsPoint(rp) && select == null) {
				select = us.getShapeAt(rp);
				if (select != null)
					return select;
				return us;
			}
		}
		return select;
	}

	public UMLShape[] getShapeIn(Rectangle rect) {
		ArrayList<UMLShape> ret = new ArrayList<UMLShape>();
		for (int i = 0; i < umlShapes.size(); i++) {
			UMLShape us = umlShapes.get(i);
			int h, w;
			w = us.getWidth();
			h = us.getHeight();
			Point top_left = new Point(us.getX(), us.getY());
			if (us instanceof Component) {
				top_left = SwingUtilities.convertPoint((Component) us,
						new Point(0, 0), this);
			}
			Rectangle r = new Rectangle(top_left.x, top_left.y, w, h);
			if (rect.contains(r))
				ret.add(us);
		}
		for (int i = 0; i < umlContainers.size(); i++) {
			UMLShape us = umlContainers.get(i);
			int h, w;
			w = us.getWidth();
			h = us.getHeight();
			Point top_left = new Point(us.getX(), us.getY());
			if (us instanceof Component) {
				top_left = SwingUtilities.convertPoint((Component) us,
						new Point(0, 0), this);
			}
			Rectangle r = new Rectangle(top_left.x, top_left.y, w, h);
			if (rect.contains(r))
				ret.add(us);
		}
		UMLShape[] rett = new UMLShape[ret.size()];
		for (int i = 0; i < ret.size(); i++)
			rett[i] = ret.get(i);
		return rett;
	}

	public UMLShapeContainer getContainerParent() {
		return parent;
	}

	public void setContainerParent(UMLShapeContainer p) {
		this.parent = p;
	}

	public void paint(Graphics g) {
		super.paint(g);
		for (int i = 0; i < ports.size(); i++) {
			PortInterface e = ports.get(i);
			e.draw(g);
		}
		if (umlShapes != null) {
			for (int i = 0; i < umlShapes.size(); i++) {
				UMLShape e = umlShapes.get(i);
				e.paint(g);
			}
		}
	}

	protected void paintComponent(Graphics g) {
		super.paintComponent(g);
		Graphics2D g2 = (Graphics2D) g;
		/*g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
				RenderingHints.VALUE_ANTIALIAS_ON);
		g2.setStroke(new BasicStroke(4f));
		g2.setPaint(Color.blue);
		g2.drawRect(0, 0, 10, 10);
		g2.create().drawRect(20, 20, 10, 10);*/
		/*for (int i = 0; i < ports.size(); i++) {
			PortInterface e = ports.get(i);
			e.draw(g2.create());
			System.out.println("create");
		}
		g2.create().drawRect(30, 30, 10, 10);*/
	}
}
