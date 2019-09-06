import java.awt.*;
import java.awt.image.ImageObserver;
import java.util.ArrayList;

public class Player {
    private String name;
    ImageObserver imageObserver = (img, infoflags, x, y, width, height) -> false;
    private ArrayList<Card> cards = new ArrayList<>();
    private Point cardsPosition;
    private Point identifierPosition;
    private int pos = 1;


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
        g2.fillRect(cardsPosition.x - (((((Main.CARDWIDTH / 4) * 13) + Main.CARDWIDTH) * 3) / 4), cardsPosition.y, ((Main.CARDWIDTH / 4) * 13) + Main.CARDWIDTH, Main.CARDHEIGHT);
        g2.fillRect(identifierPosition.x, identifierPosition.y, 100, Main.CARDHEIGHT);
        g2.fillRect(identifierPosition.x + 110, identifierPosition.y, 100, Main.CARDHEIGHT);
        int cardNum = 14;
        if (cards.size() < 14)
            cardNum = cards.size();
        int width = ((Main.CARDWIDTH / 4) * 13);
        int cardW = width / (cardNum - 1);
        int i = 0;
        for (int x = cardsPosition.x - (((((Main.CARDWIDTH / 4) * 13) + Main.CARDWIDTH) * 3) / 4); x < cardsPosition.x + ((Main.CARDWIDTH / 4) * 13) + Main.CARDWIDTH; x += cardW) {
            Image image;
            if (pos == 1)
                image = cards.get(i).image;
            else
                image = cards.get(i).backImage;
            g2.drawImage(image, x, cardsPosition.y, Main.CARDWIDTH, Main.CARDHEIGHT, imageObserver);
            i++;
            if (i >= cards.size())
                break;
        }
    }
}
