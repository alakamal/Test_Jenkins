 package graph;
import java.util.*;
import java.awt.*;
import javax.swing.*;
import java.awt.event.*;
import javax.swing.*;
import java.util.*;
import java.awt.*;
import javax.swing.*;
import java.awt.event.*;


 class Node {
	Point position;
	int data;
	ArrayList<Node> lines;
	int xl, xr, yu, yd;
	int w, h;
	public Node(int data) {
		position = new Point();
		this.data = data;
		lines = new ArrayList<Node>();
	}
	public boolean inBounds(int x, int y) {
		updatePosition();
		if (x > xl && x < xr && y > yu && y < yd)
			return true;
		return false;
	}
	public void setPoint(int xValue, int yValue) {
		position.x = xValue;
		position.y = yValue;
		updatePosition();
	}
	public void updatePosition() {          
		int i = 20;
		xl = (int) position.x - i;
		xr = (int) position.x + i;
		yu = (int) position.y - i;
		yd = (int) position.y + i;
	}
}
class GraphArea extends JPanel {
	ArrayList<Node> graph;
	Node currentNode;
	Point currentPoint;
	boolean new_node, new_node2;
	public GraphArea() {
		new_node = false;
		new_node2 = false;
		currentPoint = new Point();
		addMouseListener(new mouse());
		addMouseMotionListener(new mousemotion());
	}
	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		for (Node n : graph) {
			g.setColor(new Color(0, 102, 204));
			g.drawOval(n.xl, n.yu, 40, 40);
			g.fillOval(n.xl, n.yu, 40, 40);
			g.setColor(Color.white);
			g.drawString("" + n.data, (int) n.position.getX(), (int) n.position.getY());
		}
		g.setColor(new Color(0, 102, 204));
		for (Node s : graph)
			for (Node d : s.lines)
				g.drawLine((int) s.position.x, (int) s.position.y, (int) d.position.x, (int) d.position.y);
		if (new_node2) {
			g.setColor(new Color(0, 102, 204));
			g.drawOval(currentNode.xl, currentNode.yu, 40, 40);
			g.fillOval(currentNode.xl, currentNode.yu, 40, 40);
			g.setColor(Color.WHITE);
			g.drawString("" + currentNode.data, (int) currentNode.position.getX(), (int) currentNode.position.getY());
			if (new_node == false)
				new_node2 = false;
		}
	}
	private class mouse extends MouseAdapter {
		public void mouseClicked(MouseEvent e) throws NullPointerException {
			if (e.isMetaDown()) {
				for (Node n : graph)
					if (n.inBounds(e.getX(), e.getY())) {
						boolean check = true;
						String s = JOptionPane.showInputDialog(GraphArea.this, "Enter a new integer");
						char[] x = s.toCharArray();
						for (char c : x)
							if (!(Character.isDigit(c)))
								check = false;
						if (check)
						{
							boolean isnew = true;
							int no = Integer.parseInt(s);
							for (Node k : graph)
								if (k.data == no)
									isnew = false;
							if (isnew) {
								n.data = no;
								repaint();
							} 
							else
								JOptionPane.showMessageDialog(GraphArea.this, "Data is already taking place");
						}
						else
							JOptionPane.showMessageDialog(GraphArea.this, "Integer only is allowed");
						break;
					} 
			} 
		}
		public void mousePressed(MouseEvent e) {
			if (new_node) {
				if (e.isMetaDown())
					;
				else if (e.isAltDown())
					;
				else {
					new_node2 = true;
					new_node = false;
				}
			}
		}
	}
	private class mousemotion extends MouseMotionAdapter {
		public void mouseMoved(MouseEvent e) {
			if (new_node) {
				currentNode.setPoint(e.getX(), e.getY());
				repaint();
			}
		}
		public void mouseDragged(MouseEvent e) {
			if (e.isMetaDown())
				;
			else if (e.isAltDown())
				;
			else
			{
				for (Node n : graph) {
					if (n.inBounds(e.getX(), e.getY())) {
						n.setPoint(e.getX(), e.getY());
						repaint();
						break;
					} 

				} 

			}

		}

	}
}
 class GraphEditor extends JFrame implements ActionListener {
	JButton New, link, delete;
	GraphArea area;
	JPanel buttons;
	ArrayList<Node> graph;

	public GraphEditor() {
		super("Graph Editor");
		setLayout(new BorderLayout());
		graph = new ArrayList<Node>();
		area = new GraphArea();
		area.setBackground(Color.DARK_GRAY);
		area.graph = graph;
		add(area, BorderLayout.CENTER);
		
		New= new JButton("New Node");
		New.setBorderPainted(true);
		link = new JButton("New Link");
		link.setBorderPainted(true);
		delete = new JButton("Delete Node");
		delete.setBorderPainted(true);
		
		New.addActionListener(this);
		link.addActionListener(this);
		delete.addActionListener(this);

		buttons = new JPanel();
		buttons.add(New);
		buttons.add(link);
		buttons.add(delete);
		buttons.setBackground(new Color(0, 102, 204));
		add(buttons, BorderLayout.SOUTH);
	}
	public void actionPerformed(ActionEvent e) {
		if (e.getSource() == New) {
			String s;
			boolean check = true;
			s = JOptionPane.showInputDialog(this, "Enter an integer");
			char[] x = s.toCharArray();
			for (char c : x)
				if (!(Character.isDigit(c)))
					check = false;
			if (check)
			{
				boolean isnew = true;
				int no = Integer.parseInt(s);
				for (Node n : graph)
					if (n.data == no)
						isnew = false;

				if (isnew) {
					area.new_node = true;

					Node a = new Node(no);

					area.currentNode = a;
					graph.add(a);

				} 
				else
					JOptionPane.showMessageDialog(this, "No Node-Duplication allowed");

			} 
			else {
				JOptionPane.showMessageDialog(this, "Enter an integer");
			}

		} 
		if (e.getSource() == link) {
			boolean check_s = true, check_d = true;
			String src = JOptionPane.showInputDialog(this, "Source Node ?");
			String Dest = JOptionPane.showInputDialog(this, "Destenation Node ?");

			char[] s = src.toCharArray();
			for (char c : s)
				if (!(Character.isDigit(c)))
					check_s = false;
			char[] d = Dest.toCharArray();
			for (char c : d)
				if (!(Character.isDigit(c)))
					check_d = false;

			if (check_s == true && check_d == true) {
				int si = Integer.parseInt(src);
				int di = Integer.parseInt(Dest);
				boolean existSi = false, existDi = false;
				for (Node n : graph) {
					if (n.data == si)
						existSi = true;
					else if (n.data == di)
						existDi = true;
				}
				boolean diFound = false;
				boolean siFound = false;
				if (existDi == true && existSi == true) {
					for (Node n : graph)

						if (n.data == si) {
							for (Node k : n.lines)
								if (k.data == di) {
									diFound = true;
									break;
								}
						} else if (n.data == di)
							for (Node q : n.lines)
								if (q.data == si) {
									siFound = true;
									break;
								}
					if (!(siFound || diFound)) {
						for (Node t : graph)
							if (t.data == si) {
								for (Node k : graph)
									if (k.data == di)
										t.lines.add(k);
								repaint();

								break;
							}
					} else
						JOptionPane.showMessageDialog(GraphEditor.this, "No Link-Duplication allowed");

				}
				else
					JOptionPane.showMessageDialog(GraphEditor.this, "Invalid Nodes");

			} 

		}

		if (e.getSource() == delete) {
			boolean found_node = false;
			String s;
			boolean check = true;
			s = JOptionPane.showInputDialog(this, "Enter an integer");
			char[] x = s.toCharArray();
			for (char c : x)
				if (!(Character.isDigit(c)))
					check = false;
			if (check) {
				int del_num = Integer.parseInt(s);
				int index = -1;				
				for (Node k : graph) {

					for (Node toDelete : k.lines) {
						if (toDelete.data == del_num) {

							k.lines.remove(k.lines.indexOf(toDelete));
							break;
						}

					}

				}
				
				for (Node n : graph)
					if (n.data == del_num) {
						found_node = true;
						n.lines.clear();
						index = graph.indexOf(n);
						break;
					}
				if (index != -1) {
					graph.remove(index);
					repaint();
					index = -1;
				}

			} else
				JOptionPane.showMessageDialog(this, "Enter an integer");

			if (!found_node)
				JOptionPane.showMessageDialog(this, "Visit A Doctor");
			repaint();

		} 

	}
}


public class mainclass {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		GraphEditor graphh=new GraphEditor();
		graphh.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		graphh.setSize(500,500);
		graphh.setLocationRelativeTo(null);
		graphh.setVisible(true);

	}

}
