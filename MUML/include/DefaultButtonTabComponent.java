package include;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.AbstractButton;
import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.JTabbedPane;
import javax.swing.ScrollPaneConstants;
import javax.swing.plaf.basic.BasicButtonUI;
import frame.*;

public class DefaultButtonTabComponent extends ButtonTabComponent {
	public DefaultButtonTabComponent(final JTabbedPane pane) {
		super(pane);
		removeTabButton();
		addTabButton(new AddTabButton());
	}

	private class AddTabButton extends JButton implements ActionListener {
		public AddTabButton() {
			int size = 17;
			setPreferredSize(new Dimension(size, size));
			setToolTipText("add new tab");
			// Make the button looks the same for all Laf's
			setUI(new BasicButtonUI());
			// Make it transparent
			setContentAreaFilled(false);
			// No need to be focusable
			setFocusable(false);
			setBorder(BorderFactory.createEtchedBorder());
			setBorderPainted(false);
			// Making nice rollover effect
			// we use the same listener for all buttons
			addMouseListener(buttonMouseListener);
			setRolloverEnabled(true);
			// Close the proper tab by clicking the button
			addActionListener(this);
		}

		public void actionPerformed(ActionEvent e) {
			String docTitle = JOptionPane.showInputDialog(null,
					"Give new canvas name : ", "Untitled"
							+ (pane.getTabCount() - 1));
			if (docTitle != null && docTitle.trim().length() > 0) {
				Canvas canvas = new Canvas();
				MainFrame.setKeyboardShortCut(canvas);
				canvas.setName(docTitle);
				int v = ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED;
				int h = ScrollPaneConstants.HORIZONTAL_SCROLLBAR_AS_NEEDED;
				JScrollPane jp = new JScrollPane(canvas, v, h);
				Component defaultpage = pane
						.getComponentAt(pane.getTabCount() - 1);
				Component defaultcomp = pane.getTabComponentAt(pane
						.getTabCount() - 1);
				pane.remove(defaultpage);
				pane.addTab(docTitle, jp);
				pane.addTab("", defaultpage);
				pane.setTabComponentAt(pane.getTabCount() - 2,
						new ButtonTabComponent(pane));
				pane.setTabComponentAt(pane.getTabCount() - 1, defaultcomp);
			}
		}

		// we don't want to update UI for this button
		public void updateUI() {
		}

		// paint the cross
		protected void paintComponent(Graphics g) {
			super.paintComponent(g);
			Graphics2D g2 = (Graphics2D) g.create();
			// shift the image for pressed buttons
			if (getModel().isPressed()) {
				g2.translate(1, 1);
			}
			g2.setStroke(new BasicStroke(2));
			g2.setColor(Color.BLACK);
			if (getModel().isRollover()) {
				g2.setColor(Color.WHITE);
			}
			int delta = 2;
			g2.drawLine(getWidth() / 2, delta, getWidth() / 2, getHeight()
					- delta - 1);
			g2.drawLine(delta, getHeight() / 2, getWidth() - delta - 1,
					getHeight() / 2);
			g2.dispose();
		}
	}

	private final static MouseListener buttonMouseListener = new MouseAdapter() {
		public void mouseEntered(MouseEvent e) {
			Component component = e.getComponent();
			if (component instanceof AbstractButton) {
				AbstractButton button = (AbstractButton) component;
				button.setBorderPainted(true);
			}
		}

		public void mouseExited(MouseEvent e) {
			Component component = e.getComponent();
			if (component instanceof AbstractButton) {
				AbstractButton button = (AbstractButton) component;
				button.setBorderPainted(false);
			}
		}
	};
}
