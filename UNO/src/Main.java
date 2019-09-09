import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
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

    public static final int WIDTH = 1400, HEIGHT = 800;
    private Timer timer;
    public static final int PILEY = 325;
    public static final int DRAWX = 720;
    public static final int DISCARDX = 715 - 115;
    ArrayList<String> colors = new ArrayList<>();
    public static final int CARDWIDTH = 110;
    public static final int CARDHEIGHT = 150;
    ArrayList<Card> drawPile = new ArrayList<>();
    ArrayList<Card> discardPile = new ArrayList<>();
    ArrayList<Player> players = new ArrayList<>();
    ImageObserver imageObserver = (img, infoflags, x, y, width, height) -> false;


    public Main() {
        colors.add("red");
        colors.add("blue");
        colors.add("green");
        colors.add("yellow");
        colors.add("wild");
        createCards();
//        try (Stream<Path> walk = Files.walk(Paths.get("UNO/Res/"))) {
//
//            java.util.List<String> result = walk.map(Path::toString)
//                    .filter(f -> f.endsWith(".png")).collect(Collectors.toList());
//
//            for (String path : result) {
//                File newFile = new File(path);
//                String[] fls = newFile.getName().split("_");
//                if (colors.contains(fls[0])) {
//                    BufferedImage nextImage = ImageIO.read(newFile);
//                    drawPile.add(new Card(newFile.getName(), nextImage));
//                }
//            }
//
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
        String[] choices = {"1", "2", "3", "4"};
        String input = (String) JOptionPane.showInputDialog(null, "Choose now...",
                "The Choice of a Lifetime", JOptionPane.QUESTION_MESSAGE, null, // Use
                // default
                // icon
                choices, // Array of choices
                choices[1]); // Initial choice
        int curPos = 1;
        for (int i = 0; i < Integer.parseInt(input); i++) {
            ArrayList<Card> cards1 = new ArrayList<>();
            for (int c = 0; c < 7; c++) {
                Card card = drawPile.remove(drawCard());
                cards1.add(card);
            }
            players.add(new Player("Player " + curPos, curPos, cards1));
            curPos++;
        }
        timer = new Timer(1000 / 60, e -> update());
        timer.start();
        setKeyListener();
        setMouseListener();
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
        Point2D center = new Point2D.Float(715, 325 + CARDHEIGHT / 2);
        float radius = getWidth() / 4;
        float[] dist = {0.5f, 1f};
        Color[] colors = {Color.ORANGE, Color.RED};
        RadialGradientPaint p =
                new RadialGradientPaint(center, radius, dist, colors);
        g2.setPaint(p);
        g2.fillRect(0, 0, getWidth(), getHeight());
        g2.setPaint(null);
        for (Player player : players) {
            player.draw(g2);
        }
        if (discardPile.size() > 0)
            g2.drawImage(discardPile.get(discardPile.size() - 1).image, DISCARDX, PILEY, CARDWIDTH, CARDHEIGHT, imageObserver);
        if (drawPile.size() > 0)
            g2.drawImage(drawPile.get(drawPile.size() - 1).backImage, DRAWX, PILEY, CARDWIDTH, CARDHEIGHT, imageObserver);


        boolean on = false;
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

    public void setMouseListener() {
        addMouseListener(new MouseListener() {
            @Override
            public void mouseClicked(MouseEvent e) {
            }

            @Override
            public void mousePressed(MouseEvent e) {
                int start = 720 - (((((Main.CARDWIDTH / 4) * 13) + Main.CARDWIDTH) * 3) / 4);
                int index = -1;
                if (e.getY() > Main.HEIGHT - 180) {
                    if (e.getX() > start && e.getX() < start + ((Main.CARDWIDTH / 4) * 13)) {
                        index = (e.getX() - start) / Player.cardW;
                    } else if (e.getX() > start + ((Main.CARDWIDTH / 4) * 13) && e.getX() < start + ((Main.CARDWIDTH / 4) * 13) + Main.CARDWIDTH) {
                        index = 6;
                    }
                }
                for (Player player : players) {
                    if (player.getPos() == 1)
                        player.setHighlightIndex(index);
                }
            }

            @Override
            public void mouseReleased(MouseEvent e) {
            }

            @Override
            public void mouseEntered(MouseEvent e) {
            }

            @Override
            public void mouseExited(MouseEvent e) {

            }
        });

        addMouseMotionListener(new MouseMotionListener() {
            @Override
            public void mouseDragged(MouseEvent e) {
            }

            @Override
            public void mouseMoved(MouseEvent e) {
            }
        });
    }

    public void createCards() {
        drawPile.clear();
        drawPile.add(new Card("green_0.png"));
        drawPile.add(new Card("red_0.png"));
        drawPile.add(new Card("blue_0.png"));
        drawPile.add(new Card("yellow_0.png"));
        for (int i = 1; i < 10; i++) {
            for (int j = 0; j < 2; j++) {
                drawPile.add(new Card("green_" + i + ".png"));
                drawPile.add(new Card("red_" + i + ".png"));
                drawPile.add(new Card("blue_" + i + ".png"));
                drawPile.add(new Card("yellow_" + i + ".png"));
            }
        }

        for (int i = 0; i < 2; i++) {
            drawPile.add(new Card("green_picker.png"));
            drawPile.add(new Card("red_picker.png"));
            drawPile.add(new Card("blue_picker.png"));
            drawPile.add(new Card("yellow_picker.png"));

            drawPile.add(new Card("green_reverse.png"));
            drawPile.add(new Card("red_reverse.png"));
            drawPile.add(new Card("blue_reverse.png"));
            drawPile.add(new Card("yellow_reverse.png"));

            drawPile.add(new Card("green_skip.png"));
            drawPile.add(new Card("red_skip.png"));
            drawPile.add(new Card("blue_skip.png"));
            drawPile.add(new Card("yellow_skip.png"));
        }
        for (int i = 0; i < 4; i++) {
            drawPile.add(new Card("wild_color_changer.png"));
            drawPile.add(new Card("wild_pick_four.png"));
        }

    }

    public int drawCard() {
        return (int) (Math.random() * drawPile.size());
    }
}

