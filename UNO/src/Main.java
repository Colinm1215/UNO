import javax.swing.*;

public class Main {

    public static void main(String args[]) {
        GUI frame = new GUI();
        frame.setTitle("UNO");
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        frame.setSize(1200, 800);
        frame.setVisible(true);
        frame.setResizable(false);
    }
}

