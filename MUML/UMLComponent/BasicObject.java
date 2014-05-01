package UMLComponent;

import javax.swing.*;

import UMLComponent.Port.PortInterface;

import java.awt.*;
import java.util.*;

public class BasicObject extends UMLShapeContainer {
	private String objectName = "BasicObject";

	public BasicObject() {
		ports = new ArrayList<PortInterface>();
		this.setSize(180, 180);
		this.setBackground(Color.ORANGE);
	}

	public boolean containsPoint(Point p) {
		if (p.x > 0 && p.y > 0 && p.x < this.getWidth()
				&& p.y < this.getHeight())
			return true;
		return false;
	}

	public String getName() {
		return this.objectName;
	}

	public String getTypeName() {
		return "BASIC_OBJECT";
	}

	public void setName(String s) {
		this.objectName = s;
	}

	@Override
	public void highlight() {
		for (int i = 1; i < ports.size(); i++) {
			ports.get(i).highlight();
		}
	}

	@Override
	public void immediateFinalize() {
		for (int i = 0; i < ports.size(); i++) {
			PortInterface e = ports.get(i);
			e.immediateFinalize();
		}
		ports.clear();
		this.getParent().remove(this);
	}
}
