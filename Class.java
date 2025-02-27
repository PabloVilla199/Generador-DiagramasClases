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
	
	public void draw(Graphics g){
		//Dibuja la clase
		Graphics2D g2 = (Graphics2D)g;
		
		if (selected) {
            g2.setColor(Color.cyan);
        } else {
            g2.setColor(Color.white);
        }
        g2.fillRect(x, y, width, height);
        
        // Borde del rectángulo
        g2.setColor(Color.black);
        g2.drawRect(x, y, width, height);
        
        // División en tres secciones
        int sectionHeight = height / 3;
        g2.drawLine(x, y + sectionHeight, x + width, y + sectionHeight);
        g2.drawLine(x, y + 2 * sectionHeight, x + width, y + 2 * sectionHeight);
        
        // Textos: nombre, "atributos" y "métodos"
        g2.drawString(name, x + 5, y + sectionHeight - 5);
        g2.drawString("atributos", x + 5, y + sectionHeight * 2 - 5);
        g2.drawString("métodos", x + 5, y + height - 5);
		
	}
	
	public int getX() { return x; }
    public int getY() { return y; }
    public int getWidth() { return width; }
    public int getHeight() { return height; }
	//Otros metodos	
	// …
}
