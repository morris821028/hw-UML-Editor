package modelTree;

import UMLComponent.*;

import java.awt.*;

import frame.*;

public class TreeBuilder {
	public static MTreeNode getMTreeNode(Component c) {
		if (c instanceof frame.Canvas)
			return new MTreeNode(((frame.Canvas) c).getName(), MTreeNode.NODE_ROOT, c);
		if(c instanceof UMLClass) 
			return new MTreeNode(c.getName(), MTreeNode.NODE_CLASS, c);
		if(c instanceof UMLUseCase) 
			return new MTreeNode(c.getName(), MTreeNode.NODE_CASE, c);
		if(c instanceof UMLGroup) 
			return new MTreeNode(c.getName(), MTreeNode.NODE_GROUP, c);
		return null;
	}

	public static MTreeNode build(Component c) {
		MTreeNode rootNode = getMTreeNode(c);
		if (c instanceof Container && rootNode != null) {
			int child = ((Container) c).getComponentCount();
			Component[] cc = ((Container) c).getComponents();
			Component[] cz = new Component[child];
			for (int i = 0; i < child; i++) {
				int zorder = ((Container) c).getComponentZOrder(cc[i]);
				cz[zorder] = cc[i];
			}
			for (int i = 0; i < child; i++) {
				MTreeNode node = TreeBuilder.build(cz[i]);
				if (node != null)
					rootNode.addChild(node);
			}
		}
		return rootNode;
	}
}
