import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.util.ArrayList;

public class GameLogic {
    boolean changingColor;
    private boolean drawing;
    private Rectangle red= new Rectangle(428, 449, 100, 80);
    private Rectangle yellow = new Rectangle(576, 449, 100, 80);
    private Rectangle green = new Rectangle(724, 449, 100, 80);
    private Rectangle blue = new Rectangle(872, 449, 100, 80);
    private boolean reverse = false;
    private boolean skip = false;
    private int savedDraw = 0;

    GameLogic() {}

    public boolean cardCheck(Card input, Card topCard, ArrayList<Player> players, Main main, MouseEvent e) {
        System.out.println(input.number);
        System.out.println(topCard.number);
        System.out.println(savedDraw);
        if (savedDraw != 0) {
            if (!input.drawTwo && !input.drawFour) {
                System.out.println("YEEEEEEEETTTTTT");
                drawPlayer(0, players, topCard, main, true);
//                gameStep(players, e, topCard, main);
                return false;
            }
        }

        if (input.isWild) {
            changingColor = true;
            if (input.drawFour) {
                savedDraw += 4;
                drawing = true;
//                drawPlayer(4, players, topCard, main, false);
            }
            System.out.println("wild thing");
            return true;
        } else if (input.color.equals(topCard.color)) {
            System.out.println("color");
            if (input.drawTwo) {
                System.out.println("drawtwo");
                savedDraw += 2;
                drawing = true;
//                drawPlayer(2, players, topCard, main, false);
            } else if (input.reverse) {
                System.out.println("reverse");
                reverseDirection();
            } else if (input.skip) {
                System.out.println("skip");
                skipPlayer();
            }
            return true;
        } else if (input.number == topCard.number && input.number != -1) {
            System.out.println("number");
            return true;
        } else {
            if (input.drawTwo && topCard.drawTwo) {
                System.out.println("drawtwo");
                savedDraw += 2;
                drawing = true;
//                drawPlayer(2, players, topCard, main, false);
                return true;
            } else if (input.reverse && topCard.reverse) {
                System.out.println("reverse");
                reverseDirection();
                return true;
            } else if (input.skip && topCard.skip) {
                System.out.println("skip");
                skipPlayer();
                return true;
            }
        }
        return false;
    }

    private void drawPlayer(int num, ArrayList<Player> players, Card topCard, Main main, boolean override) {
        for (Player player : players) {
            if (player.getPos() == 1) {
                boolean hasDrawTwo = false;
                for (Card c : player.getCards()) {
                    if (c.drawTwo && c.color.equals(topCard.color)) {
                        hasDrawTwo = true;
                        break;
                    }
                }
                savedDraw += num;
                if ((!hasDrawTwo && savedDraw != 0) || override) {
                    JOptionPane.showMessageDialog(main,
                            "Drawing " + savedDraw);
                    for (int i = 0; i < savedDraw; i++) {
                        player.drawCard(main.drawCard());
                    }
                    savedDraw = 0;
                    drawing = false;
                }
            }
        }
    }

    private void reverseDirection() {
        reverse = !reverse;
    }

    private void skipPlayer() {
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

        if (drawing) {
            if (savedDraw != 0) {
                g2.setColor(Color.black);
                g2.drawString("Cards to Draw : " + savedDraw, 20, 50);
            }
        }
    }

    private String containsButton(Point p) {
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

    private void rotPlayers(ArrayList<Player> players, Card topCard, Main main) {
        for (Player player : players) {
            int newPos = player.getPos();
            if (reverse) {
                if (players.size() > 2) {
                    newPos--;
                    if (newPos < 1)
                        newPos = players.size();
                }
            } else if (skip) {
                System.out.println("SKIPPPPPP   " + skip);
                if (players.size() > 2) {
                    newPos += 2; // 1 -> 3 2 -> 4 3 -> 5// 4 -> 6
                    if (newPos > players.size())
                        newPos = newPos - players.size();
                }
            } else {
                newPos++;
                if (newPos > players.size())
                    newPos = 1;
            }

            player.setPosition(newPos);
            drawPlayer(0, players, topCard, main, false);
        }
        skip = false;
    }

    public String gameStep(ArrayList<Player> players, MouseEvent e, Card topCard, Main main) {
        if (changingColor) {
            String str = containsButton(new Point(e.getX(), e.getY()));
            if (str != null) {
                rotPlayers(players, topCard, main);
            }
            return str;
        } else {
            rotPlayers(players, topCard, main);
        }
        return null;
    }
}

// bob
// bob
// bob
// bob
// bob
// bob
// bob
// bob
// bob
// bob
// bob
// bob
// bob
// bob
// bob
// bob
// bob
// bob
// bob
// bob
// bob
// bob
// bob
// bob
// bob
// bob
// bob
// bob
// bob
// bob
// bob
// bob
// bob
// bob
// bob
// bob
// bob
// bob
// bob
// bob
// bob
// bob
// bob
// bob
// bob
// bob
// bob
// bob
// bob
// bob
// bob
// bob
// bob
// bob
// bob
// bob
// bob
// bob
// bob
// bob
// bob
// bob
// bob
// bob
// bob
// bob
// bob
// bob
// bob
// bob
// bob
// bob
// bob
// bob
// bob
// bob
// bob
// bob
// bob
// bob
// bob
// bob
// bob
// bob
// bob
// bob
// bob
// bob
// bob
// bob
// bob
// bob
// bob
// bob
// bob
// bob
// bob
// bob
// bob
// bob
// bob
// bob
// bob
// bob
// bob
// bob
// bob
// bob
// bob
// bob
// bob
// bob
// bob
// bob
// bob
// bob
// bob
// bob
// bob
// bob
// bob
// bob
// bob
// bob
// bob
// bob
// bob
// bob
// bob
// bob
// bob
// bob
// bob
// bob
// bob
// bob
// bob
// bob
// bob
// bob
// bob
// bob
// bob
// bob
// bob
// bob
// bob
// bob
// bob
// bob
// bob
// bob
// bob
// bob
// bob
// bob
// bob
// bob
// bob
// bob
// bob
// bob
// bob
// bob
// bob
// bob
// bob
// bob
// bob
// bob
// bob
// bob
// bob
// bob
// bob
// bob
// bob
// bob
// bob
// bob
// bob
// bob
// bob
// bob
// bob
// bob
// bob
// bob
// bob
// bob
// bob
// bob
// bob
// bob
// bob
// bob
// bob
// bob
// bob
// bob
// bob
// bob
// bob
// bob
// bob
// bob
// bob
// bob
// bob
// bob
// bob
// bob
// bob
// bob
// bob
// bob
// bob
// bob
// bob
// bob
// bob
// bob
// bob
// bob
// bob
// bob
// bob
// bob
// bob
// bob
// bob
// bob
// bob
// bob
// bob
// bob
// bob
// bob
// bob
// bob
// bob
// bob
// bob
// bob
// bob
// bob
// bob
// bob
// bob
// bob
// bob
// bob
// bob
// bob
// bob
// bob
// bob
// bob
// bob
// bob
// bob
// bob
// bob
// bob
// bob
// bob
// bob
// bob
// bob
// bob
// bob
// bob
// bob
// bob
// bob
// bob
// bob
// bob
// bob
// bob
// bob
// bob
// bob
// bob
// bob
// bob
// bob
// bob
// bob
// bob
// bob
// bob
// bob
// bob
// bob
// bob
// bob
// bob
// bob
// bob
// bob
// bob
// bob
// bob
// bob
// bob
// bob
// bob
// bob
// bob
// bob
// bob
// bob
// bob
// bob
// bob
// bob
// bob
// bob
// bob
// bob
// bob
// bob
// bob
// bob
// bob
// bob
// bob
// bob
// bob
// bob
// bob
// bob
// bob
// bob
// bob
// bob
// bob
// bob
// bob
// bob
// bob
// bob
// bob
// bob
// bob
// bob
// bob
// bob
// bob
// bob
// bob
// bob
// bob
// bob
// bob
// bob
// bob
// bob
// bob
// bob
// bob
// bob
// bob
// bob
// bob
// bob
// bob
// bob
// bob
// bob
// bob
// bob
// bob
// bob
// bob
// bob
// bob
// bob
// bob
// bob
// bob
// bob
// bob
// bob
// bob
// bob
// bob
// bob
// bob
// bob
// bob
// bob
// bob
// bob
// bob
// bob
// bob
// bob
// bob
// bob
// bob
// bob
// bob
// bob
// bob
// bob
// bob
// bob
// bob
// bob
// bob
// bob
// bob
// bob
// bob
// bob
// bob
// bob
// bob
// bob
// bob
// bob
// bob
// bob
// bob
// bob
// bob
// bob
// bob
// bob
// bob
// bob
// bob
// bob
// bob
// bob
// bob
// bob
// bob
// bob
// bob
// bob
// bob
// bob
// bob
// bob
// bob
// bob
// bob
// bob
// bob
// bob
// bob
// bob
// bob
// bob
// bob
// bob
// bob
// bob
// bob
// bob
// bob
// bob
// bob
// bob
// bob
// bob
// bob
// bob
// bob
// bob
// bob
// bob
// bob
// bob
// bob
// bob
// bob
// bob
// bob
// bob
// bob
// bob
// bob
// bob
// bob
// bob
// bob
// bob
// bob
// bob
// bob
// bob
// bob
// bob
// bob
// bob
// bob
// bob
// bob
// bob
// bob
// bob
// bob
// bob
// bob
// bob
// bob
// bob
// bob
// bob
// bob
// bob
// bob
// bob
// bob
// bob
// bob
// bob
// bob
// bob
// bob
// bob
// bob
// bob
// bob
// bob
// bob
// bob
// bob
// bob
// bob
// bob
// bob
// bob
// bob
// bob
// bob
// bob
// bob
// bob
// bob
// bob
// bob
// bob
// bob
// bob
// bob
// bob
// bob
// bob
// bob
// bob
// bob
// bob
// bob
// bob
// bob
// bob
// bob
// bob
// bob
// bob
// bob
// bob
// bob
// bob
// bob
// bob
// bob
// bob
// bob
// bob
// bob
// bob
// bob
// bob
// bob
// bob
// bob
// bob
// bob
// bob
// bob
// bob
// bob
// bob
// bob
// bob
// bob
// bob
// bob
// bob
// bob
// bob
// bob
// bob
// bob
// bob
// bob
// bob
// bob
// bob
// bob
// bob
// bob
// bob
// bob
// bob
// bob
// bob
// bob
// bob
// bob
// bob
// bob
// bob
// bob
// bob
// bob
// bob
// bob
// bob
// bob
// bob
// bob
// bob
// bob
// bob
// bob
// bob
// bob
// bob
// bob
// bob
// bob
// bob
// bob
// bob
// bob
// bob
// bob
// bob
// bob
// bob
// bob
// bob
// bob
// bob
// bob
// bob
// bob
// bob
// bob
// bob
// bob
// bob
// bob
// bob
// bob
// bob
// bob
// bob
// bob
// bob
// bob
// bob
// bob
// bob
// bob
// bob
// bob
// bob
// bob
// bob
// bob
// bob
// bob
// bob
// bob
// bob
// bob
// bob
// bob
// bob
// bob
// bob
// bob
// bob
// bob
// bob
// bob
// bob
// bob
// bob
// bob
// bob
// bob
// bob
// bob
// bob
// bob
// bob
// bob
// bob
// bob
// bob
// bob
// bob
// bob
// bob
// bob
// bob
// bob
// bob
// bob
// bob
// bob
// bob
// bob
// bob
// bob
// bob
// bob
// bob
// bob
// bob
// bob
// bob
// bob
// bob
// bob
// bob
// bob
// bob
// bob
// bob
// bob
// bob
// bob
// bob
// bob
// bob
// bob
// bob
// bob
// bob
// bob
// bob
// bob
// bob
// bob
// bob
// bob
// bob
// bob
// bob
// bob
// bob
// bob
// bob
// bob
// bob
// bob
// bob
// bob
// bob
// bob
// bob
// bob
// bob
// bob
// bob
// bob
// bob
// bob
// bob
// bob
// bob
// bob
// bob
// bob
// bob
// bob
// bob
// bob
// bob
// bob
// bob
// bob
// bob
// bob
// bob
// bob
// bob
// bob
// bob
// bob
// bob
// bob
// bob
// bob
// bob
// bob
// bob
// bob
// bob
// bob
// bob
// bob
// bob
// bob
// bob
// bob
// bob
// bob
// bob
// bob
// bob
// bob
// bob
// bob
// bob
// bob
// bob
// bob
// bob
// bob
// bob
// bob
// bob
// bob
// bob
// bob
// bob
// bob
// bob
// bob
// bob
// bob
// bob
// bob
// bob
// bob
// bob
// bob
// bob
// bob
// bob
// bob
// bob
// bob
// bob
// bob
// bob
// bob
// bob
// bob
// bob
// bob
// bob
// bob
// bob
// bob
// bob
// bob
// bob
// bob
// bob
// bob
// bob
// bob
// bob
// bob
// bob
// bob
// bob
// bob
// bob
// bob
// bob
// bob
// bob
// bob
// bob
// bob
// bob
// bob
// bob
// bob
// bob
// bob
// bob
// bob
// bob
// bob
// bob
// bob
// bob
// bob
// bob
// bob
// bob
// bob
// bob
// bob
// bob
// bob
// bob
// bob
// bob
// bob
// bob
// bob
// bob
// bob
// bob
// bob
// bob
// bob
// bob
// bob
// bob
// bob
// bob
// bob
// bob
// bob
// bob
// bob
// bob
// bob
// bob
// bob
// bob
// bob
// bob
// bob
// bob
// bob
// bob
// bob
// bob
// bob
// bob
// bob
// bob
// bob
// bob
// bob
// bob
// bob
// bob
// bob
// bob
// bob
// bob
// bob
// bob
// bob
// bob
// bob
// bob
// bob
// bob
// bob
// bob
// bob
// bob
// bob
// bob
// bob
// bob
// bob
// bob
// bob
// bob
// bob
// bob
// bob
// bob
// bob
// bob
// bob
// bob
// bob
// bob
// bob
// bob
// bob
// bob
// bob
// bob
// bob
// bob
// bob
// bob
// bob
// bob
// bob
// bob
// bob
// bob
// bob
// bob
// bob
// bob
// bob
// bob
// bob
// bob
// bob
// bob
// bob
// bob
// bob
// bob
// bob
// bob
// bob
// bob
// bob
// bob
// bob
// bob
// bob
// bob
// bob
// bob
// bob
// bob
// bob
// bob
// bob
// bob
// bob
// bob
// bob
// bob
// bob
// bob
// bob
// bob
// bob
// bob
// bob
// bob
// bob
// bob
// bob
// bob
// bob
// bob
// bob
// bob
// bob
// bob
// bob
// bob
// bob
// bob
// bob
// bob
// bob
// bob
// bob
// bob
// bob
// bob
// bob
// bob
// bob
// bob
// bob
// bob
// bob
// bob
// bob
// bob
// bob
// bob
// bob
// bob
// bob
// bob
// bob
// bob
// bob
// bob
// bob
// bob
// bob
// bob
// bob
// bob
// bob
// bob
// bob
// bob
// bob
// bob
// bob
// bob
// bob
// bob
// bob
// bob
// bob
// bob
// bob
// bob
// bob
// bob
// bob
// bob
// bob
// bob
// bob
// bob
// bob
// bob
// bob
// bob
// bob
// bob
// bob
// bob
// bob
// bob
// bob
// bob
// bob
// bob
// bob
// bob
// bob
// bob
// bob
// bob
// bob
// bob
// bob
// bob
// bob
// bob
// bob
// bob
// bob
// bob
// bob
// bob
// bob
// bob
// bob
// bob
// bob
// bob
// bob
// bob
// bob
// bob
// bob
// bob
// bob
// bob
// bob
// bob
// bob
// bob
// bob
// bob
// bob
// bob
// bob
// bob
// bob
// bob
// bob
// bob
// bob
// bob
// bob
// bob
// bob
// bob
// bob
// bob
// bob
// bob
// bob
// bob
// bob
// bob
// bob
// bob
// bob
// bob
// bob
// bob
// bob
// bob
// bob
// bob
// bob
// bob
// bob
// bob
// bob
// bob
// bob
// bob
// bob
// bob
// bob
// bob
// bob
// bob
// bob
// bob
// bob
// bob
// bob
// bob
// bob
// bob
// bob
// bob
// bob
// bob
// bob
// bob
// bob
// bob
// bob
// bob
// bob
// bob
// bob
// bob
// bob
// bob
// bob
// bob
// bob
// bob
// bob
// bob
// bob
// bob
// bob
// bob
// bob
// bob
// bob
// bob
// bob
// bob
// bob
// bob
// bob
// bob
// bob
// bob
// bob
// bob
// bob
// bob
// bob
// bob
// bob
// bob
// bob
// bob
// bob
// bob
// bob
// bob
// bob
// bob
// bob
// bob
// bob
// bob
// bob
// bob
// bob
// bob
// bob
// bob
// bob
// bob
// bob
// bob
// bob
// bob
// bob
// bob
// bob
// bob
// bob
// bob
// bob
// bob
// bob
// bob
// bob
// bob
// bob
// bob
// bob
// bob
// bob
// bob
// bob
// bob
// bob
// bob
// bob
// bob
// bob
// bob
// bob
// bob
// bob
// bob
// bob
// bob
// bob
// bob
// bob
// bob
// bob
// bob
// bob
// bob
// bob
// bob
// bob
// bob
// bob
// bob
// bob
// bob
// bob
// bob
// bob
// bob
// bob
// bob
// bob
// bob
// bob
// bob
// bob
// bob
// bob
// bob
// bob
// bob
// bob
// bob
// bob
// bob
// bob
// bob
// bob
// bob
// bob
// bob
// bob
// bob
// bob
// bob
// bob
// bob
// bob
// bob
// bob
// bob
// bob
// bob
// bob
// bob
// bob
// bob
// bob
// bob
// bob
// bob
// bob
// bob
// bob
// bob
// bob
// bob
// bob
// bob
// bob
// bob
// bob
// bob
// bob
// bob
// bob
// bob
// bob
// bob
// bob
// bob
// bob
// bob
// bob
// bob
// bob
// bob
// bob
// bob
// bob
// bob
// bob
// bob
// bob
// bob
// bob
// bob
// bob
// bob
// bob
// bob
// bob
// bob
// bob
// bob
// bob
// bob
// bob
// bob
// bob
// bob
// bob
// bob
// bob
// bob
// bob
// bob
// bob
// bob
// bob
// bob
// bob
// bob
// bob
// bob
// bob
// bob
// bob
// bob
// bob
// bob
// bob
// bob
// bob
// bob
// bob
// bob
// bob
// bob
// bob
// bob
// bob
// bob
// bob
// bob
// bob
// bob
// bob
// bob
// bob
// bob
// bob
// bob
// bob
// bob
// bob
// bob
// bob
