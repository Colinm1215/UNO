import javax.swing.*;

public class Main {

    public static void main(String args[]) {
        JFrame frame = new JFrame();
        frame.setTitle("UNO");
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        frame.setSize(1200, 820);
        GUI gui = new GUI();
        frame.add(gui);
        frame.setVisible(true);
        frame.setResizable(false);
    }
}

