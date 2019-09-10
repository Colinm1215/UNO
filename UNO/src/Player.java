import javax.swing.*;
import java.awt.*;
import java.awt.image.ImageObserver;
import java.util.ArrayList;
import java.util.concurrent.TimeUnit;

public class Player {
    private String name;
    ImageObserver imageObserver = (img, infoflags, x, y, width, height) -> false;
    private ArrayList<Card> cards = new ArrayList<>();
    private Point cardsPosition;
    private Point identifierPosition;
    private int pos;
    private int highlightIndex = -1;
    int selectionIndex = -1;
    Image playerImage = null;


    Player(String n, int pos, ArrayList<Card> cds) {
        cards.addAll(cds);
        name = n;
        setPosition(pos);
        this.pos = pos;
        Object[] options = {"Yes, please",
                "No, thanks"};
        int op = JOptionPane.showOptionDialog(Main.window,
                "Would you like to take a photo for player identification?",
                "Photo for " + name,
                JOptionPane.YES_NO_OPTION,
                JOptionPane.QUESTION_MESSAGE,
                null,
                options,
                options[1]);
        if (op == 0) {
            JFrame frame = new JFrame(name);
            frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);

            frame.setBounds(0, 0, 100, 100 + 22); //(x, y, w, h) 22 due to title bar.

            JPanel panel = new JPanel();
            panel.setSize(100, 100);
            JLabel nameLabel = new JLabel(name);
            JLabel label = new JLabel("0");
            panel.add(label);

            panel.setFocusable(true);
            panel.grabFocus();

            frame.add(panel);
            frame.setResizable(false);
            frame.setLocationRelativeTo(null);
            frame.setVisible(true);
            for (int i = 1; i < 4; i++) {
                try {
                    label.setText(Integer.toString(i));
                    TimeUnit.SECONDS.sleep(1);
                } catch (InterruptedException e) {
                }
            }
            frame.setVisible(false);
            frame.dispose();
            playerImage = Main.pictureTaker.capture();
            if (playerImage != null)
                JOptionPane.showMessageDialog(frame,
                        "Photo has been taken for " + name,
                        "Confirmation",
                        JOptionPane.PLAIN_MESSAGE);
        }
    }

    public String getName() {
        return name;
    }

    public ArrayList<Card> getCards() {
        return cards;
    }

    public int numOfCards() {
        return cards.size();
    }

    public Point getPosition() {
        return cardsPosition;
    }

    public void setPosition(int pos) {
        switch (pos) {
            case 1:
                cardsPosition = new Point(720, Main.HEIGHT - 180);
                identifierPosition = new Point(720 + ((((Main.CARDWIDTH / 4) * 13) + Main.CARDWIDTH) / 4) + 10, Main.HEIGHT - 180);
                break;
            case 2:
                cardsPosition = new Point(720, 20);
                identifierPosition = new Point(720 + ((((Main.CARDWIDTH / 4) * 13) + Main.CARDWIDTH) / 4) + 10, 20);
                break;
            case 3:
                cardsPosition = new Point((((((Main.CARDWIDTH / 4) * 13) + Main.CARDWIDTH) * 3) / 4) + 10, Main.HEIGHT / 2 - Main.CARDHEIGHT / 2);
                identifierPosition = new Point(10, Main.HEIGHT / 2 + Main.CARDHEIGHT / 2 + 10);
                break;
            case 4:
                cardsPosition = new Point(Main.WIDTH - (((((Main.CARDWIDTH / 4) * 13) + Main.CARDWIDTH) / 4) + 10), Main.HEIGHT / 2 - Main.CARDHEIGHT / 2);
                identifierPosition = new Point(Main.WIDTH - 220, Main.HEIGHT / 2 + Main.CARDHEIGHT / 2 + 10);
                break;
        }
    }

    public void draw(Graphics2D g2) {
        g2.setColor(Color.BLACK);
        g2.setFont(new Font("serif", Font.PLAIN, 40));
//        g2.fillRect(identifierPosition.x + 110, identifierPosition.y, 100, Main.CARDHEIGHT);
        if (playerImage == null) {
            g2.fillRect(identifierPosition.x, identifierPosition.y, 200, Main.CARDHEIGHT);
            g2.setColor(Color.white);
            g2.drawString(name, identifierPosition.x + 25, identifierPosition.y + (Main.CARDHEIGHT / 2));
        }
        else {
            g2.drawImage(playerImage, identifierPosition.x, identifierPosition.y, 200, Main.CARDHEIGHT, imageObserver);
        }
        g2.setColor(Color.BLACK);
        g2.setFont(new Font("serif", Font.PLAIN, 40));
        g2.drawString(Integer.toString(cards.size()), identifierPosition.x + 210 + 25, identifierPosition.y + (Main.CARDHEIGHT / 2));

        int width = ((Main.CARDWIDTH / 4) * 13);
        int cardNum = 14;
        int cardW = Main.CARDWIDTH / 4;
        if (cards.size() < 14)
            cardNum = cards.size();
        if (cards.size() < 7)
            cardNum = 7;
        if (cardNum - 1 > 0)
            cardW = width / (cardNum - 1);
        if (cardW > Main.CARDWIDTH / 4 || cardNum - 1 <= 0)
            cardW = Main.CARDWIDTH / 4;

        int startIndex = cards.size() - 14;
        int i = 0;
        if (startIndex >= 0)
            i = startIndex;
        else
            startIndex = 0;
        int savedX = -1;
        Image savedImage = null;
//        cardsPosition.x+Main.CARDWIDTH
        for (int x = cardsPosition.x - (((((Main.CARDWIDTH / 4) * 13) + Main.CARDWIDTH) * 3) / 4); x <= cardsPosition.x + (cardW * cards.size()); x += cardW) {
            Image image;
            if (pos == 1)
                image = cards.get(i).image;
            else
                image = cards.get(i).backImage;
            g2.drawImage(image, x, cardsPosition.y, Main.CARDWIDTH, Main.CARDHEIGHT, imageObserver);
            if (i == highlightIndex + startIndex) {
                selectionIndex = i;
                savedImage = image;
                savedX = x;
            }
            if (highlightIndex == -1)
                selectionIndex = -1;
            i++;
            if (i >= cards.size())
                break;
        }
        if (highlightIndex != -1 && pos == 1) {
            if (savedImage != null) {
                g2.drawImage(savedImage, savedX, cardsPosition.y, Main.CARDWIDTH, Main.CARDHEIGHT, imageObserver);
                g2.setColor(Color.CYAN);
                Stroke stroke = g2.getStroke();
                g2.setStroke(new BasicStroke(4));
                g2.drawRect(savedX, cardsPosition.y, Main.CARDWIDTH, Main.CARDHEIGHT);
                g2.setStroke(stroke);
            }
        }
    }

    public void setHighlightIndex(int highlightIndex) {
        this.highlightIndex = highlightIndex;
    }

    public int getPos() {
        return pos;
    }

    public void drawCard(Card card) {
        cards.add(card);
    }

    public Card sendCard() {
        if (selectionIndex != -1)
            return cards.remove(selectionIndex);
        return null;
    }
}
