import java.awt.*;

public class MoveButton {
    public Rectangle rect = new Rectangle(715 - 40, 540, 80, 40);
    boolean hover = false;
    boolean clicked = false;

    public void draw(Graphics2D g2) {
        if (hover)
            g2.setColor(Color.cyan);
        else if (clicked)
            g2.setColor(Color.blue);
        else
            g2.setColor(Color.gray);
        g2.fill(rect);
    }
}
