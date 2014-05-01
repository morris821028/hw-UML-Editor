package mode;

import javax.swing.*;

import frame.Canvas;

public class ModeButton extends JToggleButton implements ModeChooser {
	private Mode mode;
	private Canvas canvas = null;

	public ModeButton(Mode mode) {
		this.mode = mode;
		this.setModel(new ModeButtonModel());
	}

	public class ModeButtonModel extends ToggleButtonModel {
		public Mode getMode() {
			return mode;
		}
	}

	public Mode getMode() {
		return mode;
	}

	public void setMode(Mode e) {
		this.mode = e;
	}

	public void setCanvas(Canvas canvas) {
		this.canvas = canvas;
	}

	public Canvas getCanvas() {
		return this.canvas;
	}
}
