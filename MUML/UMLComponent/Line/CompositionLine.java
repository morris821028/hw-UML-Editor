package UMLComponent.Line;

import java.awt.Graphics2D;
import java.awt.Point;
import java.util.ArrayList;

import UMLComponent.Port.PortInterface;

public class CompositionLine extends DirectLine {
	public CompositionLine(ArrayList<Point> points) {
		super(points);
		this.setHeaderSize(40);
	}

	public CompositionLine(Point p1, Point p2) {
		super(p1, p2);
		this.setHeaderSize(40);
	}
	public CompositionLine(PortInterface s, PortInterface e) {
		super(s, e);
		this.setHeaderSize(40);
	}
	@Override
	public void drawHeader(Graphics2D g2d, Point s, Point e) {
		double nx, ny;
		nx = (s.x - e.x) / Math.hypot(s.x - e.x, s.y - e.y);
		ny = (s.y - e.y) / Math.hypot(s.x - e.x, s.y - e.y);
		int c1x, c1y, c2x, c2y;
		c1x = (s.x + e.x)/2 + (int) (ny * 10);
		c1y = (s.y + e.y)/2 + (int) (-nx * 10);
		c2x = (s.x + e.x)/2 + (int) (-ny * 10);
		c2y = (s.y + e.y)/2 + (int) (nx * 10);
		g2d.drawLine(c1x, c1y, e.x, e.y);
		g2d.drawLine(c2x, c2y, e.x, e.y);
		g2d.drawLine(c1x, c1y, s.x, s.y);
		g2d.drawLine(c2x, c2y, s.x, s.y);
	}

	@Override
	public String getDisplayName() {
		return "COMPOSITION_LINE";
	}
}
