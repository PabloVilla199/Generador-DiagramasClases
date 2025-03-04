import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import java.util.*;

public class Diagram 
		extends JPanel 
		implements MouseListener, 
			   MouseMotionListener, 
			   KeyListener {
	
	//atributos
	private Window window;
	public Class clase; 
	
	private Vector<Class> classes = new Vector<>(); 
	private Vector<Association> associations = new Vector<>(); 
	
	private Class draggingClass = null;
    private int offsetX, offsetY;
    
    private Class selectedClass = null;
	private Class hoveredClass = null;
    
    private boolean isDraggingAssociation = false;
    private Class associationSource = null;
    private int currentDragX, currentDragY;


	//metodos
	public Diagram(Window theWindow) {
		window = theWindow;
		
		addMouseListener(this);
		addMouseMotionListener(this);
		addKeyListener(this);
		
		setBorder(BorderFactory.createLineBorder(Color.black));
	}
	
	public void addClass() {
		int n = classes.size() + 1;
        int x = 50 + (n - 1) * 10;
        int y = 50 + (n - 1) * 10;
        Class newClass = new Class(x, y, "Class " + n);
        classes.add(newClass);
        window.updateNClasses(this);
        repaint();
	}
	
	public int getNClasses(){
		return classes.size();
	}
	
	public int getNAssociations(){
		return associations.size();
	}

	public void paint(Graphics g) {
		super.paint(g);
		for (Association assoc : associations) {
			assoc.draw(g);
		}
		for (Class dc : classes) {
			if (dc == hoveredClass && isDraggingAssociation) {
				dc.draw(g, Color.green);  
			} else {
				dc.draw(g);
			}
		}
		if (isDraggingAssociation && associationSource != null) {
			Graphics2D g2 = (Graphics2D) g;
			g2.setColor(Color.red);
			int x1 = associationSource.getX() + associationSource.getWidth() / 2;
			int y1 = associationSource.getY() + associationSource.getHeight() / 2;
			g2.drawLine(x1, y1, currentDragX, currentDragY);
		}
	}
	
	/********************************************/
	/** MÈtodos de MouseListener               **/
	/********************************************/

	public void mousePressed(MouseEvent e) {
		requestFocusInWindow();
        int x = e.getX();
        int y = e.getY();
        Class clicked = getClassAtPoint(x, y);
        if (SwingUtilities.isLeftMouseButton(e)) {
            if (clicked != null) {
                if (clicked == selectedClass) {
                    isDraggingAssociation = true;
                    associationSource = clicked;
                    currentDragX = x;
                    currentDragY = y;
                } else {
                    draggingClass = clicked;
                    offsetX = x - draggingClass.getX();
                    offsetY = y - draggingClass.getY();

                    classes.remove(clicked);
                    classes.add(clicked);
                    repaint();
                }
            }
        } else if (SwingUtilities.isRightMouseButton(e)) {
            if (clicked != null) {
                deleteClass(clicked);
                repaint();
            }
        }
   	 }
    
	public void mouseReleased(MouseEvent e) {
		int x = e.getX();
        int y = e.getY();
        if (isDraggingAssociation && associationSource != null) {
            Class target = getClassAtPoint(x, y);
            if (target != null) {
                Association assoc = new Association(associationSource, target);
                associations.add(assoc);
                window.updateNAssociations(this);
            }
            isDraggingAssociation = false;
            associationSource = null;
            if (selectedClass != null) {
                selectedClass.setSelected(false);
                selectedClass = null;
            }
            repaint();
        }
        if (draggingClass != null) {
            draggingClass = null;
        }
	}

	public void mouseEntered(MouseEvent e) {
	}
    
	public void mouseExited(MouseEvent e) {
	}
    
	public void mouseClicked(MouseEvent e) {
	}

	/********************************************/
	/** MÈtodos de MouseMotionListener         **/
	/********************************************/    
	public void mouseMoved(MouseEvent e) {
        int x = e.getX();
        int y = e.getY();
        Class currentHover = getClassAtPoint(x, y);

        if (currentHover != hoveredClass) {
            hoveredClass = currentHover;
            repaint();
        }
    }
    
	public void mouseDragged(MouseEvent e) {
		int x = e.getX();
        int y = e.getY();
        if (isDraggingAssociation) {
            currentDragX = x;
			currentDragY = y;

			hoveredClass = getClassAtPoint(x, y);
			repaint();
            repaint();
        } else if (draggingClass != null) {
            draggingClass.setPosition(x - offsetX, y - offsetY);
            repaint();
        }
	}
    
	/********************************************/
	/** MÈtodos de KeyListener                 **/
	/********************************************/

	public void keyTyped(KeyEvent e) {
	}
    
	public void keyPressed(KeyEvent e) {
		if (e.getKeyCode() == KeyEvent.VK_S) {
            Point p = getMousePosition();
            if (p != null) {
                Class clicked = getClassAtPoint(p.x, p.y);
                if (clicked != null) {
                    if (clicked == selectedClass) {
                        clicked.setSelected(false);
                        selectedClass = null;
                    } else {
                        if (selectedClass != null) {
                            selectedClass.setSelected(false);
                        }
                        selectedClass = clicked;
                        clicked.setSelected(true);
                    }
                } else {
                    if (selectedClass != null) {
                        selectedClass.setSelected(false);
                        selectedClass = null;
                    }
                }
                repaint();
            }
        }
	}
    
	public void keyReleased(KeyEvent e) {
	}

	// =============================
    // Métodos auxiliares
    // =============================
    // Devuelve la clase en el punto (x,y) recorriendo desde la última (que está al frente)

    private Class getClassAtPoint(int x, int y) {
        for (int i = classes.size() - 1; i >= 0; i--) {
            Class dc = classes.get(i);
            if (dc.containsPoint(x, y)) {
                return dc;
            }
        }
        return null;
    }

	 // Elimina una clase y las asociaciones relacionadas
	 private void deleteClass(Class dc) {
        classes.remove(dc);
        associations.removeIf(assoc -> assoc.getOrigin() == dc || assoc.getDestination() == dc);
        if (dc == selectedClass) {
            selectedClass = null;
        }
        window.updateNClasses(this);
        window.updateNAssociations(this);
    }
}
