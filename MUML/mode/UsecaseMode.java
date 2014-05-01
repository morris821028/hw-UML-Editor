package mode;

import java.awt.Point;
import java.awt.event.MouseEvent;

import observerHandle.ActionObserver;
import observerHandle.ObservableSpeaker;
import observerHandle.ObserverEvent;

import UMLComponent.BasicObject;
import UMLComponent.UMLUseCase;
import UMLComponent.Port.Port;
import UMLComponent.Port.ResizeCorner;
import frame.Canvas;
import frame.MainFrame;

public class UsecaseMode extends Mode {
	public UsecaseMode() {
		this.setDisplayName("usecase");
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
		Object source = e.getSource();
		if (source == canvasPanel) {
			Point p = e.getPoint();
			BasicObject us = new UMLUseCase();
			int ports[] = Port.NORMAL_LAYOUT;
			// for(int i = 0; i < ports.length; i++)
			// us.addPort(new Port(ports[i], us));
			int resizeports[] = Port.CORNER_LAYOUT;
			for (int i = 0; i < resizeports.length; i++) {
				ResizeCorner rc = new ResizeCorner(resizeports[i], us);
				us.addPort(rc);
				canvasPanel.add(rc);
			}
			resizeports = Port.NORMAL_LAYOUT;
			for (int i = 0; i < resizeports.length; i++) {
				ResizeCorner rc = new ResizeCorner(resizeports[i], us);
				us.addPort(rc);
				canvasPanel.add(rc);
			}
			us.setLocation(p);
			us.addMouseListener(canvasPanel);
			us.addMouseMotionListener(canvasPanel);
			canvasPanel.addUMLShapeContainer(us);
			canvasPanel.add(us);
			canvasPanel.setComponentZOrder(us, 0);
			canvasPanel.updateUI();

			ObservableSpeaker.getInstance().speak(ObserverEvent.CREATE_UMLSHAPE);
		}
	}

	public void mouseEntered(MouseEvent e, Canvas canvasPanel) {
	}

	public void mouseExited(MouseEvent e, Canvas canvasPanel) {
	}
}
