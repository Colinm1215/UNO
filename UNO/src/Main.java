import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.geom.Point2D;
import java.awt.image.BufferedImage;
import java.awt.image.ImageObserver;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class Main extends JPanel {

    public static final int WIDTH = 1200, HEIGHT = 800;
    private Timer timer;
    ArrayList<Card> cards = new ArrayList<>();
    ArrayList<String> colors = new ArrayList<>();


    public Main() {
        colors.add("red");
        colors.add("blue");
        colors.add("green");
        colors.add("yellow");
        colors.add("wild");
        try (Stream<Path> walk = Files.walk(Paths.get("UNO/Res/"))) {

            java.util.List<String> result = walk.map(Path::toString)
                    .filter(f -> f.endsWith(".png")).collect(Collectors.toList());

            for (String path : result) {
                File newFile = new File(path);
                String[] fls = newFile.getName().split("_");
                if (colors.contains(fls[0])) {
                    BufferedImage nextImage = ImageIO.read(newFile);
                    cards.add(new Card(newFile.getName(), nextImage));
                }
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
        String[] choices = {"1", "2", "3", "4"};
        String input = (String) JOptionPane.showInputDialog(null, "Choose now...",
                "The Choice of a Lifetime", JOptionPane.QUESTION_MESSAGE, null, // Use
                // default
                // icon
                choices, // Array of choices
                choices[1]); // Initial choice
        timer = new Timer(1000 / 60, e -> update());
        timer.start();
        setKeyListener();
    }


    public static void main(String[] args) {

        JFrame window = new JFrame();
        window.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);

        window.setBounds(0, 0, WIDTH, HEIGHT + 22); //(x, y, w, h) 22 due to title bar.

        Main panel = new Main();
        panel.setSize(WIDTH, HEIGHT);

        panel.setFocusable(true);
        panel.grabFocus();

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
        ArrayList<Card> testArrayList = new ArrayList<>();                                // creating array to create player
        Point2D center = new Point2D.Float(getWidth() / 2, getHeight() / 2);
        float radius = getWidth() / 4;
        float[] dist = {0.5f, 1f};
        Color[] colors = {Color.ORANGE, Color.RED};
        RadialGradientPaint p =
                new RadialGradientPaint(center, radius, dist, colors);
        g2.setPaint(p);
        g2.fillRect(0, 0, getWidth(), getHeight());
        g2.setPaint(null);
        try {
            File newFile = new File("UNO/Res/card_back_alt.png");
            BufferedImage backImage = ImageIO.read(newFile);
            Image image = backImage;
            ImageObserver imageObserver = (img, infoflags, x, y, width, height) -> false;
            g2.drawImage(image, 0, 0, 110, 150, imageObserver);

        } catch (IOException e) {
            e.printStackTrace();
        }
        Player testPlayer = new Player("Test", 1, testArrayList);
        testPlayer.draw(g2);

    }

    public void setKeyListener() {
        addKeyListener(new KeyListener() {
            @Override
            public void keyTyped(KeyEvent e) {
            }

            @Override
            public void keyPressed(KeyEvent e) {
            }

            @Override
            public void keyReleased(KeyEvent e) {
            }
        });
    }

    public int drawCard() {
        int rand = (int) (Math.random() * cards.size());
        return rand;
    }
}

