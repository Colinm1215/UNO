import java.awt.*;
import java.util.ArrayList;

public class Player {
    private String name;
    private ArrayList<String> cards;
    private int position; // 1,2,3,4

    Player(String n, int pos, ArrayList<String> cds) {
//        cards.addAll(cds);
        name = n;
        position = pos;
    }

    public String getName() {
        return name;
    }

    public ArrayList<String> getCards() {
        return cards;
    }

    public int numOfCards() {
        return cards.size();
    }

    public int getPosition() {
        return position;
    }

    public void draw(Graphics2D g2) {
        g2.setColor(new Color(133, 94, 66));
//        g2.fillRect();
    }
}
