import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Color;


public class Association {

	private Class origin;
    private Class destination;
    
    public Association(Class origin, Class destination) {
        this.origin = origin;
        this.destination = destination;
    }
	// Constructores
	// ...
	
	public void draw(Graphics graphics) {
		Graphics2D g2 = (Graphics2D)graphics;

		g2.setColor(Color.black);
        if (origin == destination) {
            // Dibujar un lazo simple a la derecha de la clase
            int x = origin.getX() + origin.getWidth();
            int y = origin.getY() + origin.getHeight() / 2;
            g2.drawArc(x, y - 20, 40, 40, 0, 270);
        } else {
            // Línea desde el centro de la clase "from" al centro de la clase "to"
            int x1 = origin.getX() + origin.getWidth() / 2;
            int y1 = origin.getY() + origin.getHeight() / 2;
            int x2 = destination.getX() + destination.getWidth() / 2;
            int y2 = destination.getY() + destination.getHeight() / 2;
            g2.drawLine(x1, y1, x2, y2);
        }
	}
}
