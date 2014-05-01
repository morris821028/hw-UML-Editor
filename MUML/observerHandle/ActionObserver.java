package observerHandle;

import java.util.Observable;
import java.util.Observer;

import javax.swing.*;

import frame.MainFrame;
import frame.ModelExplorer;

import java.awt.*;

public class ActionObserver implements Observer {
	public static ActionObserver singleton = null;

	public static ActionObserver getInstance() {
		if(singleton == null)
			singleton = new ActionObserver();
		return singleton;
	}

	private ActionObserver() {

	}

	public void update(Observable o, Object arg) {
		if (arg instanceof ObserverEvent) {
			ObserverEvent oe = (ObserverEvent) arg;
			if (oe.funcName.equals("createUMLShape")) {
				ModelExplorer.getInstance().refreshFoucsCanvas();
			} else if (oe.funcName.equals("deleteUMLShape")) {
				ModelExplorer.getInstance().refreshFoucsCanvas();
			} else if (oe.funcName.equals("")) {

			}
		}
	}
}
