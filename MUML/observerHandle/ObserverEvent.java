package observerHandle;

import java.awt.Component;
import java.awt.Point;
import java.awt.event.MouseEvent;
import javax.swing.event.MouseInputListener;
import javax.swing.*;

import java.util.*;

public class ObserverEvent {
	public static ObserverEvent CREATE_UMLSHAPE = new ObserverEvent(
			"createUMLShape");
	public static ObserverEvent DEL_UMLSHAPE = new ObserverEvent(
			"deleteUMLShape");
	public String funcName;
	public Object[] other = null;

	private ObserverEvent(String funcName) {
		this.funcName = funcName;
	}

	public void setOtherInfo(Object str[]) {
		this.other = str;
	}
}
