package mode;

import javax.swing.*;

import frame.Canvas;

public interface ModeChooser {
	public Mode getMode();

	public void setMode(Mode e);

	public void setCanvas(Canvas canvas);

	public Canvas getCanvas();
}