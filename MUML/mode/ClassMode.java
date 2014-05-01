package mode;

import java.awt.Point;
import java.awt.event.MouseEvent;

import frame.*;
import observerHandle.*;
import UMLComponent.*;
import UMLComponent.Port.*;
import UMLComponent.Port.ResizeCorner;

import java.util.Observable;

public class ClassMode extends Mode {

	public ClassMode() {
		this.setDisplayName("class");
	}

	public void mouseClicked(MouseEvent e, Canvas canvasPanel) {
	}

	public void mouseMoved(MouseEvent e, Canvas canvasPanel) {
	}

	public void mouseDragged(MouseEvent e, Canvas canvasPanel) {
	}

	@Override
	public void mouseReleased(MouseEvent e, Canvas canvasPanel) {
	}

	@Override
	public void mousePressed(MouseEvent e, Canvas canvasPanel) {
		Object source = e.getSource();
		if (source == canvasPanel) {
			Point p = e.getPoint();
			UMLShapeContainer us = new UMLClass();
			us.setSelected(false);
			us.setLocation(p);
			us.addMouseListener(canvasPanel);
			us.addMouseMotionListener(canvasPanel);
			canvasPanel.addUMLShapeContainer(us);
			canvasPanel.add(us);
			canvasPanel.setComponentZOrder(us, 0);
			int resizeports[] = Port.CORNER_LAYOUT;
			for (int i = 0; i < resizeports.length; i++) {
				ResizeCorner rc = new ResizeCorner(resizeports[i], us);
				//Port rc = new Port(resizeports[i], us);
				us.addPort(rc);
				canvasPanel.add(rc);
			}
			resizeports = Port.NORMAL_LAYOUT;
			for (int i = 0; i < resizeports.length; i++) {
				ResizeCorner rc = new ResizeCorner(resizeports[i], us);
				us.addPort(rc);
				canvasPanel.add(rc);
			}
			canvasPanel.repaint();

			ObservableSpeaker.getInstance().speak(ObserverEvent.CREATE_UMLSHAPE);
		}
	}

	public void mouseEntered(MouseEvent e, Canvas canvasPanel) {
	}

	public void mouseExited(MouseEvent e, Canvas canvasPanel) {
	}
}
