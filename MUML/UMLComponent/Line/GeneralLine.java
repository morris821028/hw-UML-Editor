package UMLComponent.Line;

import java.awt.Graphics2D;
import java.awt.Point;
import java.util.ArrayList;

import UMLComponent.Port.PortInterface;

public class GeneralLine extends DirectLine {
	public GeneralLine(ArrayList<Point> points) {
		super(points);
	}

	public GeneralLine(Point p1, Point p2) {
		super(p1, p2);
	}
	public GeneralLine(PortInterface s, PortInterface e) {
		super(s, e);
		this.setHeaderSize(20);
	}
	@Override
	public void drawHeader(Graphics2D g2d, Point s, Point e) {
		double nx, ny;
		nx = (s.x - e.x) / Math.hypot(s.x - e.x, s.y - e.y);
		ny = (s.y - e.y) / Math.hypot(s.x - e.x, s.y - e.y);
		int c1x, c1y, c2x, c2y;
		c1x = s.x + (int) (ny * 15);
		c1y = s.y + (int) (-nx * 15);
		c2x = s.x + (int) (-ny * 15);
		c2y = s.y + (int) (nx * 15);
		g2d.drawLine(s.x, s.y, e.x, e.y);
		g2d.drawLine(c1x, c1y, e.x, e.y);
		g2d.drawLine(c2x, c2y, e.x, e.y);
	}

	@Override
	public String getDisplayName() {
		return "GENERAL_LINE";
	}
}
