package UMLComponent.Line;

import java.awt.Point;
import java.util.ArrayList;

import UMLComponent.Port.PortInterface;

public class AssociationLine extends DirectLine {
	public AssociationLine(ArrayList<Point> points) {
		super(points);
	}

	public AssociationLine(Point p1, Point p2) {
		super(p1, p2);
	}

	public AssociationLine(PortInterface s, PortInterface e) {
		super(s, e);
	}

	@Override
	public String getDisplayName() {
		return "ASSOCIATION_LINE";
	}
}
