import java.awt.*;
import java.awt.image.ImageObserver;
import java.util.ArrayList;

public class Player {
    private String name;
    ImageObserver imageObserver = (img, infoflags, x, y, width, height) -> false;
    private ArrayList<Card> cards = new ArrayList<>();
    private Point cardsPosition;
    private Point identifierPosition;
    private int pos;
    private int highlightIndex = -1;
    int selectionIndex = -1;


    Player(String n, int pos, ArrayList<Card> cds) {
        cards.addAll(cds);
        name = n;
        setPosition(pos);
        this.pos = pos;
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
        g2.fillRect(identifierPosition.x, identifierPosition.y, 100, Main.CARDHEIGHT);
//        g2.fillRect(identifierPosition.x + 110, identifierPosition.y, 100, Main.CARDHEIGHT);
        g2.setFont(new Font("serif", Font.PLAIN, 40));
        g2.drawString(Integer.toString(cards.size()), identifierPosition.x + 110 + 25, identifierPosition.y + (Main.CARDHEIGHT / 2));

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
