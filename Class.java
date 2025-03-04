import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Color;

public class Class {
	
	private int x, y;
    private int width = 120;
    private int height = 90;
    private String name;
    private boolean selected = false;
	
	public Class(int x, int y, String name) {
		this.x = x;
		this.y = y;
		this.name = name;
	}
	public void draw(Graphics g) {
        draw(g, selected ? Color.cyan : Color.white);
    }
    
    public void draw(Graphics g, Color fillColor) {
        Graphics2D g2 = (Graphics2D) g;
        g2.setColor(fillColor);
        g2.fillRect(x, y, width, height);
        
        g2.setColor(Color.black);
        g2.drawRect(x, y, width, height);
        
        int sectionHeight = height / 3;
        g2.drawLine(x, y + sectionHeight, x + width, y + sectionHeight);
        g2.drawLine(x, y + 2 * sectionHeight, x + width, y + 2 * sectionHeight);
        
        g2.drawString(name, x + 5, y + sectionHeight - 5);
        g2.drawString("atributos", x + 5, y + sectionHeight * 2 - 5);
        g2.drawString("métodos", x + 5, y + height - 5);
		
	}
	
	public int getX() { return x; }
    public int getY() { return y; }
    public int getWidth() { return width; }
    public int getHeight() { return height; }
	public String getName() { return name; }
    
    public void setPosition(int newX, int newY) {
        x = newX;
        y = newY;
    }
    
    public void setSelected(boolean sel) {
        selected = sel;
    }
    
    public boolean isSelected() {
        return selected;
    }
    
    public boolean containsPoint(int px, int py) {
        return (px >= x && px <= x + width && py >= y && py <= y + height);
    }
}
