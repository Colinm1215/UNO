import javax.swing.*;
import java.awt.*;
import java.awt.geom.Point2D;

public class GUI extends JPanel {
    GUI() {
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2 = (Graphics2D) g;
        Point2D center = new Point2D.Float(getWidth() / 2, getHeight() / 2);
        float radius = getWidth() / 6;
        float[] dist = {0.4f, 1f};
        Color[] colors = {Color.ORANGE, Color.RED};
        RadialGradientPaint p =
                new RadialGradientPaint(center, radius, dist, colors);
        g2.setPaint(p);
        g2.fillRect(0, 0, getWidth(), getHeight());

    }
}
