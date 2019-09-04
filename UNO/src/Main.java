import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.geom.Point2D;

public class Main extends JPanel {

    public static final int WIDTH = 1200, HEIGHT = 800;
    private Timer timer;
    boolean doB = false;


    public Main() {
        timer = new Timer(1000 / 60, e -> update());
        timer.start();
        setKeyListener();
        String[] choices = {"1", "2", "3", "4"};
        String input = (String) JOptionPane.showInputDialog(null, "Choose now...",
                "The Choice of a Lifetime", JOptionPane.QUESTION_MESSAGE, null, // Use
                // default
                // icon
                choices, // Array of choices
                choices[1]); // Initial choice
    }


    public static void main(String[] args) {

        JFrame window = new JFrame();
        window.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);

        window.setBounds(0, 0, WIDTH, HEIGHT + 22); //(x, y, w, h) 22 due to title bar.

        Main panel = new Main();
        panel.setSize(WIDTH, HEIGHT);

//        panel.setFocusable(true);
//        panel.grabFocus();

        window.add(panel);
        window.setVisible(true);
        window.setResizable(false);
    }

    public void update() {
        repaint();
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2 = (Graphics2D) g;
        Point2D center = new Point2D.Float(getWidth() / 2, getHeight() / 2);
        float radius = getWidth() / 4;
        float[] dist = {0.5f, 1f};
        Color[] colors = {Color.ORANGE, Color.RED};
        RadialGradientPaint p =
                new RadialGradientPaint(center, radius, dist, colors);
        g2.setPaint(p);
        g2.fillRect(0, 0, getWidth(), getHeight());
        g2.setPaint(null);
        if (doB) {
            g2.setColor(Color.black);
            g2.fillRect(0, 0, 100, 100);
        }
    }

    public void setKeyListener() {
        addKeyListener(new KeyListener() {
            @Override
            public void keyTyped(KeyEvent e) {

            }

            @Override
            public void keyPressed(KeyEvent e) {
                doB = true;
            }

            @Override
            public void keyReleased(KeyEvent e) {

            }
        });
    }

}

