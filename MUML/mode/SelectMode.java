package mode;

import java.awt.event.MouseEvent;

import frame.Canvas;
import frame.MainFrame;
import java.awt.*;

import javax.swing.*;

import observerHandle.ActionObserver;
import observerHandle.ObservableSpeaker;
import observerHandle.ObserverEvent;

import UMLComponent.*;
import java.util.*;

public class SelectMode extends Mode {
	public static Point sPoint = null, ePoint = null; // select area.
	public static Point umlsPoint = null, umlePoint = null; // move component
															// used.

	public SelectMode() {
		this.setDisplayName("select");
	}

	public static void changeNameSelectedUMLShape(Canvas canvasPanel) {
		UMLShape[] shapes = canvasPanel.getShapes();
		for (int i = 0; i < shapes.length; i++) {
			UMLShape us = shapes[i];
			if (us.getSelected()) {
				String name = us.getName();
				String str = JOptionPane.showInputDialog("Rename '" + name
						+ "' to ? ");
				if (str != null && str.trim().length() > 0) {
					us.setName(str.trim());
				}
			}
		}
	}

	public static void moveSelectedUMLShape(int vx, int vy, Canvas canvasPanel) {
		boolean flag = false;
		UMLShape[] shapes = canvasPanel.getShapes();
		for (int i = 0; i < shapes.length; i++) {
			UMLShape select = shapes[i];
			if (select.getSelected()) {
				select.setBounds(select.getX() + vx, select.getY() + vy,
						select.getWidth(), select.getHeight());
				select.highlight();
				flag = true;
			}
		}
		if (flag == false) {

		}
		canvasPanel.repaint();
	}

	public static void removeSelectedUMLShape(Canvas canvasPanel) {
		UMLShape[] shapes = canvasPanel.getShapes();
		for (int i = 0; i < shapes.length; i++) {
			UMLShape us = shapes[i];
			if (us.getSelected()) {
				int option = JOptionPane.showConfirmDialog(null, "Remove ["
						+ us.getTypeName() + "] " + us.getName() + "?");
				if (option == 0) {
					canvasPanel.removeUMLShape(us);
					us.immediateFinalize();
					if (us instanceof Component) {
						canvasPanel.remove((Component) us);
					}
				}
			}
		}
		canvasPanel.updateUI();
		ObservableSpeaker.getInstance().speak(ObserverEvent.CREATE_UMLSHAPE);
	}

	public void mouseClicked(MouseEvent e, Canvas canvasPanel) {
		if (e.getClickCount() == 2) {
			Point p = SwingUtilities.convertPoint((Component) e.getSource(),
					e.getPoint(), canvasPanel);
			UMLShape select = canvasPanel.getShapeAt(p);
			UMLShape[] shapes = canvasPanel.getShapes();
			if (select != null) {
				if (select.getSelected())
					select.adjustByMouse(e);
			}
			for (int i = 0; i < shapes.length; i++) {
				UMLShape us = shapes[i];
				us.setSelected(false);
			}
			if (select != null) {
				select.highlight();
				select.setSelected(true);
			}
			canvasPanel.repaint();
		}
	}

	public void mouseMoved(MouseEvent e, Canvas canvasPanel) {
	}

	public void mouseDragged(MouseEvent e, Canvas canvasPanel) {
		Object source = e.getSource();
		if (source == canvasPanel) { // select area.
			Point p = SwingUtilities.convertPoint(e.getComponent(),
					e.getPoint(), canvasPanel);
			UMLShape select = canvasPanel.getShapeAt(p);
			if (select != null)
				select.adjustByMouse(e);
			if (sPoint != null)
				ePoint = p;
			canvasPanel.repaint();
		} else {
			Point p = SwingUtilities.convertPoint(e.getComponent(),
					e.getPoint(), canvasPanel);
			UMLShape select = canvasPanel.getShapeAt(p);
			umlePoint = p;
			if (umlsPoint != null && select != null && select.getSelected()) {
				SelectMode.moveSelectedUMLShape(umlePoint.x - umlsPoint.x,
						umlePoint.y - umlsPoint.y, canvasPanel);
				umlsPoint = umlePoint;
			}
		}
	}

	public void mouseReleased(MouseEvent e, Canvas canvasPanel) {
		if (sPoint != null && ePoint != null) {
			int mnx, mny;
			int mxx, mxy;
			mnx = Math.min(sPoint.x, ePoint.x);
			mny = Math.min(sPoint.y, ePoint.y);
			mxx = Math.max(sPoint.x, ePoint.x);
			mxy = Math.max(sPoint.y, ePoint.y);
			Rectangle area = new Rectangle(mnx, mny, mxx - mnx, mxy - mny);
			UMLShape shapes[] = canvasPanel.getShapes();
			UMLShape select[] = canvasPanel.getShapeIn(area);
			for (int i = 0; i < shapes.length; i++)
				shapes[i].setSelected(false);
			for (int i = 0; i < select.length; i++)
				select[i].setSelected(true);
			canvasPanel.repaint();
		}
		sPoint = null;
		ePoint = null;
		umlsPoint = null;
		umlePoint = null;
	}

	public void mousePressed(MouseEvent e, Canvas canvasPanel) {
		Object source = e.getSource();
		if (source == canvasPanel) {
			sPoint = e.getPoint();
			canvasPanel.repaint();
		} else {
			Component cp = e.getComponent();
			umlsPoint = SwingUtilities.convertPoint(cp, e.getPoint(),
					canvasPanel);
		}
	}

	public void mouseEntered(MouseEvent e, Canvas canvasPanel) {

	}

	public void mouseExited(MouseEvent e, Canvas canvasPanel) {

	}

	public void paint(Graphics g, Canvas canvasPanel) {
		Graphics2D g2d = (Graphics2D) g;
		g2d.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_ATOP,
				0.5f));
		// g2d.setColor(new Color(224, 255, 255));
		g2d.setColor(Color.BLACK);
		if (sPoint != null && ePoint != null) {
			int mnx, mny;
			int mxx, mxy;
			mnx = Math.min(sPoint.x, ePoint.x);
			mny = Math.min(sPoint.y, ePoint.y);
			mxx = Math.max(sPoint.x, ePoint.x);
			mxy = Math.max(sPoint.y, ePoint.y);
			g2d.fillRect(mnx, mny, mxx - mnx, mxy - mny);
		}
	}
}
