package modelTree;

import java.awt.Component;

import javax.swing.ImageIcon;
import javax.swing.JTree;
import javax.swing.tree.DefaultTreeCellRenderer;


/**
 * This class is implemented to customize the display of a node.
 * 
 * @author Ha Minh Nam
 * 
 */
public class NodeRenderer extends DefaultTreeCellRenderer {
	private ImageIcon iconRoot = new ImageIcon(getClass().getResource(
			"images/TreeRoot.png"));
	private ImageIcon iconGroup = new ImageIcon(getClass().getResource(
			"images/TreeGroup.png"));
	private ImageIcon iconClass = new ImageIcon(getClass().getResource(
			"images/TreeClass.png"));
	private ImageIcon iconCase = new ImageIcon(getClass().getResource(
			"images/TreeCase.png"));

	public Component getTreeCellRendererComponent(JTree tree, Object value,
			boolean sel, boolean expanded, boolean leaf, int row,
			boolean hasFocus) {
		super.getTreeCellRendererComponent(tree, value, sel, expanded, leaf,
				row, hasFocus);

		MTreeNode node = (MTreeNode) value;
		switch (node.getType()) {
		case MTreeNode.NODE_ROOT:
			setIcon(iconRoot);
			break;
		case MTreeNode.NODE_GROUP:
			setIcon(iconGroup);
			break;
		case MTreeNode.NODE_CLASS:
			setIcon(iconClass);
			break;
		case MTreeNode.NODE_CASE:
			setIcon(iconCase);
			break;
		}

		return this;
	}
}