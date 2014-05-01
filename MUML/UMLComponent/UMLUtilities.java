package UMLComponent;

import java.awt.*;
import java.util.*;

public class UMLUtilities {
	public static Point convertPoint(UMLShape from, Point p, UMLShape to) {
		ArrayList<UMLShape> pass = new ArrayList<UMLShape>();
		Stack<UMLShape> stack = new Stack<UMLShape>();
		ArrayList<Point> passPt = new ArrayList<Point>();
		do {
			pass.add(from);
			passPt.add(p);
			p = new Point(p.x + from.getX(), p.y + from.getY());
			from = from.getContainerParent();
		} while (from != null);
		do {
			for (int i = 0; i < pass.size(); i++) {
				if (pass.get(i) == to) {
					Point x = passPt.get(i);
					while (!stack.empty()) {
						UMLShape e = stack.pop();
						x = new Point(x.x - e.getX(), x.y - e.getY());
					}
					return x;
				}
			}
			stack.push(to);
			to = to.getContainerParent();
		} while (to != null);
		return new Point();
	}

	public static UMLShapeContainer getConainterAncestor(UMLShape e) {
		UMLShapeContainer p = e.getContainerParent(), q = null;

		while (p != null) {
			q = p;
			p = p.getContainerParent();
		}
		return q;
	}
}
