package UMLComponent.Port;

import javax.swing.*;

import frame.MainFrame;

import UMLComponent.UMLShapeContainer;
import UMLComponent.UMLUtilities;
import UMLComponent.Line.DirectLine;

import java.awt.*;
import java.util.*;

public class Port implements PortInterface {
	public final static int NORTH = 0;
	public final static int EAST = 1;
	public final static int WEST = 2;
	public final static int SOUTH = 3;
	public final static int RELATIVE = 4;
	public final static int TOP_LEFT = 5;
	public final static int TOP_RIGHT = 6;
	public final static int BOTTOM_LEFT = 7;
	public final static int BOTTOM_RIGHT = 8;
	public final static int[] NORMAL_LAYOUT = { 0, 1, 2, 3 };
	public final static int[] CORNER_LAYOUT = { 5, 6, 7, 8 };
	private int rx, ry;
	private int layout;
	private UMLShapeContainer parent = null;
	private ArrayList<DirectLine> lines = new ArrayList<DirectLine>();

	public Port(int x, int y, UMLShapeContainer p) {
		rx = x;
		ry = y;
		this.layout = Port.RELATIVE;
		this.parent = p;
	}

	public Port(int layout, UMLShapeContainer p) {
		this.layout = layout;
		this.parent = p;
	}

	public int getPortLayout() {
		return layout;
	}

	public Point getLocation() {
		if (layout == Port.RELATIVE)
			return new Point(rx, ry);
		if (layout == Port.NORTH)
			return new Point(parent.getWidth() / 2, 0);
		if (layout == Port.EAST)
			return new Point(parent.getWidth(), parent.getHeight() / 2);
		if (layout == Port.WEST)
			return new Point(0, parent.getHeight() / 2);
		if (layout == Port.SOUTH)
			return new Point(parent.getWidth() / 2, parent.getHeight());
		if (layout == Port.TOP_LEFT)
			return new Point(0, 0);
		if (layout == Port.TOP_RIGHT)
			return new Point(parent.getWidth(), 0);
		if (layout == Port.BOTTOM_LEFT)
			return new Point(0, parent.getHeight());
		if (layout == Port.BOTTOM_RIGHT)
			return new Point(parent.getWidth(), parent.getHeight());
		return new Point(100, 100);
	}

	public Point getDrawLocation() {
		Point p = this.getLocation();
		int sizepx = 10;
		if (layout == Port.RELATIVE) {
		} else if (layout == Port.NORTH) {
			p.y -= sizepx;
			p.x -= sizepx / 2;
		} else if (layout == Port.EAST) {
			p.y -= sizepx / 2;
		} else if (layout == Port.WEST) {
			p.x -= sizepx;
			p.y -= sizepx / 2;
		} else if (layout == Port.SOUTH) {
			p.x -= sizepx / 2;
		} else if (layout == Port.TOP_LEFT) {
			p.y -= sizepx;
			p.x -= sizepx;
		} else if (layout == Port.TOP_RIGHT) {
			p.y -= sizepx;
		} else if (layout == Port.BOTTOM_LEFT) {
			p.x -= sizepx;
		} else if (layout == Port.BOTTOM_RIGHT) {
		}
		return p;
	}

	public UMLShapeContainer getUMLShapeContainer() {
		return this.parent;
	}

	public DirectLine[] getDirectLines() {
		DirectLine[] ret = new DirectLine[lines.size()];
		for (int i = 0; i < lines.size(); i++)
			ret[i] = lines.get(i);
		return ret;
	}

	public void addDirectLine(DirectLine d) {
		this.lines.add(d);
	}

	public void draw(Graphics g) {
		if (this.parent.getSelected()) {
			g.setColor(Color.black);
			Graphics2D g2d = (Graphics2D) g;
			g2d.setPaint(Color.BLACK);
			g2d.setStroke(new BasicStroke(3.0f));
			Point p = this.getDrawLocation();
			p.x += parent.getX();
			p.y += parent.getY();
			int sizepx = 10;
			/*UMLUtilities.getConainterAncestor(getUMLShapeContainer())
					.getGraphics().drawRect(rp.x, rp.y, sizepx, sizepx);*/
			 System.out.println("xx" + p + layout);
			g2d.drawRect(p.x, p.y, sizepx, sizepx);
		}
		for (int i = 0; i < lines.size(); i++) {
			DirectLine d = lines.get(i);
			d.paint(g);
		}
	}

	public void highlight() {
		DirectLine lines[] = getDirectLines();
		for (int i = 0; i < lines.length; i++) {
			lines[i].setDisplayType(DirectLine.LINE_HIGHLIGHT);
			lines[i].paint(UMLUtilities.getConainterAncestor(
					this.getUMLShapeContainer()).getGraphics());
		}
	}

	@Override
	public void removeDirectLine(DirectLine e) {
		for (int i = 0; i < lines.size(); i++) {
			DirectLine d = lines.get(i);
			if (d == e)
				lines.remove(i);
		}
	}

	@Override
	public void immediateFinalize() {
		DirectLine d[] = this.getDirectLines();
		for (int i = 0; i < d.length; i++) {
			d[i].immediateFinalize();
		}
		lines.clear();
	}
}
