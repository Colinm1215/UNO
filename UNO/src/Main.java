import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.awt.geom.Point2D;
import java.awt.image.ImageObserver;
import java.util.ArrayList;

public class Main extends JPanel {

    public static final int WIDTH = 1400, HEIGHT = 800;
    private Timer timer;
    public static final int PILEY = 325;
    public static final int DRAWX = 720;
    public static final int DISCARDX = 715 - 115;
    public static final int CARDWIDTH = 110;
    public static final int CARDHEIGHT = 150;
    ArrayList<Card> drawPile = new ArrayList<>();
    ArrayList<Card> discardPile = new ArrayList<>();
    ArrayList<Player> players = new ArrayList<>();
    ImageObserver imageObserver = (img, infoflags, x, y, width, height) -> false;
    MoveButton moveButton = new MoveButton();
    GameLogic gameLogic = new GameLogic();
    Rectangle drawArea = new Rectangle(DRAWX, PILEY, CARDWIDTH, CARDHEIGHT);
    public static final PictureTaker pictureTaker = new PictureTaker();
    public static final JFrame window = new JFrame();
    Main main = this;


    public Main() {
        createCards();
        String[] choices = {"1", "2", "3", "4"};
        String input = (String) JOptionPane.showInputDialog(null, "Choose now...",
                "The Choice of a Lifetime", JOptionPane.QUESTION_MESSAGE, null, // Use
                // default
                // icon
                choices, // Array of choices
                choices[1]); // Initial choice
        int curPos = 1;
        for (int i = 0; i < Integer.parseInt(input); i++) {
            ArrayList<Card> cardsTemp = new ArrayList<>();
            for (int c = 0; c < 7; c++) {
                Card card = drawCard();
                cardsTemp.add(card);
            }
            players.add(new Player("Player " + curPos, curPos, cardsTemp, Integer.parseInt(input)));
            curPos++;
        }
        pictureTaker.webcam.close();
        timer = new Timer(1000 / 60, e -> update());
        timer.start();
        setKeyListener();
        setMouseListener();
    }


    public static void main(String[] args) {

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
        moveButton.draw(g2);

        gameLogic.draw(g2);
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
                for (Player p : players) {
                    if (p.getPos() == 1) {
                        if (e.getKeyCode() == KeyEvent.VK_SPACE) {
                            p.showCards = true;
                            break;
                        }
                    }
                }
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
                Player curPlayer = players.get(0);
                for (Player player : players) {
                    System.out.println(player.getPos());
                    if (player.getPos() == 1) {
                        curPlayer = player;
                        break;
                    }
                }
                System.out.println(curPlayer.getPos());
                Card selectedCard = curPlayer.getHighlightedCard();

                highlightCard(e.getX(), e.getY(), curPlayer);

                if (drawArea.contains(e.getX(), e.getY())) {
                    try {
                        curPlayer.drawCard(drawCard());
                    } catch (IndexOutOfBoundsException err) {
                    }
                }

                if (moveButton.rect.contains(e.getX(), e.getY())) {
                    if (selectedCard != null) {
                        if (gameLogic.cardCheck(selectedCard, discardPile.get(discardPile.size() - 1), players, main, e)) {
                            selectedCard = curPlayer.sendCard();
                            discardPile.add(selectedCard);
                            gameLogic.gameStep(players, e, discardPile.get(discardPile.size() - 1), main);
                        }
                    }

                    moveButton.clicked = true;
                    if (moveButton.hover)
                        moveButton.hover = false;
                }

                if (discardPile.get(discardPile.size() - 1).colorChange || discardPile.get(discardPile.size() - 1).isWild) {
                    if (gameLogic.changingColor) {
                        String str = gameLogic.gameStep(players, e, discardPile.get(discardPile.size() - 1), main);
                        System.out.println(str);
                        if (str != null)
                            discardPile.get(discardPile.size() - 1).changeColor(str);
                    }
                }
            }

            @Override
            public void mouseReleased(MouseEvent e) {
                if (moveButton.clicked) {
                    moveButton.clicked = false;
                    if (moveButton.rect.contains(e.getX(), e.getY()))
                        moveButton.hover = true;
                }
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
                if (moveButton.rect.contains(e.getX(), e.getY()) && !moveButton.clicked)
                    moveButton.hover = true;
                else if (moveButton.hover || moveButton.clicked)
                    moveButton.hover = false;
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

        discardPile.add(drawCard());

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

    public Card drawCard() {
        return drawPile.get((int)(Math.random() * drawPile.size()));
    }

    public void highlightCard(int x, int y, Player curPlayer) {
//        Player curPlayer = players.get(0);
//        for (Player player : players) {
//            if (player.getPos() == 1) {
//                curPlayer = player;
//                break;
//            }
//        }
        int start = 369;
        int index = -1;
        ArrayList<Card> cards = curPlayer.getCards();
        int width = ((Main.CARDWIDTH / 4) * 13);
        int cardNum = 14;
        int cardW = Main.CARDWIDTH / 4;
        if (cards.size() < 14)
            cardNum = cards.size();
        if (cardNum - 1 > 0)
            cardW = width / (cardNum - 1);
        if (cardW > Main.CARDWIDTH / 4 || cardNum - 1 <= 0)
            cardW = Main.CARDWIDTH / 4;

        if (y > Main.HEIGHT - 180) {
            if (x >= start && x <= start + (cardW * cardNum)) {
                index = (x - start) / cardW;
                if (index >= cardNum)
                    index = curPlayer.getCards().size() - 1;
                else if (index < 0)
                    index = 0;
            } else if (x >= start + (cardW * cardNum) && x <= (start + (cardW * cardNum)) + Main.CARDWIDTH - cardW) {
                if (cardNum < 14) {
                    index = cards.size() - 1;
                } else {
                    index = 13;
                }
            }
        }
        curPlayer.setHighlightIndex(index);
    }
}

