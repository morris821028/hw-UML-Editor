package frame;

import java.awt.*;
import java.awt.event.*;

import javax.swing.*;
import javax.swing.event.*;

import java.util.*;

import mode.*;
import UMLComponent.*;
import include.*;
import frame.*;

public class Canvas extends UMLShapeContainer implements MouseInputListener {
	private Mode canvasMode;

	public Canvas() {
		this.addMouseListener(this);
		this.addMouseMotionListener(this);
		this.setLayout(null);
		this.setPreferredSize(new Dimension(1920, 1080));
	}

	public void setCanvasMode(Mode mode) {
		this.canvasMode = mode;
	}

	public Mode getCanvasMode() {
		return this.canvasMode;
	}

	public void mouseMoved(MouseEvent e) {
		if (this.canvasMode == null)
			return;
		this.canvasMode.mouseMoved(e, this);
	}

	public void mouseDragged(MouseEvent e) {
		if (this.canvasMode == null)
			return;
		this.canvasMode.mouseDragged(e, this);
	}

	public void mouseReleased(MouseEvent e) {
		if (this.canvasMode == null)
			return;
		this.canvasMode.mouseReleased(e, this);
	}

	public void mouseEntered(MouseEvent e) {
		if (this.canvasMode == null)
			return;
		this.canvasMode.mouseEntered(e, this);
	}

	public void mouseExited(MouseEvent e) {
		if (this.canvasMode == null)
			return;
		this.canvasMode.mouseExited(e, this);
	}

	public void mouseClicked(MouseEvent e) {
		if (this.canvasMode == null)
			return;
		this.canvasMode.mouseClicked(e, this);
	}

	public void mousePressed(MouseEvent e) {
		if (this.canvasMode == null)
			return;
		this.canvasMode.mousePressed(e, this);
	}

	@Override
	public void paint(Graphics g) {
		super.paint(g);
		if (this.canvasMode != null)
			this.canvasMode.paint(g, this);
	}

	public UMLShapeContainer getContainerParent() {
		return parent;
	}

	public void setContainerParent(UMLShapeContainer p) {
		this.parent = p;
	}

	public void setSelected(boolean b) {

	}

	public boolean getSelected() {
		return false;
	}

	public void containtsPoint(Point p) {

	}

	public String getTypeName() {
		return "Canvas";
	}

	public void highlight() {

	}

	public void immediateFinalize() {

	}

	@Override
	public boolean containsPoint(Point p) {
		return false;
	}
}
