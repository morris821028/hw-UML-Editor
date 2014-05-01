package observerHandle;

import java.util.*;

public class ObservableSpeaker extends Observable {
	public static ObservableSpeaker singleton = null;
	public static ObservableSpeaker getInstance() {
		if(singleton == null)
			singleton = new ObservableSpeaker();
		return singleton;
	}
	private ObservableSpeaker() {
		this.addObserver(ActionObserver.getInstance());
	}
	public void speak(Object arg) {
		this.setChanged();
		this.notifyObservers(arg);
	}
}
