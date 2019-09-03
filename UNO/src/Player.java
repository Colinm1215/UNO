import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.ArrayList;

public class Player extends JPanel implements MouseListener {
    private String name;
    private ArrayList<String> cards;
    private int position; // 1,2,3,4

    Player(String n, int pos, ArrayList<String> cds) {
        cards.addAll(cds);
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

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
    }

    @Override
    public void mouseClicked(MouseEvent e) {

    }

    @Override
    public void mousePressed(MouseEvent e) {

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
}
