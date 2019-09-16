import org.w3c.dom.css.Rect;

import java.awt.*;
import java.awt.geom.Rectangle2D;
import java.lang.reflect.Array;
import java.util.ArrayList;

public class GameLogic {
    private boolean changingColor;
    private ArrayList<Card> discardPile;
    private Rectangle red= new Rectangle(428, 449, 100, 80);
    private Rectangle yellow = new Rectangle(576, 449, 100, 80);
    private Rectangle green = new Rectangle(724, 449, 100, 80);
    private Rectangle blue = new Rectangle(872, 449, 100, 80);
    boolean reverse = false;
    boolean skip = false;

    public GameLogic() {}

    public boolean cardCheck(Card input, Card topCard, ArrayList<Player> players, Main main) {
        System.out.println(input.number);
        System.out.println(topCard.number);
        if (input.colorChange) {
            changingColor = true;
            System.out.println("color changing");
            return true;
        } else if (input.isWild) {
            changingColor = true;
            if (input.drawFour) {
                drawPlayer(4, players, main);
            }
            System.out.println("wild thing");
            return true;
        } else if (input.color.equals(topCard.color)) {
            if (input.drawTwo) {
                System.out.println("drawtwo");
                drawPlayer(2, players, main);
            }
            if (input.reverse) {
                System.out.println("reverse");
                reverseDirection();
            }
            if (input.skip) {
                System.out.println("skip");
                skipPlayer();
            }
            return true;

        } else if (input.number == topCard.number && input.number != -1) {
            System.out.println("number");
            return true;
        }
        return false;
    }

    public void drawPlayer(int num, ArrayList<Player> players, Main main) {
        for (Player player : players) {
            if (player.getPos() == 2) {
                for (int i = 0; i < num; i++) {
                    player.drawCard(main.drawCard());
                }
            }
        }
    }

    public void reverseDirection() {
        reverse = true;
    }

    public void skipPlayer() {
        skip = true;
    }

    public void draw(Graphics g2) {
        if (changingColor) {
            g2.setColor(Color.RED);
            g2.fillRect(428, 449, 100, 80);
            g2.setColor(Color.YELLOW);
            g2.fillRect(576, 449, 100, 80);

            g2.setColor(Color.GREEN);
            g2.fillRect(724, 449, 100, 80);

            g2.setColor(Color.BLUE);
            g2.fillRect(872, 449, 100, 80);


            g2.setColor(new Color(200, 200, 200));
            g2.drawRect(428, 449, 100, 80);
            g2.drawRect(576, 449, 100, 80);
            g2.drawRect(724, 449, 100, 80);
            g2.drawRect(872, 449, 100, 80);
        }
    }

    public String containsButton(Point p) {
        if (red != null && blue != null && green != null && yellow != null) {
            if (red.contains(p)) {
                changingColor = false;
                return "red";
            }
            else if (blue.contains(p)) {
                changingColor = false;
                return "blue";
            }
            else if (green.contains(p)) {
                changingColor = false;
                return "green";
            }
            else if (yellow.contains(p)) {
                changingColor = false;
                return "yellow";
            }
        } return null;
    }

    public void rotPlayers(ArrayList<Player> players){
        for (Player player : players) {
            int newPos = player.getPos();
            if (reverse) {
                if (players.size() > 2) {
                    newPos--;
                    if (newPos < 1)
                        newPos = players.size();
                    reverse = false;
                } else {
                    reverse = false;
                }
            } else if (skip) {
                newPos+=2;
                if (newPos > players.size())
                    newPos = 1;
            } else {
                newPos++;
                if (newPos > players.size())
                    newPos = 1;
            }
            player.setPosition(newPos);
        }
    }
}
