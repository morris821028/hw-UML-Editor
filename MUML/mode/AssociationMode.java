package mode;

import java.awt.AlphaComposite;
import java.awt.Color;
import java.awt.Component;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.event.MouseEvent;
import java.util.ArrayList;

import javax.swing.SwingUtilities;

import UMLComponent.*;
import UMLComponent.Line.AssociationLine;
import UMLComponent.Line.DirectLine;
import UMLComponent.Port.PortInterface;
import frame.Canvas;
import frame.MainFrame;

public class AssociationMode extends Mode {
	private UMLShape sBasic, eBasic;
	private PortInterface sPort, ePort;
	private Point mouseAt;

	public AssociationMode() {
		this.setDisplayName("association");
	}

	public void mouseClicked(MouseEvent e, Canvas canvasPanel) {
	}

	public void mouseMoved(MouseEvent e, Canvas canvasPanel) {
	}

	public void mouseDragged(MouseEvent e, Canvas canvasPanel) {
		mouseAt = SwingUtilities.convertPoint(e.getComponent(), e.getPoint(),
				canvasPanel);
		Point p = SwingUtilities.convertPoint(e.getComponent(), e.getPoint(),
				canvasPanel);
		Point rp = p;
		UMLShape select = canvasPanel.getShapeAt(p);
		eBasic = null;
		ePort = null;
		if (select != null) {
			rp = UMLUtilities.convertPoint(canvasPanel, p, select);
			if (select.containsPoint(rp)) {
				if (select.getClosestPort(rp) != null) {
					eBasic = select;
					ePort = eBasic.getClosestPort(rp);
					if (eBasic == sBasic)
						eBasic = null;
				}
			}
		}
		canvasPanel.repaint();
	}

	public void mouseReleased(MouseEvent e, Canvas canvasPanel) {
		Object source = e.getSource();
		mouseAt = SwingUtilities.convertPoint(e.getComponent(), e.getPoint(),
				canvasPanel);
		if (sBasic != eBasic && sPort != null && ePort != null) {
			DirectLine al = new AssociationLine(sPort, ePort);
			al.setDisplayType(DirectLine.LINE_NORMAL);
			sPort.addDirectLine(al);
			ePort.addDirectLine(al);
			canvasPanel.addUMLShape(al);
		}
		sBasic = null;
		eBasic = null;
		canvasPanel.repaint();
	}

	public void mousePressed(MouseEvent e, Canvas canvasPanel) {
		Object source = e.getSource();
		Point p = SwingUtilities.convertPoint((Component) e.getSource(),
				e.getPoint(), canvasPanel);
		UMLShape select = canvasPanel.getShapeAt(p);
		if (select != null) {
			Point rp = UMLUtilities.convertPoint(canvasPanel, p, select);
			if (sBasic == null && select.getClosestPort(rp) != null) {
				sBasic = select;
				sPort = sBasic.getClosestPort(rp);
				mouseAt = p;
			}
		}
	}

	public void mouseEntered(MouseEvent e, Canvas canvasPanel) {
	}

	public void mouseExited(MouseEvent e, Canvas canvasPanel) {
	}

	public void paint(Graphics g, Canvas canvasPanel) {
		if (sBasic != null) {
			Point s;
			s = UMLUtilities.convertPoint(sBasic, sPort.getLocation(),
					canvasPanel);
			DirectLine preview;
			if (eBasic != null) {
				Point e;
				e = UMLUtilities.convertPoint(eBasic, ePort.getLocation(),
						canvasPanel);
				preview = new AssociationLine(s, e);
			} else
				preview = new AssociationLine(s, mouseAt);
			preview.setDisplayType(DirectLine.LINE_PREVIEW);
			preview.paint(g);
		}
	}
}
