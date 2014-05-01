package frame;

import java.awt.*;

import javax.swing.*;
import javax.swing.event.TreeSelectionEvent;
import javax.swing.event.TreeSelectionListener;

import modelTree.*;
import UMLComponent.*;

public class ModelExplorer extends JPanel implements TreeSelectionListener {
	public static ModelExplorer singleton = null;
	private Canvas foucsCanvas;
	private JScrollPane modelTreePane;
	private JScrollPane detailTablePane;
	private JTree modelTree;
	private JTable detailTable;

	public static ModelExplorer getInstance() {
		if (singleton == null)
			singleton = new ModelExplorer();
		return singleton;
	}

	private ModelExplorer() {
		foucsCanvas = null;

		MTreeNode rootNode = TreeBuilder.build(foucsCanvas);
		MTreeModel model = new MTreeModel(rootNode);
		this.modelTree = new JTree(model);
		this.modelTree.setCellRenderer(new NodeRenderer());
		this.modelTree.addTreeSelectionListener(this);
		this.modelTreePane = new JScrollPane(new JPanel());
		JPanel view = ((JPanel) modelTreePane.getViewport().getView());
		view.setLayout(new BorderLayout());
		view.add(modelTree, BorderLayout.CENTER);

		this.detailTable = new JTable();
		this.detailTablePane = new JScrollPane(new JPanel());
		view = ((JPanel) detailTablePane.getViewport().getView());
		view.setLayout(new BorderLayout());
		view.add(detailTable, BorderLayout.CENTER);

		this.setLayout(new BorderLayout());
		this.add(modelTreePane, BorderLayout.CENTER);
		this.add(detailTablePane, BorderLayout.SOUTH);
		this.setOpaque(false);
	}

	public void changeFoucsCanvas(Canvas foucsCanvas) {
		this.foucsCanvas = foucsCanvas;
		MTreeNode rootNode = TreeBuilder.build(foucsCanvas);
		MTreeModel model = new MTreeModel(rootNode);
		modelTree = new JTree(model);
		modelTree.setCellRenderer(new NodeRenderer());
		modelTree.addTreeSelectionListener(this);
		JPanel view = ((JPanel) modelTreePane.getViewport().getView());
		view.removeAll();
		view.add(modelTree, BorderLayout.CENTER);
		view.validate();
		this.updateUI();
	}

	public void refreshFoucsCanvas() {
		MTreeNode rootNode = TreeBuilder.build(foucsCanvas);
		MTreeModel model = new MTreeModel(rootNode);
		modelTree = new JTree(model);
		modelTree.setCellRenderer(new NodeRenderer());
		modelTree.addTreeSelectionListener(this);
		JPanel view = ((JPanel) modelTreePane.getViewport().getView());
		view.removeAll();
		view.add(modelTree, BorderLayout.CENTER);
		view.validate();
		this.updateUI();
	}

	@Override
	public void valueChanged(TreeSelectionEvent e) {
		Object node = modelTree.getLastSelectedPathComponent();
		if (node instanceof MTreeNode) {
			MTreeNode mnode = (MTreeNode) node;
			if (mnode.getClassName().equals("UML_CLASS")) {
				UMLClass cls = (UMLClass) mnode.target;
				JPanel view = ((JPanel) detailTablePane.getViewport().getView());
				view.removeAll();
				view.setLayout(new GridLayout(7, 1));
				JLabel title = new JLabel("Detail");
				title.setFont(new Font("Comic Sans MS", Font.BOLD, 18));
				title.setHorizontalAlignment(JLabel.CENTER);
				view.add(title);
				JPanel namePanel = new JPanel();
				namePanel.add(new JLabel("Name"), BorderLayout.NORTH);
				namePanel.add(new JTextField(cls.className.getDocument(), cls.getName(), 10), BorderLayout.CENTER);
				view.add(namePanel);
				title = new JLabel("Attribute");
				title.setFont(new Font("Comic Sans MS", Font.BOLD, 18));
				view.add(title);
				view.add(new JTable(cls.attributeTable.getModel()));
				title = new JLabel("Method");
				title.setFont(new Font("Comic Sans MS", Font.BOLD, 18));
				view.add(title);
				view.add(new JTable(cls.methodTable.getModel()));
				view.validate();
				this.updateUI();
			}
			return;
		}
	}
}
