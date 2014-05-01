package frame;

import java.util.*;

public class Workspace {
	private static Workspace singleton = null;
	private ArrayList<Canvas> canvasList;
	private Canvas selectedCanvas;

	public static Workspace getInstance() {
		if (singleton == null)
			singleton = new Workspace();
		return singleton;
	}

	private Workspace() {
		canvasList = new ArrayList<Canvas>();
	}

	public void removeCanvas(Canvas c) {
		for (int i = 0; i < canvasList.size(); i++) {
			if (canvasList.get(i) == c) {
				canvasList.remove(i);
			}
		}
	}

	public void addCanvas(Canvas c) {
		removeCanvas(c);
		canvasList.add(c);
	}

	public void setSelectedCanvas(Canvas c) {
		selectedCanvas = c;
	}

	public Canvas getSelectedCanvas() {
		return selectedCanvas;
	}
}
