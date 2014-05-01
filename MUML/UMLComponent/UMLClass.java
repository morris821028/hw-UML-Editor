package UMLComponent;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.*;

import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.border.*;
import javax.swing.event.MouseInputListener;

import frame.MainFrame;

public class UMLClass extends BasicObject implements MouseInputListener {
	public JTextField className;
	public AttributeTable attributeTable;
	public MethodTable methodTable;
	private Font displayFont = new Font("Comic Sans MS", Font.BOLD, 18);
	public JLabel mtitle;
	public JLabel atitle;
	public UMLClass() {
		super();
		this.setBackground(new Color(255, 255, 185));
		this.setLayout(new BorderLayout());
		className = new JTextField("Class_Name");
		this.setName(className.getText());
		className.setBackground(new Color(255, 255, 185));
		className.setFont(displayFont);
		className.setHorizontalAlignment(JTextField.CENTER);
		className.setEditable(false);
		attributeTable = new AttributeTable();
		methodTable = new MethodTable();

		JPanel apanel = new JPanel();
		apanel.setLayout(new BorderLayout());
		atitle = new JLabel("- Attribute field");
		atitle.setFont(this.displayFont);
		atitle.setBackground(attributeTable.getBackground());
		atitle.setOpaque(true);
		atitle.setComponentPopupMenu(attributeTable.getToolMenu());
		apanel.add(atitle, BorderLayout.NORTH);
		apanel.add(attributeTable, BorderLayout.CENTER);

		JPanel mpanel = new JPanel();
		mpanel.setLayout(new BorderLayout());
		mtitle = new JLabel("- Method field");
		mtitle.setFont(this.displayFont);
		mtitle.setBackground(methodTable.getBackground());
		mtitle.setOpaque(true);
		mtitle.setComponentPopupMenu(methodTable.getToolMenu());
		mpanel.add(mtitle, BorderLayout.NORTH);
		mpanel.add(methodTable, BorderLayout.CENTER);

		this.add(className, BorderLayout.NORTH);
		this.add(apanel, BorderLayout.CENTER);
		this.add(mpanel, BorderLayout.SOUTH);
		attributeTable.addMouseListener(this);
		attributeTable.addMouseMotionListener(this);
		this.addMouseListener(this);
		this.addMouseMotionListener(this);
		this.setBorder(new LineBorder(this.getBackground(), 5));
	}

	@Override
	public String getName() {
		if(className != null)
			return className.getText();
		return super.getName();
	}

	@Override
	public String getTypeName() {
		return "UML_CLASS";
	}

	@Override
	public void setName(String s) {
		super.setName(s);
		className.setText(s);
	}

	@Override
	public void setSelected(boolean b) {
		super.setSelected(b);
		if (b == true) {
			className.setEditable(true);
		} else {
			className.setEditable(false);
			methodTable.clearSelection();
			attributeTable.clearSelection();
		}
	}

	public void mouseMoved(MouseEvent e) {
	}

	public void mouseDragged(MouseEvent e) {
	}

	public void mouseReleased(MouseEvent e) {
	}

	public void mouseEntered(MouseEvent e) {
	}

	public void mouseExited(MouseEvent e) {
	}

	public void mouseClicked(MouseEvent e) {
	}

	public void mousePressed(MouseEvent e) {
	}

	@Override
	public void addMouseListener(MouseListener l) {
		super.addMouseListener(l);
		mtitle.addMouseListener(l);
		atitle.addMouseListener(l);
		attributeTable.addMouseListener(l);
		methodTable.addMouseListener(l);
	}
	@Override
	public void addMouseMotionListener(MouseMotionListener l) {
		super.addMouseMotionListener(l);
		mtitle.addMouseMotionListener(l);
		atitle.addMouseMotionListener(l);
		attributeTable.addMouseMotionListener(l);
		methodTable.addMouseMotionListener(l);
	}
}
