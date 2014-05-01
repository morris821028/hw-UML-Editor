package mode;

import java.awt.Component;
import java.awt.Graphics;
import java.awt.Point;
import java.awt.event.MouseEvent;
import java.util.ArrayList;

import javax.swing.JOptionPane;
import javax.swing.SwingUtilities;

import observerHandle.ActionObserver;
import observerHandle.ObservableSpeaker;
import observerHandle.ObserverEvent;

import UMLComponent.BasicObject;
import UMLComponent.UMLGroup;
import UMLComponent.UMLShape;
import frame.Canvas;
import frame.MainFrame;
import UMLComponent.*;
import UMLComponent.Line.DirectLine;
import UMLComponent.Line.GeneralLine;
import UMLComponent.Port.Port;
import UMLComponent.Port.ResizeCorner;

public class GroupMode extends Mode {
	public GroupMode() {
		this.setDisplayName("group");
	}

	public static void groupSelectedUMLShape(Canvas canvasPanel) {
		UMLShapeContainer[] shapes = canvasPanel.getShapeContainers();
		int count = 0;
		for (int i = 0; i < shapes.length; i++) {
			UMLShape us = shapes[i];
			if (us.getSelected()) {
				count++;
			}
		}
		UMLShapeContainer[] select = new UMLShapeContainer[count];
		for (int i = 0; i < shapes.length; i++) {
			UMLShapeContainer us = shapes[i];
			if (us.getSelected()) {
				select[--count] = us;
			}
		}
		UMLShapeContainer group = new UMLGroup();
		for (int i = 0; i < select.length; i++) {
			UMLShapeContainer us = select[i];
			canvasPanel.removeUMLShapeContainer(us);
			canvasPanel.remove(us);
		}
		group.addUMLShapeContainers(select);

		int resizeports[] = Port.CORNER_LAYOUT;
		for (int i = 0; i < resizeports.length; i++) {
			ResizeCorner rc = new ResizeCorner(resizeports[i], group);
			group.addPort(rc);
			canvasPanel.add(rc);
		}
		group.addMouseListener(canvasPanel);
		group.addMouseMotionListener(canvasPanel);
		canvasPanel.addUMLShapeContainer(group);
		canvasPanel.add(group);
		canvasPanel.setComponentZOrder(group, 0);
		canvasPanel.updateUI();

		ObservableSpeaker.getInstance().speak(ObserverEvent.CREATE_UMLSHAPE);
	}

	public static void ungroupSelectedUMLShape(UMLShapeContainer canvas) {
		UMLShapeContainer[] shapes = canvas.getShapeContainers();
		for (int i = 0; i < shapes.length; i++) {
			UMLShapeContainer us = shapes[i];
			UMLShapeContainer parent = us.getContainerParent();
			if (us.getSelected()) {
				System.out.println("sss");
				UMLShapeContainer[] groupShapes = us.getShapeContainers();
				int x = us.getX(), y = us.getY();
				for (int j = 0; j < groupShapes.length; j++) {
					UMLShapeContainer e = groupShapes[j];
					int ox, oy;
					ox = e.getX();
					oy = e.getY();
					e.setLocation(x + ox, y + oy);
					us.remove(e);
					if (parent != null) {
						parent.add(e);
						parent.addUMLShapeContainer(e);
					}
				}
				us.immediateFinalize();
				if (parent != null) {
					parent.remove(us);
					parent.removeUMLShapeContainer(us);
				}
				SelectMode.sPoint = null;
				SelectMode.ePoint = null; // select area.
				SelectMode.umlsPoint = null;
				SelectMode.umlePoint = null;

				ObservableSpeaker.getInstance().speak(
						ObserverEvent.DEL_UMLSHAPE);
			}
		}
	}

	public void mouseClicked(MouseEvent e, Canvas canvasPanel) {
	}

	public void mouseMoved(MouseEvent e, Canvas canvasPanel) {
	}

	public void mouseDragged(MouseEvent e, Canvas canvasPanel) {

	}

	public void mouseReleased(MouseEvent e, Canvas canvasPanel) {

	}

	public void mousePressed(MouseEvent e, Canvas canvasPanel) {

	}

	public void mouseEntered(MouseEvent e, Canvas canvasPanel) {
	}

	public void mouseExited(MouseEvent e, Canvas canvasPanel) {
	}

	public void paint(Graphics g, Canvas canvasPanel) {

	}
}
