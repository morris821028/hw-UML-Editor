package UMLComponent;

import java.awt.Color;
import java.awt.*;
import java.awt.Graphics;
import java.awt.Point;
import java.awt.Rectangle;
import java.util.ArrayList;
import javax.swing.*;
import javax.swing.border.LineBorder;

import UMLComponent.Port.PortInterface;

import frame.MainFrame;

import frame.*;

public class UMLGroup extends UMLShapeContainer implements UMLShape {
	private String groupName;

	public UMLGroup() {
		super();
		this.setLayout(null);
		this.setBackground(new Color(128, 128, 0));
		this.setBorder(new LineBorder(Color.ORANGE, 5));
		this.setName("Group");
	}

	public String getTypeName() {
		return "UML_GROUP";
	}

	public void highlight() {

	}

	public void draw(Graphics g) {
		for (int i = 0; i < ports.size(); i++) {
			PortInterface e = ports.get(i);
			e.draw(g);
		}
	}

	public boolean containsPoint(Point p) {
		if (p.x > 0 && p.y > 0 && p.x < this.getWidth()
				&& p.y < this.getHeight())
			return true;
		return false;
	}

	public void immediateFinalize() {
		for (int i = 0; i < umlShapes.size(); i++) {
			UMLShape us = umlShapes.get(i);
		}
		umlShapes.clear();
		this.selected = false;
		for (int i = 0; i < ports.size(); i++) {
			PortInterface pi = ports.get(i);
			if (pi instanceof Component) {
				((Container) UMLUtilities.getConainterAncestor(this))
						.remove((Component) pi);
			}
		}
		ports.clear();
	}

	@Override
	public void finalize() throws Throwable {
		immediateFinalize();
		super.finalize();
	}

	public UMLShapeContainer getContainerParent() {
		return parent;
	}

	public void setContainerParent(UMLShapeContainer p) {
		this.parent = p;
	}

	@Override
	public void paint(Graphics g) {
		super.paint(g);
		if (umlShapes != null) {
			for (int i = 0; i < umlShapes.size(); i++) {
				UMLShape e = umlShapes.get(i);
				e.paint(g);
			}
		}
	}
}
