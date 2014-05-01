package frame;

import java.awt.Component;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.imageio.ImageIO;
import javax.swing.*;

import mode.AssociationMode;
import mode.ClassMode;
import mode.CompositionMode;
import mode.GeneralMode;
import mode.Mode;
import mode.ModeButton;
import mode.ModeChooser;
import mode.SelectMode;
import mode.*;

import javax.swing.*;
import javax.swing.event.*;
import java.awt.event.*;
import java.awt.*;

public class CanvasModeChooser extends JPanel implements ModeChooser,
		ItemListener {
	public static CanvasModeChooser singleton = null;
	private Canvas canvas = null;
	private ButtonGroup listGroup;
	private final Mode mode[] = { new SelectMode(), new AssociationMode(),
			new GeneralMode(), new CompositionMode(), new ClassMode(),
			new UsecaseMode() };
	private final ModeButton[] listButton = new ModeButton[mode.length];

	public static CanvasModeChooser getInstance() {
		if (singleton == null)
			singleton = new CanvasModeChooser(null);
		return singleton;
	}

	private CanvasModeChooser(Canvas canvas) {
		this.canvas = canvas;
		this.listGroup = new ButtonGroup();
		this.setLayout(new GridLayout(mode.length, 1));
		for (int i = 0; i < mode.length; i++) {
			ModeButton button = new ModeButton(mode[i]);
			listButton[i] = button;
			String buttonName = mode[i].getDisplayName();
			ImageIcon selectedImg = null, unselectedImg = null;
			try {
				selectedImg = new ImageIcon(ImageIO.read(this.getClass()
						.getResource("images/" + buttonName + "0.png")));
				unselectedImg = new ImageIcon(ImageIO.read(this.getClass()
						.getResource("images/" + buttonName + "1.png")));
			} catch (Exception ee) {
				System.out.println(ee.getMessage());
			}
			button.addItemListener(this);
			button.setSelectedIcon(unselectedImg);
			button.setIcon(selectedImg);
			button.setBorder(BorderFactory.createEmptyBorder());
			button.setContentAreaFilled(false);
			button.setToolTipText(buttonName);
			if (i == 0)
				button.setSelected(true);
			this.add(button);
			this.listGroup.add(button);
		}
	}

	public void setCanvas(Canvas canvas) {
		this.canvas = canvas;
	}

	public Canvas getCanvas() {
		return this.canvas;
	}

	public void setMode(Mode mode) {
		if (mode == null) {
			listButton[0].setSelected(true);
		} else {
			for (int i = 0; i < listButton.length; i++) {
				if (listButton[i].getMode() == mode)
					listButton[i].setSelected(true);
			}
		}
	}

	@Override
	public Mode getMode() {
		return ((ModeButton.ModeButtonModel) (listGroup.getSelection()))
				.getMode();
	}

	public void itemStateChanged(ItemEvent e) {
		Object item = e.getItem();
		if (e.getStateChange() == ItemEvent.SELECTED) {
			if (item instanceof ModeChooser) {
				ModeChooser gmi = (ModeChooser) item;
				if (canvas != null) {
					this.canvas.setCanvasMode(gmi.getMode());
				}
			}
		}
	}
}
