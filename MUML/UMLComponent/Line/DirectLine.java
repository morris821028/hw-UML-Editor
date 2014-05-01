package UMLComponent.Line;

import java.awt.*;
import java.awt.event.*;
import java.util.*;

import javax.swing.JOptionPane;
import javax.swing.SwingUtilities;

import UMLComponent.UMLShape;
import UMLComponent.UMLShapeContainer;
import UMLComponent.UMLUtilities;
import UMLComponent.Port.PortInterface;

import frame.MainFrame;

public class DirectLine implements UMLShape {
	public final static int LINE_DEFAULT = 0;
	public final static int LINE_HIGHLIGHT = 1;
	public final static int LINE_NORMAL = 2;
	public final static int LINE_PREVIEW = 3;

	private ArrayList<Point> points = new ArrayList<Point>();
	private PortInterface startPortInterface, endPortInterface;
	private boolean selected;
	private int pointsize = 10;
	private int headsize = 20;
	private int displayType = LINE_DEFAULT;
	private String lineName = "";
	private UMLShapeContainer parent;

	public DirectLine() {
		this.points.add(new Point());
	}

	public DirectLine(ArrayList<Point> points) {
		this.points = points;
	}

	public DirectLine(Point p1, Point p2) {
		this.points.add(p1);
		this.points.add(p2);
	}

	public DirectLine(PortInterface s, PortInterface e) {
		this.points.add(s.getLocation());
		this.points.add(e.getLocation());
		this.startPortInterface = s;
		this.endPortInterface = e;
	}

	public void setStartPortInterface(PortInterface p) {
		this.startPortInterface = p;
	}

	public void setEndPortInterface(PortInterface p) {
		this.endPortInterface = p;
	}

	public void setSelected(boolean b) {
		this.selected = b;
	}

	public boolean getSelected() {
		return selected;
	}

	public void setDisplayType(int val) {
		this.displayType = val;
	}

	public boolean containsPoint(Point p) {
		for (int i = 0; i < points.size() - 1; i++) {
			Point s = points.get(i);
			Point e = points.get(i + 1);
			int area = Math.abs((s.x - p.x) * (e.y - p.y) - (s.y - p.y)
					* (e.x - p.x)) / 2;
			double a = Math.hypot(s.x - e.x, s.y - e.y);
			double b = Math.hypot(s.x - p.x, s.y - p.y);
			double c = Math.hypot(p.x - e.x, p.y - e.y);
			double h = 2 * area / a;
			if (h < 10 && b * b < a * a + c * c && c * c < b * b + a * a)
				return true;
		}
		return getPointMouseAt(p) != null;
	}

	protected Point getPointMouseAt(Point p) {
		for (int i = 0; i < points.size(); i++) {
			Point s = points.get(i);
			if (Math.hypot(p.x - s.x, p.y - s.y) < 20)
				return s;
		}
		return null;
	}

	protected int getLineMouseAt(Point p) {
		for (int i = 0; i < points.size() - 1; i++) {
			Point s = points.get(i);
			Point e = points.get(i + 1);
			int area = Math.abs((s.x - p.x) * (e.y - p.y) - (s.y - p.y)
					* (e.x - p.x)) / 2;
			double a = Math.hypot(s.x - e.x, s.y - e.y);
			double b = Math.hypot(s.x - p.x, s.y - p.y);
			double c = Math.hypot(p.x - e.x, p.y - e.y);
			double h = 2 * area / a;
			if (h < 10 && b * b < a * a + c * c && c * c < b * b + a * a)
				return i;
		}
		return -1;
	}

	public void adjustByMouse(MouseEvent e) {
		if (e.getClickCount() == 2) {
			if (this.selected) {
				String text = JOptionPane.showInputDialog(null,
						"What's the name of line ? (building)");
				if (text != null && text.trim().length() > 0)
					lineName = text.trim();
			}
		} else {
			Point s = this.getPointMouseAt(e.getPoint());
			if (s != null) {
				s.x = e.getPoint().x;
				s.y = e.getPoint().y;
				this.adjustByAutoline();
			} else {
				int lineIndex = this.getLineMouseAt(e.getPoint());
				if (lineIndex >= 0) {
					points.add(lineIndex + 1, e.getPoint());
				}
			}
		}
		UMLUtilities.getConainterAncestor(this).repaint();
	}

	public void drawHeader(Graphics2D g2d, Point s, Point e) {
		g2d.drawLine(s.x, s.y, e.x, e.y);
	}

	protected void setDisplayColor(Graphics2D g2d) {
		if (displayType == DirectLine.LINE_DEFAULT)
			return;
		if (displayType == DirectLine.LINE_HIGHLIGHT) {
			g2d.setStroke(new BasicStroke(5.0f));
			g2d.setColor(Color.BLACK);
		}
		if (displayType == DirectLine.LINE_NORMAL) {
			g2d.setStroke(new BasicStroke(5.0f));
			g2d.setColor(Color.GRAY);
		}
		if (displayType == DirectLine.LINE_PREVIEW) {
			g2d.setStroke(new BasicStroke(5.0f, BasicStroke.CAP_BUTT,
					BasicStroke.JOIN_MITER, 10.0f, new float[] { 10.0f }, 0.0f));
			g2d.setColor(Color.RED);
		}
	}

	protected void setHeaderSize(int px) {
		this.headsize = px;
	}

	protected void adjustByPortInterface() {
		if (startPortInterface == null || endPortInterface == null)
			return;
		Point s, e;
		/*
		 * s = SwingUtilities.convertPoint( (Component)
		 * startPortInterface.getUMLShapeContainer(),
		 * startPortInterface.getLocation(), MainFrame.canvasPanel); e =
		 * SwingUtilities.convertPoint( (Component)
		 * endPortInterface.getUMLShapeContainer(),
		 * endPortInterface.getLocation(), MainFrame.canvasPanel);
		 */
		s = UMLUtilities.convertPoint(
				startPortInterface.getUMLShapeContainer(),
				startPortInterface.getLocation(), UMLUtilities.getConainterAncestor(this));
		e = UMLUtilities.convertPoint(endPortInterface.getUMLShapeContainer(),
				endPortInterface.getLocation(), UMLUtilities.getConainterAncestor(this));
		Point a, b;
		a = this.points.get(0);
		a.setLocation(s);
		b = this.points.get(this.points.size() - 1);
		b.setLocation(e);
	}

	protected void adjustByAutoline() {// remove collinear points.
		for (int i = 0; i < points.size() - 2; i++) {
			Point s = points.get(i);
			Point p = points.get(i + 1);
			Point e = points.get(i + 2);
			int area = Math.abs((s.x - p.x) * (e.y - p.y) - (s.y - p.y)
					* (e.x - p.x)) / 2;
			double a = Math.hypot(s.x - e.x, s.y - e.y);
			double b = Math.hypot(s.x - p.x, s.y - p.y);
			double c = Math.hypot(p.x - e.x, p.y - e.y);
			double h = 2 * area / a;
			if (h < 10 && b * b < a * a + c * c && c * c < b * b + a * a)
				points.remove(i + 1);
		}
	}

	protected void adjustByHighlight() {// remove Highlight setting
		if (this.displayType == LINE_HIGHLIGHT) {
			if (this.startPortInterface == null
					|| this.endPortInterface == null) {
				this.displayType = LINE_NORMAL;
				return;
			}
			if (this.startPortInterface.getUMLShapeContainer().getSelected() == false
					&& this.endPortInterface.getUMLShapeContainer()
							.getSelected() == false) {
				this.displayType = LINE_NORMAL;
			}
		}
	}

	public void paint(Graphics g) {
		Graphics2D g2d = (Graphics2D) g;
		this.setDisplayColor(g2d);
		this.adjustByPortInterface();
		this.adjustByHighlight();
		for (int i = 0; i < points.size() - 1; i++) {
			Point s = points.get(i);
			Point e = points.get(i + 1);
			if (i == points.size() - 2) {
				double dist = Math.hypot(s.x - e.x, s.y - e.y);
				double ratio = headsize / dist;
				int mx, my;
				mx = (int) ((e.x - s.x) * (1 - ratio) + s.x);
				my = (int) ((e.y - s.y) * (1 - ratio) + s.y);
				g2d.drawLine(s.x, s.y, mx, my);
				this.drawHeader(g2d, new Point(mx, my), e);
			} else {
				g2d.drawLine(s.x, s.y, e.x, e.y);
			}
			if (this.selected) {
				for (int r = 8; r < pointsize; r++)
					g2d.drawOval(s.x - r, s.y - r, 2 * r, 2 * r);
			}
		}
		if (lineName.length() > 0) {
			Point s = points.get(0);
			Point e = points.get(1);
			g2d.drawString(lineName, (s.x + e.x) / 2 + 10, (s.y + e.y) / 2 + 10);
		}
	}

	public String getDisplayName() {
		return "LINE";
	}

	public String getName() {
		return lineName;
	}

	public String getTypeName() {
		return "DIRECT_LINE";
	}

	public void setName(String s) {
		this.lineName = s;
	}

	public void highlight() {

	}

	public Rectangle getBounds() {
		int lx, ly, rx, ry;
		lx = points.get(0).x;
		ly = points.get(0).y;
		rx = lx;
		ry = ly;
		for (int i = 0; i < points.size(); i++) {
			Point p = points.get(i);
			lx = Math.min(lx, p.x);
			rx = Math.max(rx, p.x);
			ly = Math.min(ly, p.y);
			ry = Math.max(ry, p.y);
		}
		return new Rectangle(lx, ly, rx - lx, ry - ly);
	}

	public void setBounds(int x, int y, int width, int height) {

	}

	public int getX() {
		int x = points.get(0).x;
		for (int i = 0; i < points.size(); i++)
			x = Math.min(x, points.get(i).x);
		return x;
	}

	public int getY() {
		int y = points.get(0).y;
		for (int i = 0; i < points.size(); i++)
			y = Math.min(y, points.get(i).y);
		return y;
	}

	public int getWidth() {
		int x = points.get(0).x;
		for (int i = 0; i < points.size(); i++)
			x = Math.max(x, points.get(i).x);
		return x - getX();
	}

	public int getHeight() {
		int y = points.get(0).y;
		for (int i = 0; i < points.size(); i++)
			y = Math.max(y, points.get(i).y);
		return y - getY();
	}

	@Override
	public void immediateFinalize() {
		if (startPortInterface != null)
			startPortInterface.removeDirectLine(this);
		if (endPortInterface != null)
			endPortInterface.removeDirectLine(this);
		if (parent != null)
			parent.removeUMLShape(this);
	}

	public UMLShapeContainer getContainerParent() {
		return parent;
	}

	public void setContainerParent(UMLShapeContainer p) {
		this.parent = p;
	}

	public PortInterface getClosestPort(Point e) {
		return null;
	}
}
