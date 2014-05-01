package UMLComponent;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Vector;

import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.table.AbstractTableModel;
import javax.swing.table.TableColumnModel;

import frame.MainFrame;

public class AttributeTable extends JTable {
	private MTableModel attributeModel;
	private JPopupMenu toolMenu;
	private Font displayFont = new Font("Comic Sans MS", Font.BOLD, 14);

	public AttributeTable() {
		attributeModel = new MTableModel();
		this.setModel(attributeModel);
		this.setBackground(new Color(255, 255, 185));
		this.setFont(displayFont);
		String[] choose = { "+", "#", "-", "~" };
		JComboBox<String> combx = new JComboBox<String>(choose);

		TableColumnModel tcm = this.getColumnModel();
		tcm.getColumn(0).setCellEditor(new DefaultCellEditor(combx));
		tcm.getColumn(0).setPreferredWidth(20);
		tcm.getColumn(1).setPreferredWidth(280);
		this.toolMenu = createToolMenu();
		this.setComponentPopupMenu(toolMenu);
	}

	public JPopupMenu getToolMenu() {
		return toolMenu;
	}

	private JPopupMenu createToolMenu() {
		final JPopupMenu ret = new JPopupMenu();
		ImageIcon publicImg = null, protectImg = null, privateImg = null, packageImg = null, upImg = null, downImg = null;
		ImageIcon addItemImg = null, delItemImg = null;
		try {
			addItemImg = new ImageIcon(ImageIO.read(this.getClass()
					.getResource("images/addItem.png")));
			delItemImg = new ImageIcon(ImageIO.read(this.getClass()
					.getResource("images/delItem.png")));
			publicImg = new ImageIcon(ImageIO.read(this.getClass().getResource(
					"images/public.png")));
			protectImg = new ImageIcon(ImageIO.read(this.getClass()
					.getResource("images/protected.png")));
			privateImg = new ImageIcon(ImageIO.read(this.getClass()
					.getResource("images/private.png")));
			packageImg = new ImageIcon(ImageIO.read(this.getClass()
					.getResource("images/package.png")));
			upImg = new ImageIcon(ImageIO.read(this.getClass().getResource(
					"images/upitem.png")));
			downImg = new ImageIcon(ImageIO.read(this.getClass().getResource(
					"images/downitem.png")));
		} catch (Exception ee) {

		}
		JButton btnAdd = new JButton(addItemImg);
		JButton btnDel = new JButton(delItemImg);
		JButton btnPublic = new JButton(publicImg);
		JButton btnProtect = new JButton(protectImg);
		JButton btnPrivate = new JButton(privateImg);
		JButton btnPackage = new JButton(packageImg);
		JButton btnUp = new JButton(upImg);
		JButton btnDown = new JButton(downImg);
		btnAdd.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				AttributeTable.this.clearSelection();
				attributeModel.addRow("+", "Attribute" + AttributeTable.this.getRowCount());
				updateUI();
			}
		});
		btnAdd.setContentAreaFilled(false);
		btnDel.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				int delIdx;
				delIdx = AttributeTable.this.getSelectedRow();
				if (delIdx < 0)
					return;
				AttributeTable.this.attributeModel.removeRow(delIdx);
				AttributeTable.this.clearSelection();
				updateUI();
			}
		});
		btnDel.setContentAreaFilled(false);
		btnPublic.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (AttributeTable.this.getSelectedRow() >= 0) {
					int rowIdx = AttributeTable.this.getSelectedRow();
					attributeModel.setValueAt("+", rowIdx, 0);
				}
			}
		});
		btnPublic.setContentAreaFilled(false);
		btnProtect.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (AttributeTable.this.getSelectedRow() >= 0) {
					int rowIdx = AttributeTable.this.getSelectedRow();
					attributeModel.setValueAt("#", rowIdx, 0);
				}
			}
		});
		btnProtect.setContentAreaFilled(false);
		btnPrivate.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (AttributeTable.this.getSelectedRow() >= 0) {
					int rowIdx = AttributeTable.this.getSelectedRow();
					attributeModel.setValueAt("-", rowIdx, 0);
				}
			}
		});
		btnPrivate.setContentAreaFilled(false);
		btnPackage.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (AttributeTable.this.getSelectedRow() >= 0) {
					int rowIdx = AttributeTable.this.getSelectedRow();
					attributeModel.setValueAt("~", rowIdx, 0);
				}
			}
		});
		btnPackage.setContentAreaFilled(false);
		btnUp.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (AttributeTable.this.getSelectedRow() > 0) {
					int rowIdx = AttributeTable.this.getSelectedRow();
					Object obj0, obj1;
					obj0 = attributeModel.getValueAt(rowIdx, 0);
					obj1 = attributeModel.getValueAt(rowIdx, 1);
					attributeModel.removeRow(rowIdx);
					attributeModel.insertRow(rowIdx - 1, obj0, obj1);
					AttributeTable.this.setRowSelectionInterval(rowIdx - 1,
							rowIdx - 1);
				}
				updateUI();
			}
		});
		btnUp.setContentAreaFilled(false);
		btnDown.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (AttributeTable.this.getSelectedRow() < AttributeTable.this
						.getRowCount() - 1) {
					int rowIdx = AttributeTable.this.getSelectedRow();
					if (rowIdx >= 0) {
						Object obj0, obj1;
						obj0 = attributeModel.getValueAt(rowIdx, 0);
						obj1 = attributeModel.getValueAt(rowIdx, 1);
						attributeModel.removeRow(rowIdx);
						attributeModel.insertRow(rowIdx + 1, obj0, obj1);
						AttributeTable.this.setRowSelectionInterval(rowIdx + 1,
								rowIdx + 1);
					}
				}
				updateUI();
			}
		});
		btnDown.setContentAreaFilled(false);
		btnAdd.setToolTipText("Add new attribute");
		btnDel.setToolTipText("Delete selected attribute");
		btnPublic.setToolTipText("public");
		btnProtect.setToolTipText("protected");
		btnPrivate.setToolTipText("private");
		btnPackage.setToolTipText("package");
		btnUp.setToolTipText("move up");
		btnDown.setToolTipText("move down");
		
		ret.add(btnAdd);
		ret.add(btnDel);
		ret.add(btnPublic);
		ret.add(btnProtect);
		ret.add(btnPrivate);
		ret.add(btnPackage);
		ret.add(btnUp);
		ret.add(btnDown);
		ret.setBackground(null);
		ret.setVisible(false);
		return ret;
	}

	public void addRow(Object obj0, Object obj1) {
		this.attributeModel.addRow(obj0, obj1);
	}

	class MTableModel extends AbstractTableModel {

		private static final long serialVersionUID = -7495940408592595397L;

		private Vector content = null;

		private String[] title_name = { "TYPE", "NAME" };

		public MTableModel() {
			content = new Vector();
		}

		public void insertRow(int row, Object obj0, Object obj1) {
			Vector v = new Vector(2);
			v.add(0, obj0);
			v.add(1, obj1);
			content.add(row, v);
		}

		public void addRow(Object obj0, Object obj1) {
			Vector v = new Vector(2);
			v.add(0, obj0);
			v.add(1, obj1);
			content.add(v);
		}

		public void removeRow(int row) {
			if (row < 0)
				return;
			content.remove(row);
		}

		public void removeRows(int row, int count) {
			for (int i = 0; i < count; i++) {
				if (content.size() > row) {
					content.remove(row);
				}
			}
		}

		public boolean isCellEditable(int rowIndex, int columnIndex) {
			if (columnIndex == 0)
				return false;
			return true;
		}

		public void setValueAt(Object value, int row, int col) {
			((Vector) content.get(row)).remove(col);
			((Vector) content.get(row)).add(col, value);
			this.fireTableCellUpdated(row, col);
		}

		public String getColumnName(int col) {
			return title_name[col];
		}

		public int getColumnCount() {
			return title_name.length;
		}

		public int getRowCount() {
			return content.size();
		}

		public Object getValueAt(int row, int col) {
			if (content.size() == 0)
				return null;
			return ((Vector) content.get(row)).get(col);
		}

		public Class getColumnClass(int col) {
			if (content.size() == 0)
				return null;
			return getValueAt(0, col).getClass();
		}
	}
}
